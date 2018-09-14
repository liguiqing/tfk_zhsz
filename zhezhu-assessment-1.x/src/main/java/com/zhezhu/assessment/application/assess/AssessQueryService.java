package com.zhezhu.assessment.application.assess;

import com.zhezhu.assessment.domain.model.assesse.*;
import com.zhezhu.assessment.domain.model.collaborator.Assessee;
import com.zhezhu.assessment.domain.model.collaborator.AssesseeRepository;
import com.zhezhu.assessment.domain.model.index.Index;
import com.zhezhu.assessment.domain.model.index.IndexRepository;
import com.zhezhu.commons.util.CollectionsUtilWrapper;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.common.Period;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.assessment.AssesseeId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.domain.school.Term;
import com.zhezhu.share.infrastructure.school.SchoolService;
import com.zhezhu.share.infrastructure.school.StudentData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 评价查询服务
 *
 * @author Liguiqing
 * @since V3.0
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class AssessQueryService {
    private IndexRepository indexRepository;

    private AssesseeRepository assesseeRepository;

    private AssessRepository assessRepository;

    private AssessRankRepository rankRepository;

    private SchoolService schoolService;

    protected AssessQueryService(){

    }

    @Autowired
    public AssessQueryService(IndexRepository indexRepository,
                              AssesseeRepository assesseeRepository,
                              AssessRepository assessRepository,
                              AssessRankRepository rankRepository,
                              SchoolService schoolService) {
        this.indexRepository = indexRepository;
        this.assesseeRepository = assesseeRepository;
        this.assessRepository = assessRepository;
        this.rankRepository = rankRepository;
        this.schoolService = schoolService;
    }

    /**
     * 查询分组评价排名
     *
     * @param teamId Value of {@link com.zhezhu.share.domain.id.school.SchoolId}
     *               or {@link com.zhezhu.share.domain.id.school.ClazzId}
     * @param category {@link RankCategory}
     * @param scope {@link RankScope}
     * @param node  value with category:
     *              <tt>RankCategory.Day</tt> yyyy-MM-dd ex:2018-09-01
     *              <tt>RankCategory.Weekend</tt> 1 to 54
     *              <tt>RankCategory.Month</tt> 1 to 12
     *              <tt>RankCategory.Term</tt>-> 1 or 2
     *              <tt>RankCategory.Year</tt>-> yyyy-yyyy ex:2018-2019
     * @param from rankDate from
     * @param to   rankDate to
     * @return List of SchoolAssessRankData
     */
    public List<SchoolAssessRankData> getTeamRanks(String teamId, RankCategory category,
                                                   RankScope scope, String node, Date from, Date to){
        log.debug("Get Assess ranks in clazz {} in scope of {} category {}",teamId,scope,category);

        return rankRepository.findAllByAssessTeamIdAndRankCategoryAndRankScopeAndRankNodeAndRankDateBetween(
                teamId,category,scope,node,from,to).stream()
                .sorted(Comparator.comparing(AssessRank::getRankDate).reversed())
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
     * 查询分组评价最近一条记录
     *
     * @param teamId Value of {@link com.zhezhu.share.domain.id.school.SchoolId} or {@link com.zhezhu.share.domain.id.school.ClazzId}
     * @param category {@link RankCategory}
     * @param scope {@link RankScope}
     * @param node  value with category:
     *              <tt>RankCategory.Day</tt> yyyy-MM-dd
     *              <tt>RankCategory.Weekend</tt> 1 to 54
     *              <tt>RankCategory.Month</tt> 1 to 12
     *              <tt>RankCategory.Term</tt>-> 1 or 2
     *              <tt>RankCategory.Year</tt>-> yyyy-yyyy ex:2018-2019
     * @return List of {@link SchoolAssessRankData}
     */
    public List<SchoolAssessRankData> getTeamLastRanks(String teamId, RankCategory category, RankScope scope, String node) {
        AssessRank rank = rankRepository.findByAssessTeamIdAndRankCategoryAndRankScopeAndRankNode(teamId,category,scope,node);
        if(rank == null)
            return new ArrayList<>();
        return getTeamRanks(teamId, category, scope, node, rank.getRankDate(), rank.getRankDate());
    }

    /**
     * 查询个人评价排名
     *
     * @param teamId Value of {@link com.zhezhu.share.domain.id.school.SchoolId} or {@link com.zhezhu.share.domain.id.school.ClazzId}
     * @param personId {@link com.zhezhu.share.domain.id.PersonId}
     * @param category {@link RankCategory}
     * @param scope {@link RankScope}
     * @param from rankDate from
     * @param to   rankDate to
     * @return List of {@link SchoolAssessRankData}
     */
    public List<SchoolAssessRankData> getPersonalRanks(String teamId, String personId,
                                                       RankCategory category, RankScope scope,
                                                       Date from, Date to){
        log.debug("Get Person {} ranks of {}",personId,(scope.toString() + category.toString() + from + to));

        PersonId personId1 = new PersonId(personId);
        StudentData student = schoolService.getStudentBy(personId1);
        return getRanksOfTeam(teamId,personId1,category,scope,student.getSchoolId(),student.getManagedClazzId(),from,to);
    }

    /**
     * 查询本年度个人评价分组最新排名
     * 最新排查肯定是{@link RankCategory#Day}且{@link RankScope#Clazz}的排名
     *
     * @param personId {@link com.zhezhu.share.domain.id.PersonId}
     * @return list of SchoolAssessRankData
     */
    public SchoolAssessRankData getPersonalLastRanksThisYear(String personId) {
        log.debug("Get Person  {} Last Ranks of {}",personId);

        PersonId personId1 = new PersonId(personId);
        StudentData student = schoolService.getStudentBy(personId1);

        Assessee assessee = assesseeRepository.findByPersonIdAndSchoolId(personId1, new SchoolId(student.getSchoolId()));
        if(assessee == null) {
            return null;
        }
        Period period = Term.defaultPeriodOfThisTerm();
        List<Assess> lastAssessList = assessRepository.findByAssesseeIdAndDoneDateBetween(
                assessee.getAssesseeId(),period.starts(),period.ends());
        if(CollectionsUtilWrapper.isNullOrEmpty(lastAssessList)) {
            return null;
        }

        lastAssessList.sort(Comparator.comparing(Assess::getDoneDate));
        Assess lastAssess = lastAssessList.get(0);
        String node = DateUtilWrapper.toLocalDate(lastAssess.getDoneDate()).toString();
        RankCategory category = RankCategory.Day;
        RankScope scope = RankScope.Clazz;

        AssessRank lastRank = rankRepository.findByPersonIdAndRankNodeAndRankCategoryAndRankScopeAndYearStartsAndYearEnds(
                personId1,node,category,scope,period.yearStarts(),period.yearEnds());

        if(lastRank == null) {
            return null;
        }

        return SchoolAssessRankData.builder()
                .schoolId(student.getSchoolId())
                .clazzId(student.getManagedClazzId())
                .personId(student.getPersonId())
                .score(lastRank.getScore())
                .promoteScore(lastRank.getPromoteScore())
                .promote(lastRank.getPromote())
                .rank(lastRank.getRank())
                .rankDate(lastRank.getRankDate())
                .rankCategory(lastRank.getRankCategory().name())
                .rankScope(lastRank.getRankScope().name())
                .rankNode(lastRank.getRankNode())
                .build();
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

    /**
     * 查询被评价者某个时间段的评价记录
     *
     * @param assesseeId {@link AssesseeId}
     * @param from rankDate from
     * @param to   rankDate to
     * @return list of {@link AssessData}
     */
    public List<AssessData> getAssessBetween(String assesseeId, Date from, Date to) {
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