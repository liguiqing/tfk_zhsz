package com.zhezhu.assessment.domain.model.assesse;

import com.google.common.collect.Lists;
import com.zhezhu.assessment.domain.model.assesse.rank.SameScoreSamRankStrategy;
import com.zhezhu.assessment.domain.model.collaborator.Assessee;
import com.zhezhu.assessment.domain.model.collaborator.AssesseeRepository;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.assessment.AssessTeamId;
import com.zhezhu.share.domain.id.assessment.AssesseeId;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.domain.school.StudyYear;
import com.zhezhu.share.infrastructure.school.ClazzData;
import com.zhezhu.share.infrastructure.school.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Liguiqing
 * @since V3.0
 */

@Component
public class RankService {

    private AssessRepository assessRepository;

    private AssesseeRepository assesseeRepository;

    private AssessRankRepository assessRankRepository;

    private AssessTeamRepository teamRepository;

    private RankStrategy rankStrategy;

    private SchoolService schoolService;

    private RankCategoryService rankCategoryService;

    public RankService(){

    }

    @Autowired
    public RankService(AssessRepository assessRepository,
                       AssesseeRepository assesseeRepository,
                       AssessRankRepository assessRankRepository,
                       AssessTeamRepository teamRepository,
                       Optional<RankStrategy> rankStrategy,
                       SchoolService schoolService,
                       RankCategoryService rankCategoryService) {
        this.assessRepository = assessRepository;
        this.assesseeRepository = assesseeRepository;
        this.assessRankRepository = assessRankRepository;
        this.teamRepository = teamRepository;
        rankStrategy.ifPresent(strategy->this.rankStrategy=strategy);
        this.schoolService = schoolService;
        this.rankCategoryService = rankCategoryService;
    }

    public List<AssessRank> rank(String teamId, RankCategory category, RankScope scope){

        AssessTeam team = teamRepository.findByTeamId(teamId);
        RankQuery query = new RankQuery();
        query.newRankQuery(teamId,category,scope);
        List<Assess> assesses = assessRepository.findByAssessTeamIdAndDoneDateBetween(
                team.getAssessTeamId(), query.from, query.to);
        Map<Assessee,List<Assess>> personGroup = assesses.stream()
                .collect(Collectors.groupingBy(Assess::getAssesseeId))
                .entrySet().stream().collect(Collectors.toMap(
                        e -> assesseeRepository.loadOf(e.getKey()),
                        e -> e.getValue()
                ));
        return ranks(personGroup,category, scope,query);
    }

    private List<AssessRank> ranks(Map<Assessee,List<Assess>> assessList,RankCategory category,
                                   RankScope scope,RankQuery query){
        List<AssessRank> ranks = Lists.newArrayList();
        HashMap<Assessee, Double> personScores = new HashMap<>(assessList.size());
        Set<Assessee> as = assessList.keySet();
        for(Assessee a:as){
            List<Assess> assesses = assessList.get(a);
            double sum = assesses.stream().mapToDouble(Assess::getScore).sum();
            personScores.put(a, sum);
        }
        LinkedHashMap<Assessee, Double> finalMap = new LinkedHashMap<>();
        personScores.entrySet().stream().sorted(Map.Entry.<Assessee, Double>comparingByValue()
                .reversed()).forEachOrdered(e -> finalMap.put(e.getKey(), e.getValue()));
        as = finalMap.keySet();

        RankStrategy rankStrategy = rankStrategy();

        for(Assessee a:as){
            PersonId personId = a.getCollaborator().getPersonId();
            double score = finalMap.get(a);
            int rank = rankStrategy.getRank(score);
            AssessRank prevRank = getPrevRank(personId, category, scope, query, score, rank);
            AssessTeamId teamId = assessList.get(a).get(0).getAssessTeamId();
            AssessTeam team = this.teamRepository.loadOf(teamId);
            AssessRank assessRank = AssessRank.builder()
                    .assessTeamId(team.getTeamId())
                    .assesseeId(a.getAssesseeId())
                    .personId(personId)
                    .rankCategory(category)
                    .rankScope(scope)
                    .yearStarts(query.yearStarts)
                    .yearEnds(query.yearEnds)
                    .rankNode(query.node)
                    .rankDate(DateUtilWrapper.now())
                    .rank(rank)
                    .promote(prevRank.getPromote()-rank)
                    .score(score)
                    .promoteScore(prevRank.getScore() - score)
                    .build();
            ranks.add(assessRank);
        }
        return ranks;
    }

    private AssessRank getPrevRank(PersonId personId,RankCategory category,RankScope scope,RankQuery query,double score,int rank){
        AssessRank prevRank = assessRankRepository.findByPersonIdAndRankNodeAndRankCategoryAndRankScopeAndYearStartsAndYearEnds(
                personId,query.prevNode,category,scope,query.yearStarts,query.yearEnds);
        if(prevRank == null)
            prevRank = AssessRank.builder().score(score).promoteScore(score).rank(rank).promote(rank).build();
        return prevRank;
    }

    private RankStrategy rankStrategy(){
        if(this.rankStrategy != null)
            return this.rankStrategy.newStrategy();
        return new SameScoreSamRankStrategy();
    }


    private class RankQuery{
        Date from;

        Date to;

        String node;

        String prevNode;

        int yearStarts;

        int yearEnds;

        RankQuery(){
            StudyYear year = StudyYear.now();
            this.yearStarts = year.getYearStarts();
            this.yearEnds = year.getYearEnds();
        }

        void newRankQuery(String teamId,RankCategory category, RankScope scope){
            switch (scope){
                case Clazz:
                    ClazzData clazz = schoolService.getClazz(new ClazzId(teamId));
                    SchoolId schoolId = new SchoolId(clazz.getSchoolId());
                    this.node = getNode(category,schoolId );
                    this.prevNode = getPreNode(category,schoolId);
                    break;
                default:
                    schoolId = new SchoolId(teamId);
                    this.node = getNode(category, schoolId);
                    this.prevNode = getPreNode(category,schoolId);
                    break;
            }
            fromTo(category);
        }

        private void fromTo(RankCategory category){
            this.from = rankCategoryService.from(category);
            this.to = rankCategoryService.to(category);
        }

        private String getNode(RankCategory category,SchoolId schoolId){
            return rankCategoryService.node(category);
        }

        private String getPreNode(RankCategory category,SchoolId schoolId){
            LocalDate now = LocalDate.now();
            switch (category){
                case Weekend:
                    TemporalField fieldISO = WeekFields.of(Locale.CHINA).weekOfYear();
                    return now.minusWeeks(1).get(fieldISO) + "";
                case Month:return now.getMonth().minus(1).getValue() + "";
                case Term:return schoolService.getSchoolTermOfNow(schoolId).seq()==1?2 + "":1 + "";
                case Year:return StudyYear.now().prev().toString();
                default: return now.minusDays(1).toString();
            }
        }

    }

}