package com.zhezhu.access.application.wechat;

import com.zhezhu.access.domain.model.wechat.Follower;
import com.zhezhu.access.domain.model.wechat.WeChat;
import com.zhezhu.access.domain.model.wechat.WeChatCategory;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.domain.person.Gender;
import com.zhezhu.share.infrastructure.school.ClazzData;
import com.zhezhu.share.infrastructure.school.SchoolData;
import com.zhezhu.share.infrastructure.school.SchoolService;
import com.zhezhu.share.infrastructure.school.StudentData;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Copyright (c) 2016,$today.year, 深圳市易考试乐学测评有限公司
 **/
public class StudentFollowerTransferTest {

    @Mock
    private SchoolService schoolService;

    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }

    private StudentFollowerTransfer getService(){
        StudentFollowerTransfer transfer = new StudentFollowerTransfer(schoolService);
        return spy(transfer);
    }

    @Test
    public void trans(){
        StudentFollowerTransfer transfer = getService();
        List<ClazzData> clazzs = mock(List.class);
        ClazzData clazz = ClazzData.builder().clazzId(new ClazzId().id()).build();
        SchoolData school = SchoolData.builder().schoolId(new SchoolId().id()).build();
        when(clazzs.get(0)).thenReturn(clazz).thenReturn(clazz);
        when(clazzs.size()).thenReturn(1);
        StudentData student = StudentData.builder()
                .schoolId(new SchoolId().id())
                .clazzes(clazzs)
                .name("S1")
                .personId(new PersonId().id())
                .gender(Gender.Female.name())
                .build();

        when(schoolService.getStudentBy(any(PersonId.class))).thenReturn(student);
        when(schoolService.getSchool(any(SchoolId.class))).thenReturn(school);
        when(schoolService.getClazz(any(ClazzId.class))).thenReturn(clazz);
        Follower follower = mock(Follower.class);
        WeChat weChat = mock(WeChat.class);
        when(follower.getPersonId()).thenReturn(new PersonId(student.getPersonId()));
        when(follower.getWeChat()).thenReturn(weChat);
        when(weChat.getPersonId()).thenReturn(new PersonId());

        FollowerData data = transfer.trans(follower, WeChatCategory.Parent);
        assertNull(data);
        data = transfer.trans(follower, WeChatCategory.Parent);
        assertNull(data);
        data = transfer.trans(follower, WeChatCategory.Student);
        assertNotNull(data);
        assertEquals(student.getName(),data.getName());
        assertEquals(student.getSchoolId(),data.getSchoolId());
        assertEquals(student.getClazzes().get(0).getClazzId(),data.getClazzId());
        assertEquals(student.getGender(),data.getGender());
        assertEquals(student.getPersonId(),data.getPersonId());
    }
}