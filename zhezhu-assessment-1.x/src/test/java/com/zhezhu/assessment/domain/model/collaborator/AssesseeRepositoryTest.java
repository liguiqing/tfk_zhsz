package com.zhezhu.assessment.domain.model.collaborator;

import com.zhezhu.assessment.AssessmentTestConfiguration;
import com.zhezhu.assessment.config.AssessmentApplicationConfiguration;
import com.zhezhu.commons.config.CommonsConfiguration;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.assessment.AssesseeId;
import com.zhezhu.share.domain.id.school.SchoolId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * Copyright (c) 2016,$today.year, Liguiqing
 **/

@ContextConfiguration(
        classes = {
                AssessmentTestConfiguration.class,
                CommonsConfiguration.class,
                AssessmentApplicationConfiguration.class
        }
)
@Transactional
@Rollback
public class AssesseeRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private AssesseeRepository assesseeRepository;

    @Test
    public void test()throws Exception{
        assertNotNull(assesseeRepository);
        AssesseeId assesseeId = assesseeRepository.nextIdentity();
        PersonId personId = new PersonId();
        SchoolId schoolId = new SchoolId();
        Assessee assessee = Assessee.builder()
                .assesseeId(assesseeId)
                .schoolId(schoolId)
                .personId(personId)
                .role("Teacher")
                .build();

        assesseeRepository.save(assessee);
        Assessee assessee_ = assesseeRepository.loadOf(assesseeId);
        assertNotNull(assessee_);
        assertEquals(assessee,assessee_);

        for(int i=0;i<10000;i++){
            assesseeRepository.loadOf(assesseeId);
        }

        assessee_ = assesseeRepository.findByPersonIdAndSchoolId(personId, schoolId);
        assertEquals(assessee,assessee_);
        assesseeRepository.delete(assesseeId);
        assessee_ = assesseeRepository.loadOf(assesseeId);
        assertNull(assessee_);
    }

}