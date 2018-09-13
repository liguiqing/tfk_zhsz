package com.zhezhu.share.infrastructure.school;

import com.google.common.collect.Lists;
import com.zhezhu.commons.config.CommonsConfiguration;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.config.ShareConfiguration;
import com.zhezhu.share.config.ShareTestConfiguration;
import com.zhezhu.share.domain.common.Period;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.identityaccess.TenantId;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.domain.id.school.StudentId;
import com.zhezhu.share.domain.id.school.TeacherId;
import com.zhezhu.share.domain.person.Gender;
import com.zhezhu.share.domain.school.SchoolScope;
import com.zhezhu.share.domain.school.StudyYear;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Copyright (c) 2016,$today.year, 深圳市易考试乐学测评有限公司
 **/
@ContextConfiguration(classes = {ShareConfiguration.class, ShareTestConfiguration.class,CommonsConfiguration.class})
@Transactional
@Rollback
public class JdbcSchoolInfoApiTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private JdbcSchoolInfoApi api;

    @Autowired
    private JdbcTemplate jdbc;

    @Test
    public void getSchoolTenantId() {
        SchoolId schoolId = new SchoolId();
        TenantId tenantId = new TenantId();
        jdbc.update(" INSERT INTO `sm_school`(`schoolId`,`tenantId`,`name`,`alias`,`scope`,`removed`) VALUES (?,?,?,?,?,?)",
                new Object[]{schoolId.id(),tenantId.id(),"TestSchool","TS", SchoolScope.Middle.name(),0});

        TenantId tenantId1 = api.getSchoolTenantId(schoolId);
        assertEquals(tenantId,tenantId1);
    }


    @Test
    public void getAllStudentPersonIds() {
        SchoolId schoolId = new SchoolId();
        for(int i=0;i<100;i++){
            jdbc.update("INSERT INTO `sm_student`(`studentId`,`schoolId`,`personId`,`name`,`gender`,`birthday`,`joinDate`,`offDate`,`removed`) VALUES (?,?,?,?,?,?,?,?,?)",
                    new Object[]{new StudentId().id(),schoolId.id(),new PersonId().id(),"Name"+i,
                            Gender.Female.name(), DateUtilWrapper.now(),DateUtilWrapper.now(),null,0});
        }
        jdbc.update("INSERT INTO `sm_student`(`studentId`,`schoolId`,`personId`,`name`,`gender`,`birthday`,`joinDate`,`offDate`,`removed`) VALUES (?,?,?,?,?,?,?,?,?)",
                new Object[]{new StudentId().id(),new SchoolId().id(),new PersonId().id(),"Namen",
                        Gender.Female.name(), DateUtilWrapper.now(),DateUtilWrapper.now(),null,0});
        List<PersonId> personIds =  api.getAllStudentPersonIds(schoolId);
        assertEquals(100,personIds.size());
    }

    @Test
    public void getStudent() {
        SchoolId schoolId = new SchoolId();

        for(int i=0;i<10;i++){
            jdbc.update("INSERT INTO `sm_student`(`studentId`,`schoolId`,`personId`,`name`,`gender`,`birthday`,`joinDate`,`offDate`,`removed`) VALUES (?,?,?,?,?,?,?,?,?)",
                    new Object[]{new StudentId().id(),schoolId.id(),new PersonId().id(),"Name"+i,
                            Gender.Female.name(), DateUtilWrapper.now(),DateUtilWrapper.now(),null,0});
        }
        StudentId studentId = new StudentId();
        PersonId personId = new PersonId();
        jdbc.update("INSERT INTO `sm_student`(`studentId`,`schoolId`,`personId`,`name`,`gender`,`birthday`,`joinDate`,`offDate`,`removed`) VALUES (?,?,?,?,?,?,?,?,?)",
                new Object[]{studentId.id(),schoolId.id(),personId.id(),"Name",
                        Gender.Female.name(), DateUtilWrapper.now(),DateUtilWrapper.now(),null,0});

        jdbc.update("INSERT INTO `sm_student_contact`(`personId`,`category`,`name`,`info`) VALUES (?,?,?,?)",
                new Object[]{personId.id(),"Phone","手机","1235689874"});
        jdbc.update("INSERT INTO `sm_student_contact`(`personId`,`category`,`name`,`info`) VALUES (?,?,?,?)",
                new Object[]{personId.id(),"QQ","QQ","256320"});

        ClazzId clazzId = new ClazzId();
        jdbc.update("INSERT INTO `sm_student_managed`(`studentId`,`schoolId`,`clazzId`,`job`,`gradeName`,`gradeLevel`,`yearStarts`,`yearEnds`,`dateStarts`,`dateEnds`) VALUES (?,?,?,?,?,?,?,?,?,?)",
                new Object[]{studentId.id(),schoolId.id(),clazzId.id(),"a","四年级",4,2016,2017,"2016-09-01","2017-06-30"});
        jdbc.update("INSERT INTO `sm_student_managed`(`studentId`,`schoolId`,`clazzId`,`job`,`gradeName`,`gradeLevel`,`yearStarts`,`yearEnds`,`dateStarts`,`dateEnds`) VALUES (?,?,?,?,?,?,?,?,?,?)",
                new Object[]{studentId.id(),schoolId.id(),clazzId.id(),"a","五年级",5,2017,2018,"2017-09-01","2018-06-30"});
        jdbc.update("INSERT INTO `sm_student_managed`(`studentId`,`schoolId`,`clazzId`,`job`,`gradeName`,`gradeLevel`,`yearStarts`,`yearEnds`,`dateStarts`,`dateEnds`) VALUES (?,?,?,?,?,?,?,?,?,?)",
                new Object[]{studentId.id(),schoolId.id(),clazzId.id(),"a","六年级",6,2018,2019,"2018-09-01",null});

        jdbc.update("INSERT INTO `sm_clazz_history`(`clazzId`,`clazzName`,`gradeName`,`gradeLevel`,`yearStarts`,`yearEnds`) VALUES (?,?,?,?,?,?)",
                new Object[]{clazzId.id(),"一班","五年级",5,2017,2018});
        jdbc.update("INSERT INTO `sm_clazz_history`(`clazzId`,`clazzName`,`gradeName`,`gradeLevel`,`yearStarts`,`yearEnds`) VALUES (?,?,?,?,?,?)",
                new Object[]{clazzId.id(),"一班","六年级",6,2018,2019});

        List l = jdbc.query("select a.clazzId,a.gradeName,a.gradeLevel,b.clazzName,a.dateEnds from sm_student_managed a inner join sm_clazz_history b on b.clazzId = a.clazzId and a.yearEnds=b.yearEnds  where a.studentId=? and a.dateEnds is null ",
                (rs,rowNum) -> rs.getString("dateEnds"),studentId.id());

        StudentData student = api.getStudent(personId);
        assertNotNull(student);
        assertEquals(schoolId.id(),student.getSchoolId());
        assertEquals("Name",student.getName());
        assertEquals(Gender.Female.name(),student.getGender());
        assertEquals(student.getPersonId(),personId.id());
        assertEquals(2,student.getContacts().size());
        assertEquals("QQ",student.getContacts().get(1).getName());
        assertEquals("256320",student.getContacts().get(1).getValue());
        assertEquals(1,student.getClazzes().size());
        assertEquals("六年级",student.getClazzes().get(0).getGradeName());
    }

    @Test
    public void getSchoolTermPeriod() {
        SchoolId schoolId = new SchoolId();
        Period p = api.getSchoolTermPeriod(schoolId);
        assertNotNull(p);
    }

    @Test
    public void getTeacher() {
        SchoolId schoolId = new SchoolId();
        String sql = "INSERT INTO `sm_teacher`(`teacherId`,`personId`,`schoolId`,`name`,`gender`,`birthday`,`joinDate`,`offDate`,`removed`) VALUES(?,?,?,?,?,?,?,?,?)";
        List<Object[]> args = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            args.add(new Object[]{new TeacherId().id(),new PersonId().id(),schoolId.id(),"T"+i,Gender.Female.name(),null,"2015-09-01",null,0});
        }
        TeacherId teacherId = new TeacherId();
        PersonId personId = new PersonId();
        args.add(new Object[]{teacherId.id(),personId.id(),schoolId.id(),"T",Gender.Male.name(),null,"2016-09-01",null,0});
        jdbc.batchUpdate(sql, args);

        ClazzId clazzId = new ClazzId();
        jdbc.update("INSERT INTO `sm_clazz_history`(`clazzId`,`clazzName`,`gradeName`,`gradeLevel`,`yearStarts`,`yearEnds`) VALUES (?,?,?,?,?,?)",
                new Object[]{clazzId.id(),"一班","五年级",5,2017,2018});
        jdbc.update("INSERT INTO `sm_clazz_history`(`clazzId`,`clazzName`,`gradeName`,`gradeLevel`,`yearStarts`,`yearEnds`) VALUES (?,?,?,?,?,?)",
                new Object[]{clazzId.id(),"一班","六年级",6,2018,2019});
        jdbc.update("INSERT INTO `sm_teacher_contact`(`personId`,`category`,`name`,`info`) VALUES (?,?,?,?)",
                new Object[]{personId.id(),"Phone","手机","1235689874"});
        jdbc.update("INSERT INTO `sm_teacher_contact`(`personId`,`category`,`name`,`info`) VALUES (?,?,?,?)",
                new Object[]{personId.id(),"QQ","QQ","256320"});

        jdbc.update("INSERT INTO `sm_teacher_management`(`teacherId`,`schoolId`,`clazzId`,`job`,`gradeName`,`gradeLevel`,`yearStarts`,`yearEnds`,`dateStarts`,`dateEnds`) VALUES (?,?,?,?,?,?,?,?,?,?)",
                new Object[]{teacherId.id(),schoolId.id(),clazzId.id(),"a","四年级",4,2016,2017,"2016-09-01","2017-06-30"});
        jdbc.update("INSERT INTO `sm_teacher_management`(`teacherId`,`schoolId`,`clazzId`,`job`,`gradeName`,`gradeLevel`,`yearStarts`,`yearEnds`,`dateStarts`,`dateEnds`) VALUES (?,?,?,?,?,?,?,?,?,?)",
                new Object[]{teacherId.id(),schoolId.id(),clazzId.id(),"a","五年级",5,2017,2018,"2017-09-01","2018-06-30"});
        jdbc.update("INSERT INTO `sm_teacher_management`(`teacherId`,`schoolId`,`clazzId`,`job`,`gradeName`,`gradeLevel`,`yearStarts`,`yearEnds`,`dateStarts`,`dateEnds`) VALUES (?,?,?,?,?,?,?,?,?,?)",
                new Object[]{teacherId.id(),schoolId.id(),clazzId.id(),"a","六年级",6,2018,2019,"2018-09-01",null});

        TeacherData teacher = api.getTeacher(personId, schoolId);
        assertNotNull(teacher);
        assertEquals("T",teacher.getName());
        assertEquals(personId.id(),teacher.getPersonId());
        assertEquals(teacherId.id(),teacher.getTeacherId());
        assertEquals(2,teacher.getContacts().size());
        assertEquals("1235689874",teacher.getContacts().get(0).getValue());
        assertEquals(1,teacher.getClazzes().size());
        assertEquals("六年级",teacher.getClazzes().get(0).getGradeName());
        assertEquals(6,teacher.getClazzes().get(0).getGradeLevel());
    }

    @Test
    public void getClazz(){
        ClazzId clazzId = new ClazzId();
        SchoolId schoolId = new SchoolId();
        StudyYear year = StudyYear.now();
        jdbc.update("INSERT INTO `sm_clazz`(`clazzId`,`schoolId`,`openedTime`,`closedTime`,`clazzType`,`removed`) VALUES(?,?,?,?,?,?)",
                new Object[]{clazzId.id(),schoolId.id(),"2017-09-01",null,"United",0}
                );
        jdbc.update("INSERT INTO `sm_clazz_history`(`clazzId`,`clazzName`,`gradeName`,`gradeLevel`,`yearStarts`,`yearEnds`) VALUES (?,?,?,?,?,?)",
                new Object[]{clazzId.id(),"一班","五年级",5,year.getYearStarts()-1,year.getYearEnds()-1});
        jdbc.update("INSERT INTO `sm_clazz_history`(`clazzId`,`clazzName`,`gradeName`,`gradeLevel`,`yearStarts`,`yearEnds`) VALUES (?,?,?,?,?,?)",
                new Object[]{clazzId.id(),"一班","六年级",6,year.getYearStarts(),year.getYearEnds()});

        ClazzData clazzData = api.getClazz(clazzId);
        assertEquals("六年级",clazzData.getGradeName());
        assertEquals("一班",clazzData.getClazzName());
        assertEquals(6,clazzData.getGradeLevel());
        assertEquals(clazzId.id(),clazzData.getClazzId());
        assertEquals(schoolId.id(),clazzData.getSchoolId());
        assertEquals("United",clazzData.getType());
    }

    @Test
    public void getClazzStudents(){
        ClazzId clazzId = new ClazzId();
        SchoolId schoolId = new SchoolId();
        StudyYear year = StudyYear.now();
        jdbc.update("INSERT INTO `sm_clazz`(`clazzId`,`schoolId`,`openedTime`,`closedTime`,`clazzType`,`removed`) VALUES(?,?,?,?,?,?)",
                new Object[]{clazzId.id(),schoolId.id(),(year.getYearStarts()-2)+ "-09-01",null,"United",0}
        );
        jdbc.update("INSERT INTO `sm_clazz_history`(`clazzId`,`clazzName`,`gradeName`,`gradeLevel`,`yearStarts`,`yearEnds`) VALUES (?,?,?,?,?,?)",
                new Object[]{clazzId.id(),"一班","四年级",4,year.getYearStarts()-2,year.getYearEnds()-2});
        jdbc.update("INSERT INTO `sm_clazz_history`(`clazzId`,`clazzName`,`gradeName`,`gradeLevel`,`yearStarts`,`yearEnds`) VALUES (?,?,?,?,?,?)",
                new Object[]{clazzId.id(),"一班","五年级",5,year.getYearStarts()-1,year.getYearEnds()-1});
        jdbc.update("INSERT INTO `sm_clazz_history`(`clazzId`,`clazzName`,`gradeName`,`gradeLevel`,`yearStarts`,`yearEnds`) VALUES (?,?,?,?,?,?)",
                new Object[]{clazzId.id(),"一班","六年级",6,year.getYearStarts(),year.getYearEnds()});

        for(int i=0;i<10;i++){
            StudentId studentId = new StudentId();
            jdbc.update("INSERT INTO `sm_student`(`studentId`,`schoolId`,`personId`,`name`,`gender`,`birthday`,`joinDate`,`offDate`,`removed`) VALUES (?,?,?,?,?,?,?,?,?)",
                    new Object[]{studentId.id(),schoolId.id(),new PersonId().id(),"Name"+i,
                            Gender.Female.name(), DateUtilWrapper.now(),DateUtilWrapper.now(),null,0});
            jdbc.update("INSERT INTO `sm_student_managed`(`studentId`,`schoolId`,`clazzId`,`job`,`gradeName`,`gradeLevel`,`yearStarts`,`yearEnds`,`dateStarts`,`dateEnds`) VALUES (?,?,?,?,?,?,?,?,?,?)",
                    new Object[]{studentId.id(),schoolId.id(),clazzId.id(),"a","四年级",4,year.getYearStarts()-2,year.getYearEnds()-2,"2016-09-01","2017-06-30"});
            jdbc.update("INSERT INTO `sm_student_managed`(`studentId`,`schoolId`,`clazzId`,`job`,`gradeName`,`gradeLevel`,`yearStarts`,`yearEnds`,`dateStarts`,`dateEnds`) VALUES (?,?,?,?,?,?,?,?,?,?)",
                    new Object[]{studentId.id(),schoolId.id(),clazzId.id(),"a","五年级",5,year.getYearStarts()-1,year.getYearEnds()-1,"2017-09-01","2018-06-30"});
            jdbc.update("INSERT INTO `sm_student_managed`(`studentId`,`schoolId`,`clazzId`,`job`,`gradeName`,`gradeLevel`,`yearStarts`,`yearEnds`,`dateStarts`,`dateEnds`) VALUES (?,?,?,?,?,?,?,?,?,?)",
                    new Object[]{studentId.id(),schoolId.id(),clazzId.id(),"a","六年级",6,year.getYearStarts(),year.getYearEnds(),"2018-09-01",null});
        }

        List<StudentData> students = api.getClazzStudents(clazzId);
        assertEquals(10,students.size());
        assertEquals("Name0",students.get(0).getName());
        assertEquals(schoolId.id(),students.get(0).getSchoolId());

        assertEquals("Name9",students.get(9).getName());
        assertEquals(schoolId.id(),students.get(9).getSchoolId());
    }


    @Test
    public void getAllTeacherPersonIds() {
        SchoolId schoolId = new SchoolId();
        String sql = "INSERT INTO `sm_teacher`(`teacherId`,`personId`,`schoolId`,`name`,`gender`,`birthday`,`joinDate`,`offDate`,`removed`) VALUES(?,?,?,?,?,?,?,?,?)";
        List<Object[]> args = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            args.add(new Object[]{new TeacherId().id(),new PersonId().id(),schoolId.id(),"T"+i,Gender.Female.name(),null,"2015-09-01",null,0});
        }
        jdbc.batchUpdate(sql, args);
        List<PersonId> teacherPersonIds = api.getAllTeacherPersonIds(schoolId);
        assertEquals(10,teacherPersonIds.size());
        assertEquals(args.get(0)[1],teacherPersonIds.get(0).id());
        assertEquals(args.get(5)[1],teacherPersonIds.get(5).id());
        assertEquals(args.get(9)[1],teacherPersonIds.get(9).id());
    }

    @Test
    public void getSchool() {
        String sql = "INSERT INTO `sm_school`(`schoolId`,`tenantId`,`name`,`alias`,`scope`,`removed`) VALUES (?,?,?,?,?,?)";
        List<Object[]> args = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            args.add(new Object[]{new SchoolId().id(),new TenantId().id(),"TTT"+i,"T"+i,SchoolScope.Primary.name(),0});
        }
        jdbc.batchUpdate(sql, args);
        SchoolData school = api.getSchool(new SchoolId(args.get(5)[0]+""));
        assertEquals(args.get(5)[0],school.getSchoolId());
        school = api.getSchool(new SchoolId(args.get(0)[0]+""));
        assertEquals(args.get(0)[0],school.getSchoolId());
        school = api.getSchool(new SchoolId(args.get(8)[0]+""));
        assertEquals(args.get(8)[0],school.getSchoolId());
    }

    @Test
    public void getSchoolClazzs() {
        SchoolId schoolId = new SchoolId();
        StudyYear year = StudyYear.now();

        for (int i = 1; i <=5; i++) {
            ClazzId clazzId = new ClazzId();
            jdbc.update("INSERT INTO `sm_clazz`(`clazzId`,`schoolId`,`openedTime`,`closedTime`,`clazzType`,`removed`) VALUES(?,?,?,?,?,?)",
                    new Object[]{clazzId.id(), schoolId.id(), (year.getYearStarts()-1)+ "-09-01", null, "United", 0}
            );
            jdbc.update("INSERT INTO `sm_clazz_history`(`clazzId`,`clazzName`,`gradeName`,`gradeLevel`,`yearStarts`,`yearEnds`) VALUES (?,?,?,?,?,?)",
                    new Object[]{clazzId.id(), i+"班", "五年级", 5, year.getYearStarts() - 1, year.getYearEnds() - 1});
            jdbc.update("INSERT INTO `sm_clazz_history`(`clazzId`,`clazzName`,`gradeName`,`gradeLevel`,`yearStarts`,`yearEnds`) VALUES (?,?,?,?,?,?)",
                    new Object[]{clazzId.id(), i+"班", "六年级", 6, year.getYearStarts(), year.getYearEnds()});
        }

        List<ClazzData> clazzData = api.getSchoolClazzs(schoolId);
        assertEquals(5,clazzData.size());

        clazzData = api.getSchoolClazzs(new SchoolId());
        assertEquals(0,clazzData.size());
    }
}