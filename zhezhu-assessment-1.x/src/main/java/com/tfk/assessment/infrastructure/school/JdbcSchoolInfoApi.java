package com.tfk.assessment.infrastructure.school;

import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.id.identityaccess.TenantId;
import com.tfk.share.domain.id.school.SchoolId;
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
@Component("defaulTchoolInfoApi")
public class JdbcSchoolInfoApi implements SchoolInfoApi {

    @Autowired
    private JdbcTemplate jdbc ;

    @Override
    public TenantId getSchoolTenantId(SchoolId schoolId) {
        String sql  = "select tenantId from sm_school where schoolId=?";
        return jdbc.queryForObject(sql,(rs,rn)->new TenantId(rs.getString("tenantId")),schoolId.id());
    }

    @Override
    public List<PersonId> getAllStudentPersonIds(SchoolId schoolId) {
        String sql = "select personId from sm_student where schoolId = ? and removed=0";
        return jdbc.query(sql,(rs,rowNum) -> new PersonId(rs.getString("personId")),schoolId.id());
    }
}