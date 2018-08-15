package com.tfk.assessment.domain.model.collaborator;

import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.id.assessment.AssessorId;
import com.tfk.share.domain.id.school.SchoolId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * Copyright (c) 2016,$today.year, 深圳市易考试乐学测评有限公司
 **/

@ContextHierarchy({
        @ContextConfiguration(locations = {
                "classpath:META-INF/spring/applicationContext-assessment-app.xml",
                "classpath:applicationContext-test-cache.xml",
                "classpath:applicationContext-test-jndi.xml",
                "classpath:applicationContext-assessment-test-data.xml"}
        )})
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

        assessorRepository.delete(assessorId);
        assessor_ = assessorRepository.loadOf(assessorId);
        assertNull(assessor_);

    }

}