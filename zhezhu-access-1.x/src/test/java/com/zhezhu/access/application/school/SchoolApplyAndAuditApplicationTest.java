package com.zhezhu.access.application.school;

import com.google.common.collect.Lists;
import com.zhezhu.access.domain.model.school.ClazzFollowApply;
import com.zhezhu.access.domain.model.school.ClazzFollowApplyRepository;
import com.zhezhu.access.domain.model.school.ClazzFollowAudit;
import com.zhezhu.access.domain.model.school.ClazzFollowAuditRepository;
import com.zhezhu.access.domain.model.wechat.WeChat;
import com.zhezhu.access.domain.model.wechat.WeChatCategory;
import com.zhezhu.access.domain.model.wechat.WeChatRepository;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.access.ClazzFollowApplyId;
import com.zhezhu.share.domain.id.access.ClazzFollowAuditId;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.infrastructure.school.SchoolService;
import com.zhezhu.share.infrastructure.school.TeacherData;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Copyright (c) 2016,$today.year, 深圳市易考试乐学测评有限公司
 **/
public class SchoolApplyAndAuditApplicationTest {

    @Mock
    private ClazzFollowApplyRepository clazzFollowApplyRepository;

    @Mock
    private ClazzFollowAuditRepository clazzFollowAuditRepository;

    @Mock
    private WeChatRepository weChatRepository;

    @Mock
    private SchoolService schoolService;

    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }

    private SchoolApplyAndAuditApplicationService  getService(){
        SchoolApplyAndAuditApplicationService service = new SchoolApplyAndAuditApplicationService(clazzFollowApplyRepository,clazzFollowAuditRepository,weChatRepository,schoolService);
        return spy(service);
    }

    @Test
    public void followClazzApply() {
        SchoolApplyAndAuditApplicationService service = getService();
        ClazzFollowApplyId applyId = new ClazzFollowApplyId();
        when(clazzFollowApplyRepository.nextIdentity()).thenReturn(applyId);
        doNothing().when(clazzFollowApplyRepository).save(any(ClazzFollowApply.class));
        ClazzFollowApplyCommand command = ClazzFollowApplyCommand.builder()
                .applierId(new PersonId().id())
                .applierName("Applier")
                .applierPhone("021564789")
                .applyingClazzId(new ClazzId().id())
                .applyingSchoolId(new SchoolId().id())
                .build();

        service.followClazzApply(command);

        verify(clazzFollowApplyRepository,times(1)).nextIdentity();
        verify(clazzFollowApplyRepository,times(1)).save(any());

    }

    @Test
    public void followClazzApplyCancel() {
        SchoolApplyAndAuditApplicationService service = getService();
        ClazzFollowApply apply = mock(ClazzFollowApply.class);

        when(clazzFollowApplyRepository.loadOf(any(ClazzFollowApplyId.class))).thenReturn(apply);
        when(apply.isAudited()).thenReturn(true);
        doNothing().when(clazzFollowApplyRepository).delete(any(ClazzFollowApplyId.class));

        service.followClazzApplyCancel(new ClazzFollowApplyId().id());

        verify(clazzFollowApplyRepository,times(1)).loadOf(any(ClazzFollowApplyId.class));
        verify(clazzFollowApplyRepository,times(1)).delete(any(ClazzFollowApplyId.class));
    }

    @Test
    public void followClazzAudit(){
        SchoolApplyAndAuditApplicationService service = getService();
        ClazzFollowAuditCommand command = ClazzFollowAuditCommand.builder()
                .applyId(new ClazzFollowApplyId().id())
                .auditorId(new PersonId().id())
                .description("Description")
                .ok(true)
                .build();

        ClazzFollowAuditId auditId = mock(ClazzFollowAuditId.class);
        ClazzFollowApply apply = mock(ClazzFollowApply.class);
        when(apply.isAudited()).thenReturn(true);
        when(apply.getApplierId()).thenReturn(new PersonId());
        when(apply.getApplyingClazzId()).thenReturn(new ClazzId());
        doNothing().when(apply).updateApplierId(any(PersonId.class));

        when(clazzFollowAuditRepository.nextIdentity()).thenReturn(auditId);
        when(clazzFollowApplyRepository.loadOf(any(ClazzFollowApplyId.class))).thenReturn(apply);
        doNothing().when(apply).audit(any(ClazzFollowAudit.class));
        doNothing().when(clazzFollowAuditRepository).save(any(ClazzFollowAudit.class));
        doNothing().when(clazzFollowApplyRepository).save(any(ClazzFollowApply.class));
        WeChat weChat = mock(WeChat.class);
        when(weChat.getName()).thenReturn("Wname1");
        when(weChat.getPhone()).thenReturn("Phone1");
        doNothing().when(weChat).upatePersonId(any(PersonId.class));
        when(weChatRepository.findByPersonIdAndCategoryEquals(any(PersonId.class), eq(WeChatCategory.Teacher))).thenReturn(weChat);
        List<TeacherData> teachers = Lists.newArrayList();
        for(int i=0;i<5;i++){
            TeacherData teacher = mock(TeacherData.class);
            if(i%3==0){
                when(teacher.sameNameAs(any(String.class))).thenReturn(true);
                when(teacher.samePhoneAs(any(String.class))).thenReturn(true);
            }else {
                when(teacher.sameNameAs(any(String.class))).thenReturn(false);
                when(teacher.samePhoneAs(any(String.class))).thenReturn(false);
            }
            when(teacher.getPersonId()).thenReturn(new PersonId().id());
            teachers.add(teacher);
        }
        doNothing().when(weChatRepository).save(any(WeChat.class));
        when(schoolService.getClazzTeachers(any(ClazzId.class))).thenReturn(teachers);

        service.followClazzAudit(command);

        verify(clazzFollowApplyRepository,times(1)).loadOf(any(ClazzFollowApplyId.class));
        verify(clazzFollowAuditRepository,times(1)).nextIdentity();
        verify(clazzFollowAuditRepository,times(1)).save(any());
        verify(clazzFollowApplyRepository,times(1)).save(any());
        verify(weChatRepository,times(1)).save(any());
        verify(apply,times(1)).updateApplierId(any());
    }

    @Test
    public void followClazzAuditCancel(){
        SchoolApplyAndAuditApplicationService service = getService();

        ClazzFollowAudit audit = mock(ClazzFollowAudit.class);
        ClazzFollowApply apply = mock(ClazzFollowApply.class);

        when(clazzFollowApplyRepository.loadOf(any(ClazzFollowApplyId.class))).thenReturn(apply);
        when(clazzFollowAuditRepository.loadOf(any(ClazzFollowAuditId.class))).thenReturn(audit);
        when(audit.getApplyId()).thenReturn(new ClazzFollowApplyId());
        doNothing().when(apply).cancelAudit(eq(audit));
        doNothing().when(clazzFollowApplyRepository).save(any());
        doNothing().when(clazzFollowAuditRepository).delete(any());

        service.followClazzAuditCancel(new ClazzFollowAuditId().id());

        verify(clazzFollowApplyRepository,times(1)).loadOf(any(ClazzFollowApplyId.class));
        verify(clazzFollowAuditRepository,times(1)).loadOf(any(ClazzFollowAuditId.class));
        verify(clazzFollowAuditRepository,times(1)).delete(any());
        verify(clazzFollowApplyRepository,times(1)).save(any());
    }
}