package com.zhezhu.assessment.application.assess;

import com.zhezhu.assessment.domain.model.assesse.*;
import com.zhezhu.assessment.domain.model.index.Index;
import com.zhezhu.assessment.domain.model.index.IndexRepository;
import com.zhezhu.commons.util.CollectionsUtilWrapper;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.assessment.AssesseeId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.infrastructure.school.SchoolService;
import com.zhezhu.share.infrastructure.school.StudentData;
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
    private IndexRepository indexRepository;

    private AssessRepository assessRepository;

    private AssessRankRepository rankRepository;

    private AssessTeamRepository teamRepository;

    private SchoolService schoolService;

    protected AssessQueryService(){

    }

    @Autowired
    public AssessQueryService(IndexRepository indexRepository,
                              AssessRepository assessRepository,
                              AssessRankRepository rankRepository,
                              AssessTeamRepository teamRepository,
                              SchoolService schoolService) {
        this.indexRepository = indexRepository;
        this.assessRepository = assessRepository;
        this.rankRepository = rankRepository;
        this.teamRepository = teamRepository;
        this.schoolService = schoolService;
    }

    /**
     * 取班级或者学校的评价排名
     *
     * @param teamId
     * @param category
     * @param scope
     * @param node
     * @param from
     * @param to
     * @return
     */
    public List<SchoolAssessRankData> getRanks(String teamId,RankCategory category, RankScope scope,String node, Date from, Date to){
        log.debug("Get Assess ranks in clazz {} in scope of {} category {}",teamId,scope,category);

        return rankRepository.findAllByAssessTeamIdAndRankCategoryAndRankScopeAndRankNodeAndRankDateBetween(
                teamId,category,scope,node,from,to).stream()
                .sorted((a,b)->DateUtilWrapper.lessThan(a.getRankDate(),b.getRankDate())?1:-1)
                .map(rank -> SchoolAssessRankData.builder()
                        .score(rank.getScore())
                        .promoteScore(rank.getPromoteScore())
                        .promote(rank.getPromote())
                        .rank(rank.getRank())
                        .rankDate(rank.getRankDate())
                        .rankCategory(rank.getRankCategory().name())
                        .rankScope(rank.getRankScope().name())
                        .rankNode(rank.getRankNode())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 查询学生的学生评价排名
     *
     * @param schoolId
     * @param personId
     * @param category
     * @param scope
     * @param from
     * @param to
     * @return
     */
    public List<SchoolAssessRankData> getSchoolRanks(String schoolId, String personId,
                                                     RankCategory category, RankScope scope, Date from, Date to){
        log.debug("Get Assess ranks in school {} in scope of {} category {}",personId,scope,category);

        PersonId personId1 = new PersonId(personId);
        StudentData student = schoolService.getStudentBy(personId1);
        return getRanksOfTeam(schoolId,personId1,category,scope,student.getSchoolId(),student.getManagedClazzId(),from,to);
    }

    /**
     * 查询学生的班级的评价排名
     *
     * @param clazzId
     * @param personId
     * @param category
     * @param scope
     * @param from
     * @param to
     * @return
     */
    public List<SchoolAssessRankData> getClazzRanks(String clazzId, String personId,
                                                     RankCategory category, RankScope scope, Date from, Date to){
        log.debug("Get Assess ranks in clazz {} in scope of {} category {}",personId,scope,category);

        PersonId personId1 = new PersonId(personId);
        StudentData student = schoolService.getStudentBy(personId1);
        return getRanksOfTeam(clazzId,personId1,category,scope,
                student.getSchoolId(),clazzId,from,to);
    }

    private List<SchoolAssessRankData> getRanksOfTeam(String teamId,PersonId personId,
                                                      RankCategory category,RankScope scope,
                                                      String schoolId,String clazzId,
                                                      Date from, Date to) {
        return rankRepository.findAllByAssessTeamIdAndPersonIdAndRankCategoryAndRankScopeAndRankDateBetween(
                teamId, personId,category, scope, from, to).stream()
                .sorted((a,b)->DateUtilWrapper.lessThan(a.getRankDate(),b.getRankDate())?1:-1)
                .map(rank -> SchoolAssessRankData.builder()
                                .schoolId(schoolId)
                                .clazzId(clazzId)
                                .personId(personId.id())
                                .score(rank.getScore())
                                .promoteScore(rank.getPromoteScore())
                                .promote(rank.getPromote())
                                .rank(rank.getRank())
                                .rankDate(rank.getRankDate())
                                .rankCategory(rank.getRankCategory().name())
                                .rankScope(rank.getRankScope().name())
                                .rankNode(rank.getRankNode())
                                .build())
                .collect(Collectors.toList());
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
                .sorted((a,b)->DateUtilWrapper.lessThan(a.getDoneDate(),b.getDoneDate())?1:-1)
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