package com.zhezhu.access.application.wechat;

import com.zhezhu.access.domain.model.wechat.*;
import com.zhezhu.access.domain.model.wechat.audit.*;
import com.zhezhu.access.domain.model.wechat.config.WeChatConfig;
import com.zhezhu.access.domain.model.wechat.message.MessageHandler;
import com.zhezhu.access.domain.model.wechat.message.XmlMessage;
import com.zhezhu.access.domain.model.wechat.message.XmlOutMessage;
import com.zhezhu.access.infrastructure.PersonService;
import com.zhezhu.access.infrastructure.WeChatMessageService;
import com.zhezhu.commons.AssertionConcerns;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.domain.id.wechat.FollowApplyId;
import com.zhezhu.share.domain.id.wechat.FollowAuditId;
import com.zhezhu.share.domain.id.wechat.WeChatId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Slf4j
@Service
public class WeChatApplicationService {

    @Autowired
    private MessageHandler messageHandler;

    @Autowired
    private WeChatConfig weChatConfig;

    @Autowired
    private WebAccessTokenFactory webAccessTokenFactory;

    @Autowired
    private WeChatRepository weChatRepository;

    @Autowired
    private PersonService personService;

    @Autowired
    private FollowApplyRepository applyRepository;

    @Autowired
    private FollowAuditRepository auditRepository;

    @Autowired
    private ApplyAuditService applyAuditService;

    @Autowired
    private WeChatMessageService messageService;

    /**
     * 公众号关注
     *
     * @param params
     * @return
     */
    public String follow(Map<String,String> params){
        log.debug("WeChat Follow Success!");

        WeChatFollow follow = new WeChatFollow();
        follow.create(weChatConfig,params);
        return follow.getContent();
    }

    public String dialog(XmlMessage message){
        log.debug("WeChat Query!");

        XmlOutMessage outMessage = messageHandler.messageHandle(message);
        return outMessage.toXml();
    }

    /**
     * 公众号/小程序信息绑定
     *
     * @param command
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String bind(BindCommand command) {
        log.debug("WeChat Bind {} ",command);
        String openId = command.getWechatOpenId();
        if(openId == null){
            openId = webAccessTokenFactory.newWebAccessToken(command.getCode()).getOpenId();
            command.setWechatOpenId(openId);
        }
        AssertionConcerns.assertArgumentNotNull(openId,"ac-01-000");
        WeChatCategory chatCategory = WeChatCategory.valueOf(command.getCategory());
        WeChat weChat = weChatRepository.findByWeChatOpenIdAndCategoryEquals(openId,chatCategory);
        if(weChat != null){
            return  weChat.getWeChatId().id();
        }
        WeChatId weChatId = weChatRepository.nextIdentity();
        weChat = WeChat.builder()
                .weChatId(weChatId)
                .category(chatCategory)
                .weChatOpenId(openId)
                .phone(command.getPhone())
                .name(command.getName())
                .personId(new PersonId())
                .build();
        weChatRepository.save(weChat);
        return weChatId.id();
    }

    /**
     * 微信用户身份转换
     *
     * @param waChatId
     * @param other
     * @param copyFollowers
     * @return
     */
    public String transferTo(String waChatId,WeChatCategory other,boolean copyFollowers){
        log.debug("WeChat {} transfer to other category {} ",waChatId,other);

        WeChat weChat = weChatRepository.loadOf(new WeChatId(waChatId));
        AssertionConcerns.assertArgumentNotNull(weChat,"ac-01-000");
        WeChat newWeChat = weChat.cloneTo(other);
        if(copyFollowers){
            newWeChat.copyFollowers(weChat);
        }
        weChatRepository.save(newWeChat);
        return newWeChat.getWeChatId().id();
    }

    /**
     * 微信用户复制关注者转换
     *
     * @param waChatId
     * @param otherId
     * @return
     */
    public void copyFollowers(String waChatId,String otherId){
        log.debug("WeChat {} copy followers to other {} ",waChatId,otherId);

        WeChat weChat = weChatRepository.loadOf(new WeChatId(waChatId));
        WeChat other = weChatRepository.loadOf(new WeChatId(otherId));
        AssertionConcerns.assertArgumentNotNull(weChat,"ac-01-000");
        AssertionConcerns.assertArgumentNotNull(other,"ac-01-000");

        other.copyFollowers(weChat);
        weChatRepository.save(other);
    }

