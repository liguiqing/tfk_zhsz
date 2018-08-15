package com.tfk.assessment.domain.model.medal;

import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.id.medal.AwardId;
import com.tfk.share.domain.id.medal.MedalId;
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
public class MedalRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private MedalRepository medalRepository;

    @Test
    public  void test(){
        assertNotNull(medalRepository);

        AwardId awardId = new AwardId();
        MedalId medalId = medalRepository.nextIdentity();

        SchoolId schoolId = new SchoolId();

        Medal medal = Medal.builder().medalId(medalId).schoolId(schoolId).name("阳光少年").upLeast(8).category("B").build();

        medalRepository.save(medal);

        Medal medal_ = medalRepository.loadOf(medalId);
        assertNotNull(medal_);
        assertEquals(medal,medal_);
    }
}