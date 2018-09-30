package com.zhezhu.access.application.wechat;

import com.google.common.collect.Lists;
import com.zhezhu.access.domain.model.wechat.Follower;
import com.zhezhu.access.domain.model.wechat.WeChat;
import com.zhezhu.access.domain.model.wechat.WeChatCategory;
import com.zhezhu.access.domain.model.wechat.WeChatRepository;
import com.zhezhu.access.domain.model.wechat.audit.FollowApply;
import com.zhezhu.access.domain.model.wechat.audit.FollowApplyRepository;
import com.zhezhu.access.domain.model.wechat.audit.FollowAuditRepository;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.infrastructure.school.ClazzData;
import com.zhezhu.share.infrastructure.school.SchoolData;
import com.zhezhu.share.infrastructure.school.SchoolService;
import com.zhezhu.share.infrastructure.school.StudentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Component
public class StudentFollowerTransfer implements FollowerDataTransfer {

    private SchoolService schoolService;

    private WeChatRepository weChatRepository;

    @Autowired
    public StudentFollowerTransfer(SchoolService schoolService,
                                   WeChatRepository weChatRepository) {
        this.schoolService = schoolService;
        this.weChatRepository = weChatRepository;
    }

    @Override
    public FollowerData trans(Follower follower, WeChatCategory category) {
        if(!category.equals(WeChatCategory.Student)){
            return null;
        }
        WeChat weChat = weChatRepository.loadOf(follower.getWeChatId());
        StudentData student = schoolService.getStudentBy(follower.getPersonId());
        ClazzData clazzData = schoolService.getClazz(new ClazzId(student.getManagedClazzId()));
        SchoolData schoolData = schoolService.getSchool(new SchoolId(student.getSchoolId()));
        FollowerData data = FollowerData.builder()
                .schoolId(student.getSchoolId())
                .schoolName(schoolData.getName())
                .gradeName(clazzData.getGradeName())
                .clazzId(student.getManagedClazzId())
                .clazzName(clazzData.getClazzName())
                .personId(student.getPersonId())
                .name(student.getName())
                .gender(student.getGender())
                .applierName(weChat.getName())
                .applierId(weChat.getPersonId().id())
                .applierPhone(weChat.getPhone())
                .build();
        return data;
    }

    @Override
    public FollowerData trans(FollowApply apply, WeChatCategory category) {
        if(!category.equals(WeChatCategory.Student)){
            return null;
        }

        WeChat weChat = weChatRepository.loadOf(apply.getApplierWeChatId());
        StudentData student = schoolService.getStudentBy(apply.getFollowerId());
        ClazzData clazzData = schoolService.getClazz(new ClazzId(student.getManagedClazzId()));
        SchoolData schoolData = schoolService.getSchool(new SchoolId(student.getSchoolId()));
        boolean canBeOk = student.hasCredentials(apply.getApplyCredential());
        FollowerData data = FollowerData.builder()
                .schoolId(student.getSchoolId())
                .schoolName(schoolData.getName())
                .gradeName(clazzData.getGradeName())
                .clazzId(student.getManagedClazzId())
                .clazzName(clazzData.getClazzName())
                .personId(student.getPersonId())
                .name(student.getName())
                .gender(student.getGender())
                .applierName(apply.getApplierName())
                .applierId(weChat.getPersonId().id())
                .applierPhone(weChat.getPhone())
                .applyId(apply.getApplyId().id())
                .cause(apply.getCause())
                .studentNo(apply.getApplyCredential())
                .canBeOk(canBeOk)
                .build();
        return data;
    }
}