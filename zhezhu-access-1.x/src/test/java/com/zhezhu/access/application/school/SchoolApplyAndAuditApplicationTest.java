package com.zhezhu.access.application.school;

import com.zhezhu.access.domain.model.school.ClazzFollowApply;
import com.zhezhu.access.domain.model.school.ClazzFollowApplyRepository;
import com.zhezhu.access.domain.model.school.ClazzFollowAudit;
import com.zhezhu.access.domain.model.school.ClazzFollowAuditRepository;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.access.ClazzFollowApplyId;
import com.zhezhu.share.domain.id.access.ClazzFollowAuditId;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

/**
 * Copyright (c) 2016,$today.year, 深圳市易考试乐学测评有限公司
 **/
public class SchoolApplyAndAuditApplicationTest {

    @Mock
    private ClazzFollowApplyRepository clazzFollowApplyRepository;

    @Mock
    private ClazzFollowAuditRepository clazzFollowAuditRepository;

    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }

    private SchoolApplyAndAuditApplicationService  getService()throws Exception{
        SchoolApplyAndAuditApplicationService service = new SchoolApplyAndAuditApplicationService();
        FieldUtils.writeField(service,"clazzFollowApplyRepository",clazzFollowApplyRepository,true);
        FieldUtils.writeField(service,"clazzFollowAuditRepository",clazzFollowAuditRepository,true);
        return spy(service);
    }

    @Test
    public void followClazzApply() throws Exception{
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
    public void followClazzApplyCancel() throws Exception{
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
    public void followClazzAudit() throws Exception{
        SchoolApplyAndAuditApplicationService service = getService();
        ClazzFollowAuditCommand command = ClazzFollowAuditCommand.builder()
                .applyId(new ClazzFollowApplyId().id())
                .auditDate(DateUtilWrapper.now())
                .auditorId(new PersonId().id())
                .description("Description")
                .ok(true)
                .build();

        ClazzFollowAuditId auditId = mock(ClazzFollowAuditId.class);
        ClazzFollowApply apply = mock(ClazzFollowApply.class);
        when(clazzFollowAuditRepository.nextIdentity()).thenReturn(auditId);
        when(clazzFollowApplyRepository.loadOf(any(ClazzFollowApplyId.class))).thenReturn(apply);
        when(apply.isAudited()).thenReturn(true);
        doNothing().when(clazzFollowAuditRepository).save(any(ClazzFollowAudit.class));
        doNothing().when(apply).audit(any(ClazzFollowAudit.class));
        doNothing().when(clazzFollowApplyRepository).save(any(ClazzFollowApply.class));

        service.followClazzAudit(command);

        verify(clazzFollowApplyRepository,times(1)).loadOf(any(ClazzFollowApplyId.class));
        verify(clazzFollowAuditRepository,times(1)).nextIdentity();
        verify(clazzFollowAuditRepository,times(1)).save(any());
        verify(clazzFollowApplyRepository,times(1)).save(any());
    }

    @Test
    public void followClazzAuditCancel() throws Exception{
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