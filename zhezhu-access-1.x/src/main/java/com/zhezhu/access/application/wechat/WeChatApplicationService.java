package com.zhezhu.access.application.wechat;

import com.zhezhu.access.domain.model.wechat.*;
import com.zhezhu.access.domain.model.wechat.audit.*;
import com.zhezhu.access.domain.model.wechat.config.WeChatConfig;
import com.zhezhu.access.domain.model.wechat.message.MessageHandler;
import com.zhezhu.access.domain.model.wechat.message.XmlMessage;
import com.zhezhu.access.domain.model.wechat.message.XmlOutMessage;
import com.zhezhu.access.infrastructure.PersonService;
import com.zhezhu.access.infrastructure.WeChatMessageService;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.domain.id.wechat.FollowApplyId;
import com.zhezhu.share.domain.id.wechat.WeChatId;
import com.zhezhu.share.domain.person.Gender;
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

        WeChatId weChatId = weChatRepository.nextIdentity();
        WeChat weChat = WeChat.builder()
                .weChatId(weChatId)
                .category(WeChatCategory.valueOf(command.getCategory()))
                .weChatOpenId(command.getWechatOpenId())
                .phone(command.getPhone())
                .name(command.getName())
                .personId(personService.getPersonId(command.getWechatOpenId()))
                .build();
        weChatRepository.save(weChat);
        return weChatId.id();
    }

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

    private void addFollower(FollowApply apply){
        PersonId followerId = apply.getFollowerId();
        WeChat applyWeChat = weChatRepository.loadOf(apply.getApplierWeChatId());

        Follower follower = Follower.builder()
                .personId(followerId)
                .followDate(apply.getApplyDate())
                .build();
        applyWeChat.addFollower(follower);
        weChatRepository.save(applyWeChat);
    }

    @Transactional(rollbackFor = Exception.class)
    public void bindCancel(BindCommand command) {
        log.debug("WeChat Bind Canceld {} ",command);
        WeChatCategory chatCategory = WeChatCategory.valueOf(command.getName());
        WeChat weChat = weChatRepository.findByWeChatOpenIdAndCategoryEquals(command.getWechatOpenId(),chatCategory);
        if(weChat != null)
            weChatRepository.delete(weChat.getWeChatId());
    }

    public WebAccessToken getWeChatAccessToken(String code){
        WeChatService weChatService = new WeChatService(code);
        return weChatService.getWebAccessToken(weChatConfig);
    }
}