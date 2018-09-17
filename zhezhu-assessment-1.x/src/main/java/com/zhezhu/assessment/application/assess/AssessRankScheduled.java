package com.zhezhu.assessment.application.assess;

import com.zhezhu.assessment.domain.model.assesse.RankCategory;
import com.zhezhu.assessment.domain.model.assesse.RankScope;
import com.zhezhu.assessment.domain.model.assesse.RankService;
import com.zhezhu.commons.util.CollectionsUtilWrapper;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.common.Period;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.domain.school.SchoolScope;
import com.zhezhu.share.domain.school.StudyYear;
import com.zhezhu.share.domain.school.Term;
import com.zhezhu.share.infrastructure.school.ClazzData;
import com.zhezhu.share.infrastructure.school.SchoolData;
import com.zhezhu.share.infrastructure.school.SchoolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 排名计算定时服务
 *
 * @author Liguiqing
 * @since V3.0
 */

@Slf4j
@Service
public class AssessRankScheduled {

    private SchoolService schoolService;

    private AssessApplicationService rankService;

    @Autowired
    public AssessRankScheduled(SchoolService schoolService,AssessApplicationService rankService) {
        this.schoolService = schoolService;
        this.rankService = rankService;
    }

    /**
     * 小学排名,每周到周五下午5点计算排名
     *
     */
    @Scheduled(cron="0 0 17 ? * MON-FRI")
    public void primaryAutoDayRank(){
        List<SchoolData> schoolData = schoolService.getAllSchool().stream()
               .filter(school->school.scopeOf(SchoolScope.Primary))
               .collect(Collectors.toList());
        rank(schoolData);
    }

    /**
     * 常排名,每周到周五晚上9点计算排名
     *
     */
    @Scheduled(cron="0 0 21 ? * MON-FRI")
    public void middleAutoDayRank(){
        List<SchoolData> schoolData = schoolService.getAllSchool().stream()
                .filter(school->school.scopeOf(SchoolScope.Middle))
                .collect(Collectors.toList());
        rank(schoolData);
    }

    /**
     * 排名,每周到周五晚上9点计算排名
     *
     */
    @Scheduled(cron="0 0 22 ? * MON-FRI")
    public void highAutoDayRank(){
        List<SchoolData> schoolData = schoolService.getAllSchool().stream()
                .filter(school->school.scopeOf(SchoolScope.High))
                .collect(Collectors.toList());
        rank(schoolData);
    }

    private void rank(List<SchoolData> schoolData){
        if(CollectionsUtilWrapper.isNullOrEmpty(schoolData))
            return;

        dayRank(schoolData);
        weekendRank(schoolData);
        monthRank(schoolData);
        termRank(schoolData);
        yearRank(schoolData);
    }

    //进行日排名计算
    private void dayRank(List<SchoolData> schoolData){
        log.debug("Day Ranks Executing {}", LocalDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));

        schoolData.forEach(school -> rankOnClazz(school, RankCategory.Day));
        schoolData.forEach(school -> rankService.rank(school.getSchoolId(), RankCategory.Day, RankScope.Grade));
    }

    //进行周排名计算
    private void weekendRank(List<SchoolData> schoolData){
        log.debug("Weekend Ranks Executing {}", LocalDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));

        LocalDate now = LocalDate.now();
        //周五进行周排名计算
        if(now.getDayOfWeek().getValue() != 5){
            return;
        }
        schoolData.forEach(school -> rankOnClazz(school, RankCategory.Weekend));
        schoolData.forEach(school -> rankService.rank(school.getSchoolId(), RankCategory.Weekend, RankScope.Grade));
    }

    //进行月排名计算
    private void monthRank(List<SchoolData> schoolData){
        LocalDate now = LocalDate.now();
        LocalDate lastDayOfMonth = now.with(TemporalAdjusters.lastDayOfMonth());
        if(!now.equals(lastDayOfMonth)) {
            return;
        }

        log.debug("Month Ranks Executing {}", LocalDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));

        schoolData.forEach(school ->rankOnClazz(school, RankCategory.Month));
        schoolData.forEach(school -> rankService.rank(school.getSchoolId(), RankCategory.Month, RankScope.Grade));
    }

    //进行学期排名计算
    private void termRank(List<SchoolData> schoolData){
        LocalDate now = LocalDate.now();
        //应该改成按学校的定义来实现
        Period period = Term.defaultPeriodOfThisTerm();
        LocalDate lastDayOfTerm = DateUtilWrapper.toLocalDate(period.ends());
        if (!lastDayOfTerm.equals(now)) {
            return;
        }
        log.debug("Term Ranks Executing {}", LocalDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));


        schoolData.forEach(school -> rankOnClazz(school, RankCategory.Term));
        schoolData.forEach(school -> rankService.rank(school.getSchoolId(), RankCategory.Term, RankScope.Grade));
    }

    //进行学年排名计算
    private void yearRank(List<SchoolData> schoolData){
        //应该改成按学校的定义来实现
        StudyYear year = StudyYear.now();
        LocalDate date = DateUtilWrapper.toLocalDate(year.getDefaultDateEnds());
        if(!date.equals(LocalDate.now())){
            return ;
        }

        log.debug("Year Ranks Executing {}", LocalDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));

        schoolData.forEach(school -> rankOnClazz(school, RankCategory.Year));
        schoolData.forEach(school -> rankService.rank(school.getSchoolId(), RankCategory.Year, RankScope.Grade));
    }

    private void rankOnClazz(SchoolData school,RankCategory category){
        List<ClazzData> clazzData = schoolService.getClazzs(new SchoolId(school.getSchoolId()));
        if(CollectionsUtilWrapper.isNullOrEmpty(clazzData))
            return;
        clazzData.forEach(clazz->rankService.rank(clazz.getClazzId(),category,RankScope.Clazz));

    }
}