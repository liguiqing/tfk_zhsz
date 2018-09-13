package com.zhezhu.share.infrastructure.school;

import com.zhezhu.share.domain.common.Period;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.identityaccess.TenantId;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.domain.school.Term;

import java.util.List;

/**
 * 学校信息查询接口
 *
 * @author Liguiqing
 * @since V3.0
 */

public interface SchoolInfoApi {
    Period getSchoolTermPeriod(SchoolId schoolId);

    TenantId getSchoolTenantId(SchoolId schoolId);

    List<PersonId> getAllStudentPersonIds(SchoolId schoolId);

    List<PersonId> getAllTeacherPersonIds(SchoolId schoolId);

    SchoolData getSchool(SchoolId schoolId);

    ClazzData getClazz(ClazzId clazzId);

    StudentData getStudent(PersonId personId);

    TeacherData getTeacher(PersonId personId,SchoolId schoolId);

    List<StudentData> getClazzStudents(ClazzId clazzId);

    List<ClazzData> getSchoolClazzs(SchoolId schoolId);
}