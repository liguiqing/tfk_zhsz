package com.tfk.assessment.domain.model.medal;

import com.tfk.assessment.AssessmentTestConfiguration;
import com.tfk.assessment.config.AssessmentApplicationConfiguration;
import com.tfk.commons.config.CommonsConfiguration;
import com.tfk.commons.util.DateUtilWrapper;
import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.id.medal.AwardId;
import com.tfk.share.domain.id.medal.EvidenceId;
import com.tfk.share.domain.id.medal.MedalId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

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
public class AwardRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private AwardRepository awardRepository;

    @Test
    public void test()throws Exception{
        assertNotNull(awardRepository);

        AwardId awardId = awardRepository.nextIdentity();
        MedalId medalId = new MedalId();
        PersonId possessorId = new PersonId();

        Award award = Award.builder()
                .awardId(awardId)
                .medalId(medalId)
                .possessorId(possessorId)
                .winDate(DateUtilWrapper.now())
                .medalName("阳光少年")
                .build();

        award.addEvidence(FileEvidence.builder().awardId(awardId).evidenceId(new EvidenceId()).uploadDate(DateUtilWrapper.now()).build())
                .addEvidence(PhotoEvidence.builder().awardId(awardId).evidenceId(new EvidenceId()).uploadDate(DateUtilWrapper.now()).build())
                .addEvidence(MaterialEvidence.builder().awardId(awardId).evidenceId(new EvidenceId()).uploadDate(DateUtilWrapper.now()).build());

        awardRepository.save(award);
        Award award_ = awardRepository.loadOf(awardId);
        assertNotNull(award_);
        assertEquals(award,award_);

        for(int i =0;i<10000;i++){
            awardRepository.loadOf(awardId);
        }

        awardRepository.delete(awardId);

        award_ = awardRepository.loadOf(awardId);
        assertNull(award_);
    }

    @Test
    public void findAwardsByPossessorIdAndMedalIdEqualsAndWinDateBetweenAndRiseToIsNull(){
        assertNotNull(awardRepository);
        MedalId medalId = new MedalId();
        PersonId possessorId = new PersonId();

        Date dateStarts = DateUtilWrapper.now();
        Date dateEnds = dateStarts;
        for(int i =0;i<10;i++){
            AwardId awardId = awardRepository.nextIdentity();
            dateEnds = DateUtilWrapper.addSecondTo(dateEnds,3600);
            Award award = Award.builder()
                    .awardId(awardId)
                    .medalId(medalId)
                    .possessorId(possessorId)
                    .winDate(dateEnds)
                    .medalName("阳光少年")
                    .build();

            awardRepository.save(award);
        }
        dateEnds = DateUtilWrapper.addSecondTo(dateEnds,3600);
        List<Award> awards = awardRepository.findAwardsByPossessorIdAndMedalIdEqualsAndWinDateBetweenAndRiseToIsNull(possessorId,medalId,dateStarts,dateEnds);
        assertEquals(10,awards.size());
    }
}