    /**
     * 申请关注者
     *
     * @param command
     */
    @Transactional(rollbackFor = Exception.class)
    public void applyFollowers(BindCommand command){
        log.debug("Apply followers from {} ",command.getWechatOpenId());

        if(!command.hasFollowers())
            return ;

        WeChatCategory chatCategory = WeChatCategory.valueOf(command.getCategory());
        WeChat weChat = weChatRepository.findByWeChatOpenIdAndCategoryEquals(command.getWechatOpenId(),chatCategory);
        List<FollowerData> followers = command.getFollowers();
        for(FollowerData data:followers){
            FollowApplyId applyId = applyRepository.nextIdentity();
            FollowApply apply = FollowApply.builder()
                    .applyId(applyId)
                    .applierName(weChat.getName())
                    .applierWeChatId(weChat.getWeChatId())
                    .applierWeChatOpenId(weChat.getWeChatOpenId())
                    .applyDate(DateUtilWrapper.now())
                    .cause(data.getCause())
                    .followerSchoolId(new SchoolId(data.getSchoolId()))
                    .followerClazzId(new ClazzId(data.getClazzId()))
                    .followerId(new PersonId(data.getPersonId()))
                    .build();
            applyRepository.save(apply);

            messageService.notifyNewFollowerApply(apply.getFollowerSchoolId(),
                    apply.getFollowerClazzId(),apply.getFollowerId(),apply.getCause());
        }
    }

    /**
     * 取消关注申请
     *
     * @param applyId
     */
    public void cancelApply(String applyId){
        log.debug("Apply canceled  {} ",applyId);

        FollowApplyId followApplyId = new FollowApplyId(applyId);
        FollowApply apply = applyRepository.loadOf(followApplyId);
        AssertionConcerns.assertArgumentNotNull(apply,"ac-01-005");

        applyRepository.delete(followApplyId);
    }

    /**
     * 审核关注申请
     *
     * @param command
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String applyAudit(ApplyAuditCommand command){
        log.debug("Apply Audit from {} ",command);

        FollowApply apply = applyRepository.loadOf(new FollowApplyId(command.getApplyId()));
        if(apply == null || apply.isAudited())
            return "";

        FollowAudit audit = applyAuditService.auditFollowStudent(new PersonId(command.getAuditorId()),
                apply, command.isOk(), command.getDescription());
        apply.audite(audit);
        auditRepository.save(audit);
        applyRepository.save(apply);

        addFollower(apply);
        return audit.getAuditId().id();
    }

    /**
     * 取消关注申请审核
     *
     * @param command
     */
    @Transactional(rollbackFor = Exception.class)
    public void cancelApplyAudit(ApplyAuditCommand command){
        log.debug("Cancel audit of {} by auditor {} ",command.getAuditId(),command.getAuditorId());

        FollowAudit audit = auditRepository.loadOf(new FollowAuditId(command.getAuditId()));
        if(audit == null)
            return ;

        FollowApply apply = applyRepository.loadOf(new FollowApplyId(command.getApplyId()));
        boolean b = apply.cancel(audit);
        AssertionConcerns.assertArgumentTrue(b,"ac-01-004");

        audit.no(command.getDescription());
        auditRepository.save(audit);
        applyRepository.save(apply);
    }

    private void addFollower(FollowApply apply){
        PersonId followerId = apply.getFollowerId();
        WeChat applyWeChat = weChatRepository.loadOf(apply.getApplierWeChatId());
        applyWeChat.addFollower(followerId,apply.getApplyDate());
        weChatRepository.save(applyWeChat);
    }

    @Transactional(rollbackFor = Exception.class)
    public void bindCancel(BindCommand command) {
        log.debug("WeChat Bind Canceld {} ",command);
        WeChatCategory chatCategory = WeChatCategory.valueOf(command.getCategory());
        AssertionConcerns.assertArgumentNotNull(chatCategory,"ac-01-007");
        WeChat weChat = weChatRepository.findByWeChatOpenIdAndCategoryEquals(command.getWechatOpenId(),chatCategory);
        if(weChat != null)
            weChatRepository.delete(weChat.getWeChatId());
    }

    @Transactional(readOnly = true)
    public WebAccessToken getWeChatAccessToken(String code){
        return webAccessTokenFactory.newWebAccessToken(code);
    }

}