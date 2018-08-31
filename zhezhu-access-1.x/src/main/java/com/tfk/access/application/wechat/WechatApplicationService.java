package com.tfk.access.application.wechat;

import com.tfk.access.domain.model.wechat.*;
import com.tfk.access.domain.model.wechat.audit.*;
import com.tfk.access.domain.model.wechat.config.WeChatConfig;
import com.tfk.access.domain.model.wechat.message.MessageHandler;
import com.tfk.access.domain.model.wechat.message.XmlMessage;
import com.tfk.access.domain.model.wechat.message.XmlOutMessage;
import com.tfk.access.infrastructure.PersonService;
import com.tfk.access.infrastructure.WeChatMessageService;
import com.tfk.commons.util.DateUtilWrapper;
import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.SchoolId;
import com.tfk.share.domain.id.wechat.FollowApplyId;
import com.tfk.share.domain.id.wechat.WeChatId;
import com.tfk.share.domain.person.Gender;
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
public class WechatApplicationService {

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

    @Transactional(rollbackFor = Exception.class)
    public void bind(BindCommand command) {
        log.debug("WeChat Bind {} ",command);

        WeChatId weChatId = weChatRepository.nextIdentity();
        WeChat weChat = WeChat.builder()
                .weChatId(weChatId)
                .weChatOpenId(command.getWechatOpenId())
                .phone(command.getPhone())
                .name(command.getName())
                .personId(personService.getPersonId(command.getWechatOpenId()))
                .build();
        List<FollowerData> followers = command.getFollowers();
        addFollowers(followers,weChat);
        weChatRepository.save(weChat);
    }

    @Transactional(rollbackFor = Exception.class)
    public void applyFollowers(BindCommand command){
        log.debug("Apply followers from {} ",command.getWechatOpenId());

        WeChat weChat = weChatRepository.findByWeChatOpenId(command.getWechatOpenId());
        List<FollowerData> followers = command.getFollowers();
        if(followers == null)
            return ;

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

        //addFollowers(followers,weChat);
        //weChatRepository.save(weChat);
    }

    public void applyAudit(ApplyAuditCommand command){
        log.debug("Apply Audit from {} ",command);

        FollowApply apply = applyRepository.loadOf(new FollowApplyId(command.getApplyId()));
        if(apply == null || apply.isAudited())
            return;

        FollowAudit audit = applyAuditService.auditFollowStudent(new PersonId(command.getAuditorId()),
                apply, command.isOk(), command.getDescription());
        apply.audite(audit);
        auditRepository.save(audit);
        applyRepository.save(apply);

    }

    private void addFollowers(List<FollowerData> followers,WeChat weChat){
        if(followers != null){
            followers.forEach(data->{
                PersonId personId = personService.getPersonId(data.getSchoolId(),data.getClazzId(),data.getName(),
                        Gender.valueOf(data.getGender()),PersonService.QueryTarget.Student);
                Follower follower = Follower.builder()
                        .personId(personId)
                        .followDate(DateUtilWrapper.now())
                        .build();
                weChat.addFollower(follower);
            });
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void bindCancel(BindCommand command) {
        log.debug("WeChat Bind Canceld {} ",command);
        WeChat weChat = weChatRepository.findByWeChatOpenId(command.getWechatOpenId());
        if(weChat != null)
            weChatRepository.delete(weChat.getWeChatId());
    }

    public WebAccessToken getWeChatAccessToken(String code){
        WeChatService weChatService = new WeChatService(code);
        return weChatService.getWebAccessToken(weChatConfig);
    }
}