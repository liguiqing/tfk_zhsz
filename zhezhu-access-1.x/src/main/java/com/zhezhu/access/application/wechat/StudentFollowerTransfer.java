package com.zhezhu.access.application.wechat;

import com.zhezhu.access.domain.model.wechat.Follower;
import com.zhezhu.access.domain.model.wechat.WeChatCategory;
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

    @Autowired
    private SchoolService schoolService;

    @Override
    public FollowerData trans(Follower follower, WeChatCategory category) {
        if(!category.equals(WeChatCategory.Student)){
            return null;
        }

        StudentData student = schoolService.getStudentBy(follower.getPersonId());
        FollowerData data = FollowerData.builder()
                .personId(student.getPersonId())
                .schoolId(student.getSchoolId())
                .clazzId(student.getManagedClazzId())
                .name(student.getName())
                .gender(student.getGender())
                .build();
        return data;
    }
}