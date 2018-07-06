/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.application.school;

import com.tfk.commons.util.DateUtilWrapper;
import com.tfk.ts.application.school.command.NewTermCommand;
import com.tfk.ts.domain.model.school.School;
import com.tfk.ts.domain.model.school.SchoolId;
import com.tfk.ts.domain.model.school.SchoolRepository;
import com.tfk.ts.domain.model.school.clazz.ClazzRepository;
import com.tfk.ts.domain.model.school.staff.StaffRepository;
import com.tfk.ts.domain.model.school.term.TermId;
import com.tfk.ts.domain.model.school.term.TermRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Liguiqing
 * @since V3.0
 */

@RunWith(PowerMockRunner.class)
public class SchoolApplicationServiceTest {

    private SchoolRepository schoolRepository;

    private ClazzRepository clazzRepository;

    private StaffRepository staffRepository;

    private TermRepository termRepository;

    private SchoolApplicationService schoolApplicationService = new SchoolApplicationService();

    @Before
    public void before()throws Exception{
        clazzRepository = mock(ClazzRepository.class);
        schoolApplicationService.setClazzRepository(clazzRepository);
        schoolRepository = mock(SchoolRepository.class);
        schoolApplicationService.setSchoolRepository(schoolRepository);
        staffRepository = mock(StaffRepository.class);
        schoolApplicationService.setStaffRepository(staffRepository);
        termRepository = mock(TermRepository.class);
        schoolApplicationService.setTermRepository(termRepository);
    }

    @Test
    public void testNewSchool()throws Exception{
        when(schoolRepository.nextIdentity()).thenReturn(new SchoolId(("asdfasdfasdfadsf")));
        schoolApplicationService.newSchool("testtenantId","name","Primary");
    }

    @Test
    public void testNewTerm()throws Exception{
        NewTermCommand command = mock(NewTermCommand.class);
        when(command.getTermOrder()).thenReturn("First");
        when(command.getStarts()).thenReturn(DateUtilWrapper.today());
        when(command.getEnds()).thenReturn(DateUtilWrapper.tomorrow());
        when(command.getYear()).thenReturn(DateUtilWrapper.thisYear()+"-"+DateUtilWrapper.nextYear(new Date()));
        School school = mock(School.class);
        SchoolId id = new SchoolId("123456789");
        when(school.schoolId()).thenReturn(id);
        when(schoolRepository.loadOfId(id)).thenReturn(school);
        TermId tid = new TermId("123456789");
        when(termRepository.nextIdentity()).thenReturn(tid);
        schoolApplicationService.newTerm("123456789",command);
    }
}