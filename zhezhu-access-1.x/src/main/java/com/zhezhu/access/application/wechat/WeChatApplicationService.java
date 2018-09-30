package com.zhezhu.access.application.wechat;

import com.zhezhu.access.domain.model.wechat.*;
import com.zhezhu.access.domain.model.wechat.audit.*;
import com.zhezhu.access.domain.model.wechat.config.WeChatConfig;
import com.zhezhu.access.domain.model.wechat.message.MessageHandler;
import com.zhezhu.access.domain.model.wechat.message.XmlMessage;
import com.zhezhu.access.domain.model.wechat.message.XmlOutMessage;
import com.zhezhu.access.infrastructure.WeChatMessageService;
import com.zhezhu.commons.AssertionConcerns;
import com.zhezhu.commons.util.CollectionsUtilWrapper;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.domain.id.wechat.FollowApplyId;
import com.zhezhu.share.domain.id.wechat.FollowAuditId;
import com.zhezhu.share.domain.id.wechat.WeChatId;
import com.zhezhu.share.infrastructure.school.SchoolService;
import com.zhezhu.share.infrastructure.school.StudentData;
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

    private MessageHandler messageHandler;

    private WeChatConfig weChatConfig;

    private WebAccessTokenFactory webAccessTokenFactory;

    private WeChatRepository weChatRepository;

    private FollowApplyRepository applyRepository;

    private FollowAuditRepository auditRepository;

    private ApplyAuditService applyAuditService;

    private WeChatMessageService messageService;

    private SchoolService schoolService;

    @Autowired
    public WeChatApplicationService(MessageHandler messageHandler, WeChatConfig weChatConfig,
                                    WebAccessTokenFactory webAccessTokenFactory, WeChatRepository weChatRepository,
                                    FollowApplyRepository applyRepository, FollowAuditRepository auditRepository,
                                    ApplyAuditService applyAuditService, WeChatMessageService messageService,
                                    SchoolService schoolService) {
        this.messageHandler = messageHandler;
        this.weChatConfig = weChatConfig;
        this.webAccessTokenFactory = webAccessTokenFactory;
        this.weChatRepository = weChatRepository;
        this.applyRepository = applyRepository;
        this.auditRepository = auditRepository;
        this.applyAuditService = applyAuditService;
        this.messageService = messageService;
        this.schoolService = schoolService;
    }

    /**
     * 公众号关注
     *
     * @param params properties of message args
     * @return welcome message
     */
    public String follow(Map<String, String> params) {
        log.debug("WeChat Follow Success!");

        WeChatFollow follow = new WeChatFollow();
        follow.create(weChatConfig, params);
        return follow.getContent();
    }

    /**
     * 公众号对话
     *
     * @param message {@link XmlMessage}
     * @return a xml{@link XmlOutMessage} message
     */
    public String dialog(XmlMessage message) {
        log.debug("WeChat Query!");

        XmlOutMessage outMessage = messageHandler.messageHandle(message);
        return outMessage.toXml();
    }

    /**
     * 公众号/小程序用户绑定
     *
     * @param command {@link BindCommand}
     * @return {@link WeChatId}
     */
    @Transactional(rollbackFor = Exception.class)
    public String bind(BindCommand command) {
        log.debug("WeChat Bind {} ", command);
        String openId = command.getWechatOpenId();
        if (openId == null) {
            openId = webAccessTokenFactory.newWebAccessToken(command.getCode()).getOpenId();
            command.setWechatOpenId(openId);
        }
        AssertionConcerns.assertArgumentNotNull(openId, "ac-01-000");
        WeChatCategory chatCategory = WeChatCategory.valueOf(command.getCategory());
        WeChat weChat = weChatRepository.findByWeChatOpenIdAndCategoryEquals(openId, chatCategory);
        if (weChat != null) {
            return weChat.getWeChatId().id();
        }
        PersonId personId = new PersonId();
        List<WeChat> weChats = weChatRepository.findAllByWeChatOpenId(openId);
        if(CollectionsUtilWrapper.isNotNullAndNotEmpty(weChats)){
            personId = weChats.get(0).getPersonId();
        }
        WeChatId weChatId = weChatRepository.nextIdentity();
        weChat = WeChat.builder()
                .weChatId(weChatId)
                .category(chatCategory)
                .weChatOpenId(openId)
                .phone(command.getPhone())
                .name(command.getName())
                .personId(personId)
                .build();
        weChatRepository.save(weChat);
        return weChatId.id();
    }

    /**
     * 微信用户身份转换
     *
     * @param waChatId {@link WeChatId} 转换者
     * @param other {@link WeChatCategory} 转换类型
     * @param copyFollowers 是否自制另一个类型的微信用户关注者
     * @return {@link WeChatId}
     */
    public String transferTo(String waChatId, WeChatCategory other, boolean copyFollowers) {
        log.debug("WeChat {} transfer to other category {} ", waChatId, other);

        WeChat weChat = weChatRepository.loadOf(new WeChatId(waChatId));
        AssertionConcerns.assertArgumentNotNull(weChat, "ac-01-000");
        WeChat newWeChat = weChat.cloneTo(other);
        if (copyFollowers) {
            newWeChat.copyFollowers(weChat);
        }
        weChatRepository.save(newWeChat);
        return newWeChat.getWeChatId().id();
    }

    /**
     * 微信用户复制关注者转换
     *
     * @param waChatId waChatId {@link WeChatId} 被转换者
     * @param otherId  {@link WeChatId} 转换者
     *
     */
    public void copyFollowers(String waChatId, String otherId) {
        log.debug("WeChat {} copy followers to other {} ", waChatId, otherId);

        WeChat weChat = weChatRepository.loadOf(new WeChatId(waChatId));
        WeChat other = weChatRepository.loadOf(new WeChatId(otherId));
        AssertionConcerns.assertArgumentNotNull(weChat, "ac-01-000");
        AssertionConcerns.assertArgumentNotNull(other, "ac-01-000");

        other.copyFollowers(weChat);
        weChatRepository.save(other);
    }

    /**
     * 申请关注者
     *
     * @param command {@link BindCommand}
     */
    @Transactional(rollbackFor = Exception.class)
    public void applyFollowers(BindCommand command) {
        log.debug("Apply followers from {} ", command.getWechatOpenId());

        if (!command.hasFollowers())
            return;

        WeChatCategory chatCategory = WeChatCategory.valueOf(command.getCategory());
        //学生用户只能申请一个关注者
        if(chatCategory.equals(WeChatCategory.Student))
            AssertionConcerns.assertArgumentTrue(command.getFollowers().size()==1, "ac-01-000");

        WeChat weChat = weChatRepository.findByWeChatOpenIdAndCategoryEquals(command.getWechatOpenId(), chatCategory);
        List<FollowerData> followers = command.getFollowers();
        for (FollowerData data : followers) {
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
                    apply.getFollowerClazzId(), apply.getFollowerId(), apply.getCause());
        }
    }

    /**
     * 取消关注申请
     *
     * @param applyId {@link FollowApplyId} 关注申请唯一标识
     */
    public void cancelApply(String applyId) {
        log.debug("Apply canceled  {} ", applyId);

        FollowApplyId followApplyId = new FollowApplyId(applyId);
        FollowApply apply = applyRepository.loadOf(followApplyId);
        AssertionConcerns.assertArgumentNotNull(apply, "ac-01-005");

        applyRepository.delete(followApplyId);
    }

    /**
     * 审核关注申请
     *
     * @param command {@link ApplyAuditCommand}
     * @return value of {@link FollowAuditId}
     */
    @Transactional(rollbackFor = Exception.class)
    public String applyAudit(ApplyAuditCommand command) {
        log.debug("Apply Audit from {} ", command);

        FollowApply apply = applyRepository.loadOf(new FollowApplyId(command.getApplyId()));
        if (apply == null || apply.isAudited())
            return "";

        StudentData student = schoolService.getStudentBy(apply.getFollowerId());
        if(student == null)
            return "";

        if(!student.sameManagedClazzOf(apply.getFollowerClazzId().id())){
            return "";
        }

        apply.updateFollowerId(new PersonId(student.getPersonId()));

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
     * @param command {@link ApplyAuditCommand}
     */
    @Transactional(rollbackFor = Exception.class)
    public void cancelApplyAudit(ApplyAuditCommand command) {
        log.debug("Cancel audit of {} by auditor {} ", command.getAuditId(), command.getAuditorId());

        FollowAudit audit = auditRepository.loadOf(new FollowAuditId(command.getAuditId()));
        if (audit == null)
            return;

        FollowApply apply = applyRepository.loadOf(new FollowApplyId(command.getApplyId()));
        boolean b = apply.cancel(audit);
        AssertionConcerns.assertArgumentTrue(b, "ac-01-004");

        audit.no(command.getDescription());
        auditRepository.save(audit);
        applyRepository.save(apply);
    }

    private void addFollower(FollowApply apply) {
        PersonId followerId = apply.getFollowerId();
        WeChat applyWeChat = weChatRepository.loadOf(apply.getApplierWeChatId());
        applyWeChat.addFollower(followerId, apply.getApplyDate());
        weChatRepository.save(applyWeChat);
    }

    /**
     *  取消用户身份绑定
     * @param command {@link BindCommand}
     */
    @Transactional(rollbackFor = Exception.class)
    public void bindCancel(BindCommand command) {
        log.debug("WeChat Bind Canceld {} ", command);
        WeChatCategory chatCategory = WeChatCategory.valueOf(command.getCategory());
        AssertionConcerns.assertArgumentNotNull(chatCategory, "ac-01-007");
        WeChat weChat = weChatRepository.findByWeChatOpenIdAndCategoryEquals(command.getWechatOpenId(), chatCategory);
        if (weChat != null)
            weChatRepository.delete(weChat.getWeChatId());
    }

    /**
     * 获取微信用户访问授权
     *
     * @param code 微信用户临时凭证
     * @return {@link WebAccessToken}
     */
    @Transactional(readOnly = true)
    public WebAccessToken getWeChatAccessToken(String code) {
        return webAccessTokenFactory.newWebAccessToken(code);
    }

}