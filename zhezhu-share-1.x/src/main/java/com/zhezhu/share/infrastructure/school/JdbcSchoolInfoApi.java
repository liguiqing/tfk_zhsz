package com.zhezhu.share.infrastructure.school;

import com.google.common.collect.Lists;
import com.zhezhu.commons.lang.Throwables;
import com.zhezhu.commons.util.CollectionsUtilWrapper;
import com.zhezhu.share.domain.common.Period;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.identityaccess.TenantId;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.domain.school.StudyYear;
import com.zhezhu.share.domain.school.Term;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 通过JDBC实现的学校信息API
 *
 * @author Liguiqing
 * @since V3.0
 */
@Slf4j
@Component("defaultSchoolInfoApi")
public class JdbcSchoolInfoApi implements SchoolInfoApi {

    @Autowired
    private JdbcTemplate jdbc ;

    public List<SchoolData> getAllSchool(){
        String sql = "select * from sm_school where removed = 0";
        return jdbc.query(sql,(rs,rowNum) ->
                        SchoolData.builder()
                                .tenantId(rs.getString("tenantId"))
                                .schoolId(rs.getString("schoolId"))
                                .name(rs.getString("name"))
                                .alias(rs.getString("alias"))
                                .scope(rs.getString("scope"))
                                .build());
    }

    @Override
    public TenantId getSchoolTenantId(SchoolId schoolId) {
        String sql = "select tenantId from sm_school where schoolId=?";
        try {
            return jdbc.queryForObject(sql, (rs, rn) -> new TenantId(rs.getString("tenantId")), schoolId.id());
        }catch (Exception e){
            log.debug(Throwables.toString(e));
        }
        return null;
    }


    @Override
    public Period getSchoolTermPeriod(SchoolId schoolId) {
        return Term.defaultPeriodOfThisTerm();
    }

    @Override
    public List<PersonId> getAllStudentPersonIds(SchoolId schoolId) {
        String sql = "select personId from sm_student where schoolId = ? and removed=0";
        return jdbc.query(sql,(rs,rowNum) -> new PersonId(rs.getString("personId")),schoolId.id());
    }

    @Override
    public List<PersonId> getAllTeacherPersonIds(SchoolId schoolId) {
        String sql = "select personId from sm_teacher where schoolId = ? and removed=0";
        return jdbc.query(sql,(rs,rowNum) -> new PersonId(rs.getString("personId")),schoolId.id());
    }

    @Override
    public SchoolData getSchool(SchoolId schoolId) {
        String sql = "select schoolId,name,alias,scope from sm_school where schoolId=? and removed=0 ";
        try{
            return jdbc.queryForObject(sql, (rs, rn) ->
                            SchoolData.builder()
                                    .schoolId(schoolId.id())
                                    .name(rs.getString("name"))
                                    .alias(rs.getString("alias"))
                                    .scope(rs.getString("scope"))
                                    .build()
                    , schoolId.id());
        }catch (Exception e){
            log.debug(Throwables.toString(e));
        }
        return null;
    }

    @Override
    public ClazzData getClazz(ClazzId clazzId) {
        StudyYear year = StudyYear.now();
        String sql = "select a.schoolId, a.clazzId,a.clazzType,b.gradeName,b.gradeLevel,b.clazzName ,a.clazzType " +
                "from sm_clazz a inner join sm_clazz_history b on b.clazzId=a.clazzId " +
                "where a.clazzId=? and a.removed=0 and b.yearStarts=? and b.yearEnds=?";
        try{
            return jdbc.queryForObject(sql,(rs,rowNum) ->
                        ClazzData.builder()
                                .schoolId(rs.getString("schoolId"))
                                .clazzName(rs.getString("clazzName"))
                                .clazzId(rs.getString("clazzId"))
                                .gradeName(rs.getString("gradeName"))
                                .gradeLevel(rs.getInt("gradeLevel"))
                                .type(rs.getString("clazzType"))
                                .build()
                ,clazzId.id(),year.getYearStarts(),year.getYearEnds());
        }catch (Exception e){
            log.debug(Throwables.toString(e));
        }
        return null;
    }

