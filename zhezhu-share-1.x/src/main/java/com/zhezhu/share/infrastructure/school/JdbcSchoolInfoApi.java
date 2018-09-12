package com.zhezhu.share.infrastructure.school;

import com.zhezhu.commons.lang.Throwables;
import com.zhezhu.share.domain.common.Period;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.identityaccess.TenantId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.domain.school.Term;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

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
    public Period getSchoolTermPeriod(SchoolId schoolId) {
        return Term.defaultPeriodOfThisTerm();
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
                            .clazzes(getTeacherClazzes(rs.getString("teacherId")))
                            .contacts(getTeacherContacts(personId.id()))
                            .build()
                , personId.id(), schoolId.id());
        }catch (Exception e){
            log.debug(Throwables.toString(e));
        }
        return null;
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
        String sql = "select `name`,info from sm_student_contact where personId=? ";
        return jdbc.query(sql,(rs,rowNum) ->
                        new ContactData(rs.getString("name"),rs.getString("info"))
                ,personId);
    }

    private List<ClazzData> getTeacherClazzes(String teacherId){
        String sql = "select a.clazzId,b.clazzName,a.gradeName,a.gradeLevel,a.job " +
                "from sm_teacher_management a " +
                "inner join sm_clazz_history b on b.clazzId = a.clazzId and a.yearEnds=b.yearEnds " +
                "where  a.dateEnds is null and a.teacherId=?";
        return jdbc.query(sql,(rs,rowNum) ->
                        ClazzData.builder()
                                .clazzName(rs.getString("clazzName"))
                                .clazzId(rs.getString("clazzId"))
                                .gradeName(rs.getString("gradeName"))
                                .gradeLevel(rs.getInt("gradeLevel"))
                                .type("United")
                                .build()
                ,teacherId);
    }
    private List<ContactData> getTeacherContacts(String personId){
        String sql = "select `name`,info from sm_teacher_contact where personId=? ";
        return jdbc.query(sql,(rs,rowNum) ->
                        new ContactData(rs.getString("name"),rs.getString("info"))
                ,personId);
    }

}