package com.zhezhu.boot.infrastructure.init;

import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.id.SubjectId;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.domain.school.SchoolScope;
import com.zhezhu.share.domain.school.StudyYear;
import com.zhezhu.sm.application.clazz.ClazzApplicationService;
import com.zhezhu.sm.application.clazz.NewClazzCommand;
import com.zhezhu.sm.application.data.Contacts;
import com.zhezhu.sm.application.data.CourseData;
import com.zhezhu.sm.application.data.CredentialsData;
import com.zhezhu.sm.application.data.StudyData;
import com.zhezhu.sm.application.student.ArrangeStudentCommand;
import com.zhezhu.sm.application.student.NewStudentCommand;
import com.zhezhu.sm.application.student.StudentApplicationService;
import com.zhezhu.sm.application.teacher.ArrangeTeacherCommand;
import com.zhezhu.sm.application.teacher.NewTeacherCommand;
import com.zhezhu.sm.application.teacher.TeacherApplicationService;
import com.zhezhu.sm.domain.model.school.School;
import com.zhezhu.sm.domain.model.school.SchoolRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 深圳二外
 *
 * @author Liguiqing
 * @since V3.0
 */
@Slf4j
@Component
public class SzewSchool extends AbstractSchoolData {


    @Override
    public void create() {
        createSchool();
    }

    private void createSchool(){
        log.debug("Create school 深圳第二外国语学校");
        School school = School.builder()
                .name("深圳第二外国语学校")
                .alias("深圳二外")
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
        String[] names = {"赖名建","吴剑青","韦若天","王瑶","陈颖","吕惠青","王嘉琪","王彬","林莉容","余裕豪","区桐烨","钟嘉君","李丹燕","陈西","张子盈","陈榕","严方平","林子佳","李泽钊","张煜阳","万卓然","肖文康","林晓哲","刘耿添","吕志鹏","闵凡毓","陈瑞芬","肖君豪","郭昊天","慕容萦","幸至珺","梁嘉丽","邱鹏2","王凯民","戴薇","石培思","刘宇涵","冯皓源","许可凡","伍飞虎","黄宇涛","陈周宏","陶志源","叶子宁","杨宇","蔡伟林","薛越畅","杜金保","廖伟锐","吴渊","马钦彬","刘嘉佳","郑佩君"};
        String[] phones = {"13600001234", "13600001235", "13600001236", "13600001237", "13600001238", "13600001239", "13600001240", "13600001241", "13600001242", "13600001243", "13600001244", "13600001245", "13600001246", "13600001247", "13600001248", "13600001249", "13600001250", "13600001251", "13600001252", "13600001253", "13600001254", "13600001255", "13600001256", "13600001257", "13600001258", "13600001259", "13600001260", "13600001261", "13600001262", "13600001263", "13600001264", "13600001265", "13600001266", "13600001267", "13600001268", "13600001269", "13600001270", "13600001271", "13600001272", "13600001273", "13600001274", "13600001275", "13600001276", "13600001277", "13600001278", "13600001279", "13600001280", "13600001281", "13600001282", "13600001283", "13600001284", "13600001285", "13600001286"};
        String[] studyNo = {"1176301011","1176301021","1176301031","1176301041","1176301051","1176301061","1176301071","1176301081","1176301091","1176301101","1176301111","1176301121","1176301131","1176301141","1176301151","1176301161","1176301171","1176301181","1176301191","1176301201","1176301211","1176301221","1176301231","1176301241","1176301251","1176301261","1176301271","1176301281","1176301291","1176301301","1176301311","1176301321","1176301331","1176301341","1176301351","1176301361","1176301371","1176301381","1176301391","1176301401","1176301411","1176301421","1176302011","1176302021","1176302031","1176302041","1176302051","1176302061","1176302071","1176302081","1176302091","1176302101","1176302111"};
        CredentialsData[] credentialsData = toCredentials("学籍号",studyNo);
        StudyData[] studyData = new StudyData[]{
                StudyData.builder().clazzId(clazzId.id()).courseName("语文").gradeLevel(1).subjectId(subjectIds[0].id()).build(),
                StudyData.builder().clazzId(clazzId.id()).courseName("数学").gradeLevel(1).subjectId(subjectIds[1].id()).build(),
                StudyData.builder().clazzId(clazzId.id()).courseName("英语").gradeLevel(1).subjectId(subjectIds[2].id()).build()};

        super.createStudent(clazzId,names,phones,credentialsData,studyData, StudyYear.now());
    }

    private ClazzId cleateClazzs(){
        String clazzId = applicationService.newClazz(NewClazzCommand.builder()
                .clazzName("1班")
                .schoolId(schoolId.id())
                .gradeLevel(1)
                .openedTime(DateUtilWrapper.toDate("2018-09-01","yyyy-MM-dd"))
                .yearEnds(2019)
                .yearStarts(2018)
                .build());
        return new ClazzId(clazzId);
    }

    private void createTeachers(ClazzId[] clazzIds){
        StudyYear year = StudyYear.now();
        super.createTeachers("姜典来",new Contacts[]{new Contacts("Phone","1390001234")},
                year,new CourseData[]{new CourseData("语文",subjectIds[0].id())},clazzIds,new ClazzId[]{});
        super.createTeachers("柯海清",new Contacts[]{new Contacts("Phone","1390001235")},
                year,new CourseData[]{new CourseData("数学",subjectIds[1].id())},clazzIds,clazzIds);
        super.createTeachers("徐泓静",new Contacts[]{new Contacts("Phone","1390001236")},
                year,new CourseData[]{new CourseData("英语",subjectIds[2].id())},clazzIds,new ClazzId[]{});
    }
}