    @Override
    public StudentData getStudent(PersonId personId) {
        String sql = "select studentId,schoolId,`name`,gender from sm_student where personId=? and removed=0 and offDate is null";
        try{
            return jdbc.queryForObject(sql,(rs,rn) ->
                 StudentData.builder()
                         .name(rs.getString("name"))
                         .schoolId(rs.getString("schoolId"))
                         .studentId(rs.getString("studentId"))
                         .personId(personId.id())
                         .gender(rs.getString("gender"))
                         .clazzes(getStudentClazzes(rs.getString("studentId")))
                         .contacts(getStudentContacts(personId.id()))
                         .build()
            ,personId.id());
        }catch (Exception e){
            log.debug(Throwables.toString(e));
        }
        return null;
    }


    @Override
    public TeacherData getTeacher(PersonId personId, SchoolId schoolId) {
        String sql = "select teacherId,name,gender from sm_teacher where personId =? and schoolId=? and removed=0 ";

        try{
            return jdbc.queryForObject(sql, (rs, rn) ->
                    TeacherData.builder()
                            .schoolId(schoolId.id())
                            .teacherId(rs.getString("teacherId"))
                            .name(rs.getString("name"))
                            .personId(personId.id())
                            .clazzes(getTeacherMgrClazzs(rs.getString("teacherId")))
                            .contacts(getTeacherContacts(personId.id()))
                            .build()
                , personId.id(), schoolId.id());
        }catch (Exception e){
            log.debug(Throwables.toString(e));
        }
        return null;
    }

    public List<TeacherData> getClazzTeachers(ClazzId clazzId){
        ClazzData clazz = getClazz(clazzId);
        StudyYear year = StudyYear.now();
        String sqlManaged = "select a.gradeName,a.gradeLevel,b.name,b.personId,b.teacherId " +
                "from sm_teacher_management a inner join sm_teacher b on b.teacherId = a.teacherId " +
                "where a.dateEnds is null and a.clazzId=? and a.yearStarts=? and a.yearEnds=?";
        String sqlTeach = "select a.courseAlias,a.courseName,a.gradeName,a.gradeLevel,b.name,b.personId,b.teacherId " +
                "from sm_teacher_teaching a inner join sm_teacher b on b.teacherId = a.teacherId" +
                " where a.dateEnds is null and a.clazzId=? and a.yearStarts=? and a.yearEnds=?" ;
        List<TeacherData> teachers = jdbc.query(sqlManaged,(rs,rn) ->
                    TeacherData.builder()
                            .name(rs.getString("name"))
                            .personId(rs.getString("personId"))
                            .teacherId(rs.getString("teacherId"))
                            .schoolId(clazz.getSchoolId())
                            .contacts(getTeacherContacts(rs.getString("personId")))
                            .build().asMaster(clazz),
                new Object[]{clazz.getClazzId(),year.getYearStarts(),year.getYearEnds()});

        List<TeacherData> teachers2 = jdbc.query(sqlTeach,(rs,rn) ->
                        TeacherData.builder()
                                .name(rs.getString("name"))
                                .personId(rs.getString("personId"))
                                .teacherId(rs.getString("teacherId"))
                                .schoolId(clazz.getSchoolId())
                                .contacts(getTeacherContacts(rs.getString("personId")))
                                .build().asTeacher(clazz,rs.getString("courseName")),
                new Object[]{clazz.getClazzId(),year.getYearStarts(),year.getYearEnds()});

        if(CollectionsUtilWrapper.isNullOrEmpty(teachers))
            return teachers2;

        if(CollectionsUtilWrapper.isNullOrEmpty(teachers))
            return teachers2;

        Map<String, List<TeacherData>> mgrs = this.grouping(teachers);
        Map<String,List<TeacherData>> teachings = this.grouping(teachers2);
        teachings.entrySet().stream().map(v->mgrs.merge(v.getKey(),v.getValue(),this::merge)).count();
        ArrayList<TeacherData> tss = new ArrayList<>();
        mgrs.values().forEach(ts->tss.addAll(ts));
        return tss;
    }

    private Map<String,List<TeacherData>> grouping(List<TeacherData> teachers){
        return teachers.stream().collect(Collectors.groupingBy(TeacherData::getPersonId))
                .entrySet().stream().collect(Collectors.toMap(
                e -> e.getKey(),
                e -> e.getValue()
        ));
    }

    private List<TeacherData> merge(List<TeacherData> dest,List<TeacherData> src){
        src.forEach(teacher -> teacher.getClazzes().forEach(clazz->dest.forEach(teacher2->teacher2.addClazz(clazz))));
        return dest;
    }

