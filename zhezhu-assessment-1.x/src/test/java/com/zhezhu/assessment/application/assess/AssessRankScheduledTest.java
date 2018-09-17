package com.zhezhu.assessment.application.assess;

import com.zhezhu.assessment.AssessmentTestConfiguration;
import com.zhezhu.assessment.application.index.IndexApplicationService;
import com.zhezhu.assessment.application.index.IndexData;
import com.zhezhu.assessment.application.index.IndexQueryService;
import com.zhezhu.assessment.config.AssessmentApplicationConfiguration;
import com.zhezhu.assessment.domain.model.assesse.RankCategory;
import com.zhezhu.assessment.domain.model.assesse.RankScope;
import com.zhezhu.assessment.domain.model.assesse.rank.DayDate;
import com.zhezhu.commons.config.CommonsConfiguration;
import com.zhezhu.commons.util.CollectionsUtilWrapper;
import com.zhezhu.share.config.ShareConfiguration;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.infrastructure.school.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Copyright (c) 2016,$today.year, 深圳市易考试乐学测评有限公司
 **/
@ContextConfiguration(
        classes = {
                CommonsConfiguration.class,
                ShareConfiguration.class,
                AssessmentApplicationConfiguration.class
        }
)
@Transactional
@Rollback
public class AssessRankScheduledTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private AssessRankScheduled rankScheduled;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private AssessApplicationService assessApplicationService;

    @Autowired
    private IndexQueryService indexQueryService;

    @Autowired
    private AssessQueryService assessQueryService;

    @Autowired
    private DayDate dayDate;

    @Test
    public void primaryAutoDayRank() {
        System.out.println(LocalDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
        LocalDate now = LocalDate.now();
        now.atStartOfDay(ZoneId.systemDefault()).plusDays(1L).minusNanos(1L).toInstant();
        System.out.println(now.with(TemporalAdjusters.lastDayOfMonth()));
        List<SchoolData> schools = schoolService.getAllSchool();
        if(CollectionsUtilWrapper.isNullOrEmpty(schools)){
           return;
        }
        schools.forEach(school->genAssess(school));
        rankScheduled.primaryAutoDayRank();

        schools.forEach(school->verify(school));
    }

    private void verify(SchoolData school){
        SchoolId schoolId = new SchoolId(school.getSchoolId());
        List<ClazzData> clazzData = schoolService.getClazzs(schoolId);
        if(CollectionsUtilWrapper.isNullOrEmpty(clazzData)){
            return;
        }

        clazzData.forEach(clazz->verifyClazzRank(clazz));
    }

    private void verifyClazzRank(ClazzData clazz){
        verifyClazzDayRank(clazz);
    }

    private void verifyClazzDayRank(ClazzData clazz){
        RankCategory category = RankCategory.Day;
        RankScope rankScope = RankScope.Clazz;
        List<SchoolAssessRankData> rankData = assessQueryService.getTeamRanks(clazz.getClazzId(), category, rankScope, dayDate.node(),dayDate.from(), dayDate.to());
        assertNotNull(rankData);
        List<StudentData> students = null;
    }

    private void genAssess(SchoolData school){
        List<ClazzData> clazzs = schoolService.getClazzs(new SchoolId(school.getSchoolId()));
        if(CollectionsUtilWrapper.isNotNullAndNotEmpty(clazzs)){
            for(ClazzData clazz:clazzs){
                ClazzId clazzId = new ClazzId(clazz.getClazzId());
                List<StudentData> students = schoolService.getClazzStudents(clazzId);
                List<TeacherData> teachers = schoolService.getClazzTeachers(clazzId);
                if(CollectionsUtilWrapper.isNullOrEmpty(students) || CollectionsUtilWrapper.isNullOrEmpty(teachers)){
                    continue;
                }
                for(TeacherData teacher:teachers){
                    teacherAssessStudent(teacher, students);
                }
            }
        }
    }

    private void teacherAssessStudent(TeacherData teacherData,List<StudentData> students){
        List<IndexData> indexes = indexQueryService.getOwnerIndexes(teacherData.getSchoolId(), "1",false);
        if(CollectionsUtilWrapper.isNullOrEmpty(indexes))
            return ;
        for(StudentData student:students){
            NewTeacherAssessStudentCommand command = NewTeacherAssessStudentCommand.builder()
                    .teacherPersonId(teacherData.getPersonId())
                    .studentPersonId(student.getPersonId())
                    .schoolId(student.getSchoolId())
                    .build();
            for(IndexData index:indexes){
                command.addAssess(new IndexAssess(index.getIndexId(),index.randamScore()));
            }
            assessApplicationService.teacherAssessStudent(command);
        }

    }
}