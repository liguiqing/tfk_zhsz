package com.zhezhu.assessment.domain.model.assesse;

import com.google.common.collect.Lists;
import com.zhezhu.assessment.domain.model.collaborator.Assessee;
import com.zhezhu.assessment.domain.model.collaborator.AssesseeRepository;
import com.zhezhu.commons.util.CollectionsUtilWrapper;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.common.Period;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.assessment.AssesseeId;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.domain.person.Person;
import com.zhezhu.share.domain.school.StudyYear;
import com.zhezhu.share.domain.school.Term;
import com.zhezhu.share.infrastructure.school.ClazzData;
import com.zhezhu.share.infrastructure.school.SchoolService;
import com.zhezhu.share.infrastructure.school.StudentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Liguiqing
 * @since V3.0
 */

@Component
public class RankService {
    @Autowired
    private AssessRepository repository;

    @Autowired
    private AssesseeRepository assesseeRepository;

    @Autowired
    private AssessRankRepository assessRankRepository;

    @Autowired
    private SchoolService schoolService;

    public List<AssessRank> rank(SchoolId schoolId,ClazzId clazzId, RankCategory category, RankScope scope){
        List<AssessRank> ranks = Lists.newArrayList();
        List<ClazzData> clazzs = schoolService.getClazzs(schoolId);
        if(CollectionsUtilWrapper.isNullOrEmpty(clazzs))
            return ranks;

        Map<String, List<ClazzData>> gradeClazzs = clazzs.stream().collect(Collectors.groupingBy(ClazzData::getGradeName));
        Set<String> grades = gradeClazzs.keySet();
        HashMap<PersonId,List<Assess>> gradeAssesses = new HashMap<>();


        LocalDateTime now = LocalDateTime.now();
        Date from = DateUtilWrapper.fromLocalTime(now);
        Date to = from;
        for(String grade:grades){
            List<ClazzData> aGradeClazzs = gradeClazzs.get(grade);
            List<List<Assess>> assessGrade = Lists.newArrayList();
            for(ClazzData clazz:aGradeClazzs){
                List<StudentData> clazzStudents = schoolService.getClazzStudents(new ClazzId(clazz.getClazzId()));
                if(CollectionsUtilWrapper.isNullOrEmpty(clazzs))
                    continue;
                List<List<Assess>> assessClazz = Lists.newArrayList();
                for(StudentData student:clazzStudents){
                    PersonId personId = new PersonId(student.getPersonId());
                    Assessee assessee = assesseeRepository.findByPersonIdAndSchoolId(personId, schoolId);
                    List<Assess> onDayAssess = getAssessOf(assessee.getAssesseeId(), from, to);

                    assessClazz.add(onDayAssess);

                    assessGrade.add(onDayAssess);

                    DayOfWeek weekDay = now.getDayOfWeek();
                    if(DayOfWeek.FRIDAY.equals(weekDay)){

                    }
                }

            }

        }
        List<StudentData> clazzStudent = schoolService.getClazzStudents(clazzId);



        return ranks;
    }

    private List<AssessRank> ranks(Map<PersonId,List<Assess>> assessList,String node,RankCategory category, RankScope scope){
        List<AssessRank> ranks = Lists.newArrayList();
        HashMap<PersonId, Double> personScores = new HashMap<>(assessList.size());
        Set<PersonId> personIds = assessList.keySet();
        for(PersonId personId:personIds){
            List<Assess> assesses = assessList.get(personId);
            double sum = assesses.stream().mapToDouble(a->a.getScore()).sum();
            personScores.put(personId, sum);
        }
        LinkedHashMap<PersonId, Double> finalMap = new LinkedHashMap<>();
        personScores.entrySet().stream().sorted(Map.Entry.<PersonId, Double>comparingByValue()
                .reversed()).forEachOrdered(e -> finalMap.put(e.getKey(), e.getValue()));
        personIds = finalMap.keySet();

        double score = 0;
        int rank = 0;
        for(PersonId personId:personIds){
            double score_ = finalMap.get(personId);
            if(score != score_){
                score = score_;
                rank++;
            }
            StudyYear year = StudyYear.now();
            AssessRank prevRank = assessRankRepository.findByPersonIdAndRankNodeAndRankCategoryAndRankScopeAndYearStartsAndYearEnds(
                    personId,"",category,scope,year.getYearStarts(),year.getYearEnds());
            AssessRank assessRank = AssessRank.builder()
                    .personId(personId)
                    .schoolId(prevRank.getSchoolId())
                    .clazzId(prevRank.getClazzId())
                    .rankDate(DateUtilWrapper.now())
                    .rank(rank)
                    .promote(prevRank.getPromote()-rank)
                    .score(score_)
                    .promoteScore(prevRank.getScore() - score_)
                    .rankScope(scope)
                    .build();
            ranks.add(assessRank);
        }
        return ranks;
    }

    private List<AssessRank> ranksOfDay(SchoolId schoolId,ClazzId clazzId){
        List<AssessRank> ranks = Lists.newArrayList();

        return ranks;
    }

    private List<AssessRank> ranksOfWeek(SchoolId schoolId,ClazzId clazzId){
        List<AssessRank> ranks = Lists.newArrayList();

        return ranks;
    }

    private List<AssessRank> ranksOfMoth(SchoolId schoolId,ClazzId clazzId){
        List<AssessRank> ranks = Lists.newArrayList();

        return ranks;
    }

    private List<AssessRank> ranksOfTerm(SchoolId schoolId,ClazzId clazzId){
        List<AssessRank> ranks = Lists.newArrayList();

        return ranks;
    }

    private List<AssessRank> ranksOfYear(SchoolId schoolId,ClazzId clazzId){
        List<AssessRank> ranks = Lists.newArrayList();

        return ranks;
    }

    private List<Assess> getAssessOf(AssesseeId assesseeId,Date from, Date to) {
        return repository.findByAssesseeIdAndDoneDateBetween(assesseeId, from, to);
    }

}