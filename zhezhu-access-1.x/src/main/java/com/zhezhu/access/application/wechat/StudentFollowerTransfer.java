package com.zhezhu.access.application.wechat;

import com.zhezhu.access.domain.model.wechat.Follower;
import com.zhezhu.access.domain.model.wechat.WeChat;
import com.zhezhu.access.domain.model.wechat.WeChatCategory;
import com.zhezhu.access.domain.model.wechat.WeChatRepository;
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

    @Autowired
    public StudentFollowerTransfer(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @Override
    public FollowerData trans(Follower follower, WeChatCategory category) {
        if(!category.equals(WeChatCategory.Student)){
            return null;
        }
        WeChat weChat = follower.getWeChat();
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
}