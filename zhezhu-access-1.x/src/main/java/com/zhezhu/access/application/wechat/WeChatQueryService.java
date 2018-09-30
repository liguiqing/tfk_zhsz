package com.zhezhu.access.application.wechat;

import com.zhezhu.access.domain.model.wechat.Follower;
import com.zhezhu.access.domain.model.wechat.WeChat;
import com.zhezhu.access.domain.model.wechat.WeChatCategory;
import com.zhezhu.access.domain.model.wechat.WeChatRepository;
import com.zhezhu.access.domain.model.wechat.audit.FollowApply;
import com.zhezhu.access.domain.model.wechat.audit.FollowApplyRepository;
import com.zhezhu.access.domain.model.wechat.audit.FollowAuditRepository;
import com.zhezhu.commons.util.CollectionsUtilWrapper;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.wechat.WeChatId;
import com.zhezhu.share.domain.person.Gender;
import com.zhezhu.share.infrastructure.school.SchoolService;
import com.zhezhu.share.infrastructure.school.StudentData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 微信小程序或者公众号查询服务
 *
 * @author Liguiqing
 * @since V3.0
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class WeChatQueryService {

    private WeChatRepository weChatRepository;

    private FollowerTransferHelper followerTransferHelper;

    private SchoolService schoolService;

    private FollowApplyRepository applyRepository;

    private FollowAuditRepository auditRepository;

    @Autowired
    public WeChatQueryService(WeChatRepository weChatRepository,
                              FollowerTransferHelper followerTransferHelper,
                              SchoolService schoolService,
                              FollowApplyRepository applyRepository,
                              FollowAuditRepository auditRepository) {
        this.weChatRepository = weChatRepository;
        this.followerTransferHelper = followerTransferHelper;
        this.schoolService = schoolService;
        this.applyRepository = applyRepository;
        this.auditRepository = auditRepository;
    }

    /**
     * 查询微信OpenId与系统的绑定关系
     *
     * @param weChatOpenId 微信OpenId
     * @return list of {@link WeChatData}
     */
    public List<WeChatData> getWeChats(String weChatOpenId) {
        log.debug("Get Wechats by OpenId {}", weChatOpenId);

        return this.weChatRepository.findAllByWeChatOpenId(weChatOpenId)
                .stream().map(weChat -> WeChatData.builder()
                        .personId(weChat.getPersonId().id())
                        .name(weChat.getName())
                        .openId(weChat.getWeChatOpenId())
                        .phone(weChat.getPhone())
                        .role(weChat.getCategory().name())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 取得已经通过审核的关注者
     *
     * @param weChatOpenId 微信OpenId
     * @param category {@link WeChatCategory}
     * @param isAudited 审核结果
     * @return list of {@link FollowerData}
     */
    public List<FollowerData> getFollowers(String weChatOpenId, WeChatCategory category,boolean isAudited) {
        log.debug("Get Followers by OpenId {}", weChatOpenId);

        WeChat weChat = this.weChatRepository.findByWeChatOpenIdAndCategoryEquals(weChatOpenId, category);
        if (weChat == null || CollectionsUtilWrapper.isNullOrEmpty(weChat.getFollowers()))
            return new ArrayList<>();

        return weChat.getFollowers().stream().filter(follower -> follower.isAudited() == isAudited).map(follower ->
                followerTransferHelper.transTo(follower, category)
        ).collect(Collectors.toList());
    }

    /**
     * 查询可申请的关注者<br>
     *
     * @param name 被查询人姓名
     * @param clazzId 被查询人班级ID
     * @param credentialsName 被查询人证件名称
     * @param credentialsValue 被查询人证件号码
     * @param gender 被查询人姓名
     * @return Array of 被查询人personId {@link com.zhezhu.share.domain.id.PersonId}
     */
    public String[] findFollowerBy(String name, String clazzId, String credentialsName, String credentialsValue, Gender gender) {
        log.debug("Get Follower  by {} and clazzId {}", name, clazzId);

        List<StudentData> studentData = schoolService.getClazzStudents(new ClazzId(clazzId));
        if (CollectionsUtilWrapper.isNullOrEmpty(studentData))
            return new String[]{};

        List<StudentData> names = studentData.stream()
                .filter(student -> student.getName().equals(name))
                .collect(Collectors.toList());
        if (CollectionsUtilWrapper.isNullOrEmpty(names))
            return new String[]{};
        if (names.size() == 1)
            return new String[]{names.get(0).getPersonId()};

        List<StudentData> namesAnGender = names.stream()
                .filter(student -> {
                    if (gender == null)
                        return true;
                    return student.sameGenderAs(gender);
                }).collect(Collectors.toList());
        if (CollectionsUtilWrapper.isNullOrEmpty(namesAnGender))
            return new String[]{};
        if (namesAnGender.size() == 1)
            return new String[]{namesAnGender.get(0).getPersonId()};
        if (credentialsName == null)
            return namesAnGender.stream().map(StudentData::getPersonId).collect(Collectors.toList()).toArray(new String[]{});


        List<StudentData> namesAnGenderAndCredentials = namesAnGender.stream()
                .filter(student -> student.hasCredentials(credentialsName, credentialsValue))
                .collect(Collectors.toList());
        if (CollectionsUtilWrapper.isNullOrEmpty(namesAnGenderAndCredentials))
            return new String[]{};

        return namesAnGenderAndCredentials.stream().map(StudentData::getPersonId).collect(Collectors.toList()).toArray(new String[]{});
    }

    /**
     * 查询全部学生关注申请
     *
     * @param page 页码
     * @param size 页容
     * @param isAudited 是否审核
     * @return
     */
    public List<FollowerData> getAllFollowers(int page, int size,boolean isAudited) {
        List<FollowApply> applies = null;
        if(isAudited){
            applies = applyRepository.findAllByAuditIdIsNotNull(page, size);
        }else{
            applies = applyRepository.findAllByAuditIdIsNull(page, size);
        }

        if(CollectionsUtilWrapper.isNullOrEmpty(applies))
            return new ArrayList<>();

        return applies.stream().map(apply -> followerTransferHelper.transTo(apply, WeChatCategory.Student)).collect(Collectors.toList());
    }
}