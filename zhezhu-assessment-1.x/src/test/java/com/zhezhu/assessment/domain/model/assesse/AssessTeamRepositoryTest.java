package com.zhezhu.assessment.domain.model.assesse;

import com.zhezhu.assessment.AssessmentTestConfiguration;
import com.zhezhu.assessment.config.AssessmentApplicationConfiguration;
import com.zhezhu.commons.config.CommonsConfiguration;
import com.zhezhu.share.domain.id.assessment.AssessTeamId;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Copyright (c) 2016,$today.year, 深圳市易考试乐学测评有限公司
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
public class AssessTeamRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private AssessTeamRepository repository;

    @Test
    public void test() {
        AssessTeamId teamId1 = repository.nextIdentity();
        AssessTeamId teamId2 = repository.nextIdentity();
        AssessTeamId teamId3 = repository.nextIdentity();
        AssessTeam team1 = AssessTeam.builder().assessTeamId(teamId1).teamName("TM1").teamId(new SchoolId().id()).build();
        AssessTeam team2 = AssessTeam.builder().assessTeamId(teamId2).teamName("TM2").teamId(new ClazzId().id()).parentAssessTeamId(teamId1).build();
        AssessTeam team3 = AssessTeam.builder().assessTeamId(teamId3).teamName("TM3").teamId(new ClazzId().id()).parentAssessTeamId(teamId1).build();
        repository.save(team1);
        AssessTeam team1_ = repository.loadOf(teamId1);
        assertEquals(team1,team1_);
        team1_ = repository.findByTeamId(team1.getTeamId());
        assertEquals(team1,team1_);
        for(int i=0;i<10000;i++){
            repository.loadOf(teamId1);
            //repository.findByTeamId(team1.getTeamId());
        }

        repository.save(team2);
        repository.save(team3);

        List<AssessTeam> teams = repository.findAllByParentAssessTeamId(teamId1);
        assertEquals(2,teams.size());

    }
}