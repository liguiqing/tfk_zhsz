package com.zhezhu.access.application.school;

import com.zhezhu.access.domain.model.school.ClazzFollowApply;
import com.zhezhu.access.domain.model.school.ClazzFollowApplyRepository;
import com.zhezhu.access.domain.model.school.ClazzFollowAuditRepository;
import com.zhezhu.access.infrastructure.SchoolService;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
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

    private SchoolApplyAndAuditQueryService  getService()throws Exception{
        SchoolApplyAndAuditQueryService service = new SchoolApplyAndAuditQueryService();
        FieldUtils.writeField(service,"clazzFollowApplyRepository",clazzFollowApplyRepository,true);
        FieldUtils.writeField(service,"clazzFollowAuditRepository",clazzFollowAuditRepository,true);
        FieldUtils.writeField(service,"schoolService",schoolService,true);

        return spy(service);
    }

    @Test
    public void getAuditedClazzs() throws Exception{
        SchoolApplyAndAuditQueryService service = getService();
        ArrayList<ClazzFollowApply> arrayList = new ArrayList<>();
        for(int i=0;i<5;i++){
            ClazzFollowApply apply = mock(ClazzFollowApply.class);
            when(apply.getApplyingClazzId()).thenReturn(new ClazzId());
            arrayList.add(apply);
        }
        when(clazzFollowApplyRepository.findAllByApplierIdAndAuditIdIsNotNull(any(PersonId.class))).thenReturn(null).thenReturn(arrayList);
        when(schoolService.getClazzName(any(String.class))).thenReturn("ClazzName");
        when(schoolService.getSchoolIdBy(any(String.class))).thenReturn(new SchoolId().id());
        when(schoolService.getSchoolName(any(String.class))).thenReturn("SchoolName");
        List<ClazzFollowApplyAndAuditData> data = service.getAuditedClazzs(new PersonId().id());
        assertEquals(0,data.size());
        data =  service.getAuditedClazzs(new PersonId().id());
        assertEquals(5,data.size());
        assertEquals("ClazzName",data.get(2).getClazzName());
        verify(clazzFollowApplyRepository,times(2)).findAllByApplierIdAndAuditIdIsNotNull(any(PersonId.class));
        verify(schoolService, times(5)).getClazzName(any(String.class));

    }
}