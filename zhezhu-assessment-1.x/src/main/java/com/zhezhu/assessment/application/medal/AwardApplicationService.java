package com.zhezhu.assessment.application.medal;

import com.zhezhu.assessment.domain.model.medal.Award;
import com.zhezhu.assessment.domain.model.medal.AwardRepository;
import com.zhezhu.assessment.domain.model.medal.Medal;
import com.zhezhu.assessment.domain.model.medal.MedalRepository;
import com.zhezhu.share.infrastructure.school.SchoolService;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.common.Period;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.school.SchoolId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 授勋服务
 *
 * @author Liguiqing
 * @since V3.0
 */
@Service
@Slf4j
public class AwardApplicationService {

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private AwardRepository awardRepository;

    @Autowired
    private MedalRepository medalRepository;

    @Transactional(rollbackFor = Exception.class)
    public void promotion( SchoolId schoolId){
        List<PersonId> personIds = schoolService.getAllStudentPersonIds(schoolId);
        personIds.forEach(personId ->promotion(personId,schoolId) );
    }

    /**
     * 学生晋级
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void promotion(PersonId personId, SchoolId schoolId){
        log.debug("Promotions of {} in {}",personId,schoolId);
        Period period = schoolService.getSchoolTermPeriod(schoolId);
        List<Medal> medals = medalRepository.findMedalsBySchoolId(schoolId);
        if(medals == null)
            return;

        medals.forEach(medal->this.promotionAMedal(medal,personId,period));
    }

    protected void promotionAMedal(Medal medal,PersonId personId,Period period){
        if(!medal.canUp()){
            return ;
        }

        List<Award> awards = awardRepository.findAwardsByPossessorIdAndMedalIdEqualsAndWinDateBetweenAndRiseToIsNull(
                personId,medal.getMedalId(),period.starts(), period.ends());
        int sum = awards.stream().mapToInt(value ->1).sum();
        if(sum == 0)
            return;

        Medal high = medal.promotion(sum);
        if(high == null)
            return;

        Award award = Award.builder()
                .awardId(awardRepository.nextIdentity())
                .medalId(medal.getMedalId())
                .possessorId(personId)
                .winDate(DateUtilWrapper.now())
                .build();
        awardRepository.save(award);

        for(Award oa:awards){
            oa.riseTo(award);
            awardRepository.save(oa);
        }
    }

}