    @Override
    public List<StudentData> getClazzStudents(ClazzId clazzId) {
        ClazzData clazz = getClazz(clazzId);
        StudyYear year = StudyYear.now();
        String sql = "select a.personId,a.studentId,a.name,gender from sm_student a inner join sm_student_managed b on b.studentId=a.studentId  " +
                "where b.clazzId=? and b.yearStarts=? and b.yearEnds=?";
        if(!"United".equals(clazz.getType())){
            sql = "select a.personId,a.studentId,a.name,gender from sm_student a inner join sm_student_study b on b.studentId=a.studentId  " +
                    "where b.clazzId=? and b.yearStarts=? and b.yearEnds=?";
        }
        return jdbc.query(sql,(rs,rn) ->
                        StudentData.builder()
                                .name(rs.getString("name"))
                                .schoolId(clazz.getSchoolId())
                                .studentId(rs.getString("studentId"))
                                .personId(rs.getString("personId"))
                                .gender(rs.getString("gender"))
                                .contacts(getStudentContacts(rs.getString("studentId")))
                                .build()
                ,clazzId.id(),year.getYearStarts(),year.getYearEnds());
    }

    @Override
    public List<ClazzData> getSchoolClazzs(SchoolId schoolId) {
        //目前只查询了管理班级
        StudyYear year = StudyYear.now();
        String sql = "select a.clazzId,a.clazzType,b.gradeName,b.gradeLevel,b.clazzName,a.clazzType,a.openedTime " +
                "from sm_clazz a inner join sm_clazz_history b on b.clazzId=a.clazzId " +
                "where a.schoolId=? and a.removed=0 and a.closedTime is null and b.yearStarts=? and b.yearEnds=?";
        return jdbc.query(sql,(rs,rowNum) ->
                        ClazzData.builder()
                                .clazzName(rs.getString("clazzName"))
                                .clazzId(rs.getString("clazzId"))
                                .gradeName(rs.getString("gradeName"))
                                .gradeLevel(rs.getInt("gradeLevel"))
                                .openedTime(rs.getDate("openedTime"))
                                .type(rs.getString("clazzType"))
                                .build()
                ,schoolId.id(),year.getYearStarts(),year.getYearEnds());
    }

    private List<ClazzData> getStudentClazzes(String studentId){
        //目前只查询了管理班级
        String sql = "select a.clazzId,a.gradeName,a.gradeLevel,b.clazzName " +
                "from sm_student_managed a " +
                "inner join sm_clazz_history b on b.clazzId = a.clazzId and a.yearEnds=b.yearEnds " +
                "where a.studentId=? and a.dateEnds is null ";
        return jdbc.query(sql,(rs,rowNum) ->
                        ClazzData.builder()
                                .clazzName(rs.getString("clazzName"))
                                .clazzId(rs.getString("clazzId"))
                                .gradeName(rs.getString("gradeName"))
                                .gradeLevel(rs.getInt("gradeLevel"))
                                .type("United")
                                .build()
                ,studentId);
    }

    private List<ContactData> getStudentContacts(String personId){
        String sql = "select `category`,`name`,info from sm_student_contact where personId=? ";
        return jdbc.query(sql,(rs,rowNum) ->
                        new ContactData(rs.getString("category"),rs.getString("name"),rs.getString("info"))
                ,personId);
    }

    /**
     * 查询老师为班主任的班级
     *
     * @param teacherId
     * @return
     */
    private List<TeachClazzData> getTeacherMgrClazzs(String teacherId){
        String sql = "select a.clazzId,b.clazzName,a.gradeName,a.gradeLevel,a.job " +
                "from sm_teacher_management a " +
                "inner join sm_clazz_history b on b.clazzId = a.clazzId and a.yearEnds=b.yearEnds " +
                "where  a.dateEnds is null and a.teacherId=?";
        return jdbc.query(sql,(rs,rowNum) ->
                        TeachClazzData.builder()
                                .clazz(ClazzData.builder()
                                    .clazzName(rs.getString("clazzName"))
                                    .clazzId(rs.getString("clazzId"))
                                    .gradeName(rs.getString("gradeName"))
                                    .gradeLevel(rs.getInt("gradeLevel"))
                                    .type("United").build())
                                .job("Header")
                                .build()
                ,teacherId);
    }
    private List<ContactData> getTeacherContacts(String personId){
        String sql = "select `category`,`name`,info from sm_teacher_contact where personId=? ";
        return jdbc.query(sql,(rs,rowNum) ->
                        new ContactData(rs.getString("category"),rs.getString("name"),rs.getString("info"))
                ,personId);
    }

}