package com.zhezhu.assessment.domain.model.collaborator;

import com.zhezhu.assessment.AssessmentTestConfiguration;
import com.zhezhu.assessment.config.AssessmentApplicationConfiguration;
import com.zhezhu.commons.config.CommonsConfiguration;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.assessment.AssessorId;
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
public class AssessorRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private AssessorRepository assessorRepository;

    @Test
    public void test()throws Exception{
        assertNotNull(assessorRepository);
        AssessorId assessorId = assessorRepository.nextIdentity();
        PersonId personId = new PersonId();
        SchoolId schoolId = new SchoolId();
        Assessor assessor = Assessor.builder()
                .assessorId(assessorId)
                .schoolId(schoolId)
                .personId(personId)
                .role("Teacher")
                .build();

        assessorRepository.save(assessor);
        Assessor assessor_ = assessorRepository.loadOf(assessorId);
        assertNotNull(assessor_);
        assertEquals(assessor,assessor_);

        for(int i=0;i<10000;i++){
            assessorRepository.loadOf(assessorId);
        }

        assessor_ = assessorRepository.findByPersonIdAndSchoolId(personId,schoolId);
        assertEquals(assessor,assessor_);
        assessorRepository.delete(assessorId);
        assessor_ = assessorRepository.loadOf(assessorId);
        assertNull(assessor_);

    }

}