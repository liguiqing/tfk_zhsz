package com.zhezhu.assessment.domain.model.assesse;

import com.google.common.collect.Lists;
import com.zhezhu.assessment.domain.model.assesse.rank.SameScoreSamRankStrategy;
import com.zhezhu.assessment.domain.model.collaborator.Assessee;
import com.zhezhu.assessment.domain.model.collaborator.AssesseeRepository;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.common.Period;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.assessment.AssesseeId;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.domain.school.StudyYear;
import com.zhezhu.share.domain.school.Term;
import com.zhezhu.share.infrastructure.school.ClazzData;
import com.zhezhu.share.infrastructure.school.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
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

    public RankService(){

    }

    @Autowired
    public RankService(AssessRepository assessRepository,
                       AssesseeRepository assesseeRepository,
                       AssessRankRepository assessRankRepository,
                       AssessTeamRepository teamRepository,
                       Optional<RankStrategy> rankStrategy,
                       SchoolService schoolService) {
        this.assessRepository = assessRepository;
        this.assesseeRepository = assesseeRepository;
        this.assessRankRepository = assessRankRepository;
        this.teamRepository = teamRepository;
        rankStrategy.ifPresent(strategy->this.rankStrategy=strategy);
        this.schoolService = schoolService;
    }

    public List<AssessRank> rank(String teamId, RankCategory category, RankScope scope){

        AssessTeam team = teamRepository.findByTeamId(teamId);
        RankQuery query = new RankQuery();
        query.newRankQuery(teamId,category,scope);
        List<Assess> assesses = assessRepository.findByAssessTeamIdAndDoneDateBetween(
                team.getAssessTeamId(), query.from, query.to);
        Map<PersonId,List<Assess>> personGroup = assesses.stream()
                .collect(Collectors.groupingBy(Assess::getAssesseeId))
                .entrySet().stream().collect(Collectors.toMap(
                        e -> transToPersonId(e.getKey()),
                        e -> e.getValue()
                ));
        return ranks(personGroup, query.node, category, scope);
    }

    private PersonId transToPersonId(AssesseeId assesseeId){
        Assessee assessee = assesseeRepository.loadOf(assesseeId);
        return assessee.getCollaborator().getPersonId();
    }

    private List<AssessRank> ranks(Map<PersonId,List<Assess>> assessList,String node,RankCategory category, RankScope scope){
        List<AssessRank> ranks = Lists.newArrayList();
        HashMap<PersonId, Double> personScores = new HashMap<>(assessList.size());
        Set<PersonId> personIds = assessList.keySet();
        for(PersonId personId:personIds){
            List<Assess> assesses = assessList.get(personId);
            double sum = assesses.stream().mapToDouble(Assess::getScore).sum();
            personScores.put(personId, sum);
        }
        LinkedHashMap<PersonId, Double> finalMap = new LinkedHashMap<>();
        personScores.entrySet().stream().sorted(Map.Entry.<PersonId, Double>comparingByValue()
                .reversed()).forEachOrdered(e -> finalMap.put(e.getKey(), e.getValue()));
        personIds = finalMap.keySet();

        RankStrategy rankStrategy = rankStrategy();

        for(PersonId personId:personIds){
            double score = finalMap.get(personId);
            int rank = rankStrategy.getRank(score);
            StudyYear year = StudyYear.now();

            AssessRank prevRank = assessRankRepository.findByPersonIdAndRankNodeAndRankCategoryAndRankScopeAndYearStartsAndYearEnds(
                    personId,node,category,scope,year.getYearStarts(),year.getYearEnds());
            if(prevRank == null)
                prevRank = AssessRank.builder().score(score).promoteScore(score).rank(rank).promote(rank).build();

            AssesseeId assesseeId = assessList.get(personId).get(0).getAssesseeId();
            AssessRank assessRank = AssessRank.builder()
                    .assesseeId(assesseeId)
                    .personId(personId)
                    .rankNode(node)
                    .rankDate(DateUtilWrapper.now())
                    .rank(rank)
                    .promote(prevRank.getPromote()-rank)
                    .score(score)
                    .promoteScore(prevRank.getScore() - score)
                    .rankCategory(category)
                    .rankScope(scope)
                    .build();
            ranks.add(assessRank);
        }
        return ranks;
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

        void newRankQuery(String teamId,RankCategory category, RankScope scope){
            switch (scope){
                case Clazz:
                    ClazzData clazz = schoolService.getClazz(new ClazzId(teamId));
                    this.node = getNode(category, new SchoolId(clazz.getSchoolId()));
                    break;
                default:
                    this.node = getNode(category, new SchoolId(teamId));
                    break;
            }
            fromTo(category);
        }

        private void fromTo(RankCategory category){
            LocalDate now = LocalDate.now();
            switch (category){
                case Day:
                    from = DateUtilWrapper.now();
                    to = from;
                    break;
                case Weekend:
                    TemporalField fieldISO = WeekFields.of(Locale.CHINA).dayOfWeek();
                    from = DateUtilWrapper.fromLocalDate(now.with(fieldISO, 1));
                    to = DateUtilWrapper.fromLocalDate(now.with(fieldISO, 5));
                    break;
                case Month:
                    from = DateUtilWrapper.fromLocalDate(now.with(TemporalAdjusters.firstDayOfMonth()));
                    to = DateUtilWrapper.fromLocalDate(now.with(TemporalAdjusters.lastDayOfMonth()));
                    break;
                case Term:
                    Period term = Term.defaultPeriodOfThisTerm();
                    from = term.starts();
                    to = term.ends();
                    break;
                default:
                    StudyYear year = StudyYear.now();
                    from = year.getDefaultDateEnds();
                    to = year.getDefaultDateEnds();
            }
        }

        private String getNode(RankCategory category,SchoolId schoolId){
            switch (category){
                case Weekend:return DateUtilWrapper.weekOfYear(DateUtilWrapper.now())+"";
                case Month:return LocalDate.now().getMonth().getValue() + "";
                case Term:return schoolService.getSchoolTermOfNow(schoolId)+"";
                case Year:return StudyYear.now().toString();
                default: return LocalDate.now().toString();
            }
        }
    }

}