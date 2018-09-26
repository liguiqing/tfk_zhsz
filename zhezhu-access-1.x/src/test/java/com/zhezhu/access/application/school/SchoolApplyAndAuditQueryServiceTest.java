package com.zhezhu.access.application.school;

import com.zhezhu.access.domain.model.school.ClazzFollowApply;
import com.zhezhu.access.domain.model.school.ClazzFollowApplyRepository;
import com.zhezhu.access.domain.model.school.ClazzFollowAudit;
import com.zhezhu.access.domain.model.school.ClazzFollowAuditRepository;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.access.ClazzFollowAuditId;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.infrastructure.school.ClazzData;
import com.zhezhu.share.infrastructure.school.SchoolData;
import com.zhezhu.share.infrastructure.school.SchoolService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Copyright (c) 2016,$today.year, 深圳市易考试乐学测评有限公司
 **/
public class SchoolApplyAndAuditQueryServiceTest {

    @Mock
    private ClazzFollowApplyRepository clazzFollowApplyRepository;

    @Mock
    private ClazzFollowAuditRepository clazzFollowAuditRepository;

    @Mock
    private SchoolService schoolService;

    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }

    private SchoolApplyAndAuditQueryService  getService(){
        SchoolApplyAndAuditQueryService service = new SchoolApplyAndAuditQueryService(clazzFollowApplyRepository,
                clazzFollowAuditRepository,schoolService);
        return spy(service);
    }

    @Test
    public void getAuditedClazzs(){
        SchoolApplyAndAuditQueryService service = getService();
        ArrayList<ClazzFollowApply> arrayList = new ArrayList<>();
        for(int i=0;i<5;i++){
            ClazzFollowApply apply = mock(ClazzFollowApply.class);
            when(apply.getApplyingClazzId()).thenReturn(new ClazzId());
            when(apply.isAudited()).thenReturn(true);
            when(apply.getAuditId()).thenReturn(new ClazzFollowAuditId());
            arrayList.add(apply);
        }

        when(clazzFollowApplyRepository.findAllByApplierIdAndAuditIdIsNotNull(any(PersonId.class))).thenReturn(null).thenReturn(arrayList);
        ClazzData clazzData = ClazzData.builder().schoolId(new SchoolId().id()).clazzId(new ClazzId().id()).gradeName("一年级").clazzName("ClazzName").build();
        SchoolData schoolData = SchoolData.builder().name("School").schoolId(clazzData.getSchoolId()).build();
        when(schoolService.getClazz(any(ClazzId.class))).thenReturn(clazzData);
        when(schoolService.getSchool(any(SchoolId.class))).thenReturn(schoolData);

        ClazzFollowAudit audit = mock(ClazzFollowAudit.class);
        when(clazzFollowAuditRepository.loadOf(any(ClazzFollowAuditId.class))).thenReturn(audit);
        when(audit.getAuditDate()).thenReturn(DateUtilWrapper.Now);
        when(audit.getAuditId()).thenReturn(new ClazzFollowAuditId());

        List<ClazzFollowApplyAndAuditData> data = service.getAuditedClazzs(new PersonId().id());
        assertEquals(0,data.size());
        data =  service.getAuditedClazzs(new PersonId().id());
        assertEquals(5,data.size());
        assertEquals("ClazzName",data.get(2).getClazzName());
        assertNotNull(data.get(2).getAuditDate());
        assertNotNull(data.get(2).getAuditId());
        verify(clazzFollowApplyRepository,times(2)).findAllByApplierIdAndAuditIdIsNotNull(any(PersonId.class));
        verify(schoolService, times(5)).getClazz(any(ClazzId.class));
        verify(clazzFollowAuditRepository,times(5)).loadOf(any(ClazzFollowAuditId.class));
    }

    @Test
    public void getAuditingClazzs(){
        SchoolApplyAndAuditQueryService service = getService();
        ArrayList<ClazzFollowApply> arrayList = new ArrayList<>();
        for(int i=0;i<5;i++){
            ClazzFollowApply apply = mock(ClazzFollowApply.class);
            when(apply.getApplyingClazzId()).thenReturn(new ClazzId());
            arrayList.add(apply);
        }

        PersonId personId = new PersonId();
        when(clazzFollowApplyRepository.findAllByApplierIdAndAuditIdIsNull(eq(personId))).thenReturn(null).thenReturn(arrayList);
        ClazzData clazzData = ClazzData.builder().schoolId(new SchoolId().id()).clazzId(new ClazzId().id()).gradeName("一年级").clazzName("ClazzName").build();
        SchoolData schoolData = SchoolData.builder().name("School").schoolId(clazzData.getSchoolId()).build();
        when(schoolService.getClazz(any(ClazzId.class))).thenReturn(clazzData);
        when(schoolService.getSchool(any(SchoolId.class))).thenReturn(schoolData);
        List<ClazzFollowApplyAndAuditData> data = service.getAuditingClazzs(personId.id());
        assertEquals(0,data.size());
        data =  service.getAuditingClazzs(personId.id());
        assertEquals(5,data.size());
        assertEquals("ClazzName",data.get(2).getClazzName());
        verify(clazzFollowApplyRepository,times(2)).findAllByApplierIdAndAuditIdIsNull(eq(personId));
        verify(schoolService, times(5)).getClazz(any(ClazzId.class));

    }

    @Test
    public void getAllAuditingClazzApply(){
        SchoolApplyAndAuditQueryService service = getService();
        ArrayList<ClazzFollowApply> arrayList = new ArrayList<>();
        for(int i=0;i<5;i++){
            ClazzFollowApply apply = mock(ClazzFollowApply.class);
            when(apply.getApplyingClazzId()).thenReturn(new ClazzId());
            arrayList.add(apply);
        }
        when(clazzFollowApplyRepository.findAuditingByLimit(anyInt(),anyInt())).thenReturn(null).thenReturn(arrayList);
        ClazzData clazzData = ClazzData.builder().schoolId(new SchoolId().id()).clazzId(new ClazzId().id()).gradeName("一年级").clazzName("ClazzName").build();
        SchoolData schoolData = SchoolData.builder().name("School").schoolId(clazzData.getSchoolId()).build();
        when(schoolService.getClazz(any(ClazzId.class))).thenReturn(clazzData);
        when(schoolService.getSchool(any(SchoolId.class))).thenReturn(schoolData);

        List<ClazzFollowApplyAndAuditData> data = service.getAllAuditingClazzApply(2,10);
        assertEquals(0,data.size());
        data =  service.getAllAuditingClazzApply(1,10);
        assertEquals(5,data.size());
        assertEquals("ClazzName",data.get(2).getClazzName());
    }
}