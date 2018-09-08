package com.zhezhu.assessment.application.assess;

import com.zhezhu.assessment.domain.model.assesse.*;
import com.zhezhu.assessment.domain.model.index.Index;
import com.zhezhu.assessment.domain.model.index.IndexRepository;
import com.zhezhu.commons.util.CollectionsUtilWrapper;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.assessment.AssesseeId;
import com.zhezhu.share.domain.id.school.SchoolId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class AssessQueryService {

    @Autowired
    private IndexRepository indexRepository;

    @Autowired
    private AssessRepository assessRepository;

    @Autowired
    private AssessRankRepository rankRepository;

    public List<AssessRank> getRanks(String schoolId, String personId,
                                     RankCategory category,RankScope scope,Date from,Date to){
        log.debug("Get Assess ranks of {} in scope of {} category {}",personId,scope,category);
        return rankRepository.findAllBySchoolIdAndPersonIdAndRankCategoryAndRankScopeAndRankDateBetween(
                new SchoolId(schoolId), new PersonId(personId),
                category, scope, from, to);
    }

    public List<AssessData> getAssessOf(String assesseeId, Date from, Date to) {
        Date now = DateUtilWrapper.now();
        if(from == null && to == null){
            from = DateUtilWrapper.getStartDayOfWeek(now);
            to = DateUtilWrapper.getEndDayOfWeek(now);
        }else if(from == null){
            from = now;
        }else if(to == null){
            to = DateUtilWrapper.getEndDayOfWeek(now);
        }

        log.debug("Get assess of {} from {} to {}",assesseeId,from,to);

        List<Assess> assesses = assessRepository.findByAssesseeIdAndDoneDateBetween(new AssesseeId(assesseeId), from, to);
        if(CollectionsUtilWrapper.isNullOrEmpty(assesses))
            return new ArrayList<>();

        return assesses.stream().map(assess ->toData(assesseeId,assess))
                .sorted((a,b)->DateUtilWrapper.lessThan(a.getDoneDate(),b.getDoneDate())?-1:1)
                .collect(Collectors.toList());
    }

    private AssessData toData(String assesseeId, Assess assess){
        Index index =  indexRepository.loadOf(assess.getIndexId());

        return AssessData.builder()
                .doneDate(assess.getDoneDate())
                .indexName(index.getName())
                .indexScore(index.getMaxScore())
                .assesseeId(assesseeId)
                .score(assess.getScore())
                .build();
    }
}