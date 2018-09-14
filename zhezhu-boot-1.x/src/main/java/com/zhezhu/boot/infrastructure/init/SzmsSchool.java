package com.zhezhu.boot.infrastructure.init;

import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.school.SchoolScope;
import com.zhezhu.share.domain.school.StudyYear;
import com.zhezhu.sm.application.clazz.NewClazzCommand;
import com.zhezhu.sm.application.data.Contacts;
import com.zhezhu.sm.application.data.CourseData;
import com.zhezhu.sm.application.data.CredentialsData;
import com.zhezhu.sm.application.data.StudyData;
import com.zhezhu.sm.domain.model.school.School;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 深圳民顺学校
 * @author Liguiqing
 * @since V3.0
 */
@Slf4j
@Component
public class SzmsSchool extends AbstractSchoolData{

    @Override
    public void doCreate() {
        createSchool();
    }

    private void createSchool(){
        log.debug("Create school 深圳龙华区民顺学校");
        School school = School.builder()
                .name("深圳龙华区民顺学校")
                .alias("民顺学校")
                .schoolId(schoolId)
                .scope(SchoolScope.Primary)
                .build();
        schoolRepository.save(school);
        this.schoolId = school.getSchoolId();
        ClazzId clazzId = cleateClazzs();
        createTeachers(new ClazzId[]{clazzId});
        createStudent(clazzId);
    }

    private void createStudent(ClazzId clazzId) {
        String[] names = {"罗凯","秦文玺","李贵庆","王校","孙老师","杨晶","李平","罗志勇","徐晶","罗莘","罗志峰","项荃","戴云"};
        String[] phones = {"18682006135","13530678079","18988753681","18820252228","15999698589","13402085698","18923727460","13886044577","15071178811","13607145024","13807230807","18611764486","13607102460"};
        String[] studyNo = {"20160001","20160002","20160003","20160004","20160005","20160006","20160007","20160008","20160009","20160010","20160011","20160012","20160013"};
        CredentialsData[] credentialsData = toCredentials("学籍号",studyNo);
        StudyData[] studyData = new StudyData[]{
                StudyData.builder().clazzId(clazzId.id()).courseName("语文").gradeLevel(2).subjectId(subjectIds[0].id()).build(),
                StudyData.builder().clazzId(clazzId.id()).courseName("数学").gradeLevel(2).subjectId(subjectIds[1].id()).build(),
                StudyData.builder().clazzId(clazzId.id()).courseName("英语").gradeLevel(2).subjectId(subjectIds[2].id()).build()};
        super.createStudent(clazzId,names,phones,credentialsData,studyData, StudyYear.now());
    }

    private ClazzId cleateClazzs(){
        String clazzId = applicationService.newClazz(NewClazzCommand.builder()
                .clazzName("1班")
                .schoolId(schoolId.id())
                .gradeLevel(2)
                .openedTime(DateUtilWrapper.toDate("2017-09-01","yyyy-MM-dd"))
                .yearEnds(2019)
                .yearStarts(2018)
                .build());
        return new ClazzId(clazzId);
    }

    private void createTeachers(ClazzId[] clazzIds){
        StudyYear year = StudyYear.now();
        super.createTeachers("陈利梅",new Contacts[]{new Contacts("Phone","13712345678")},
                year,new CourseData[]{new CourseData("语文",subjectIds[0].id(),2)},clazzIds,clazzIds);
        super.createTeachers("李璇",new Contacts[]{new Contacts("Phone","13700001234")},
                year,new CourseData[]{new CourseData("数学",subjectIds[1].id(),2)},clazzIds,new ClazzId[]{});
        super.createTeachers("文励",new Contacts[]{new Contacts("Phone","13700001235")},
                year,new CourseData[]{new CourseData("英语",subjectIds[2].id(),2)},clazzIds,new ClazzId[]{});
    }
}