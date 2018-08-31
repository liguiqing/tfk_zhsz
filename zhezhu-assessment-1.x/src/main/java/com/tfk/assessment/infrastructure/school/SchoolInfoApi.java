package com.tfk.assessment.infrastructure.school;

import com.tfk.share.domain.common.Period;
import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.id.identityaccess.TenantId;
import com.tfk.share.domain.id.school.SchoolId;
import com.tfk.share.domain.school.Term;

import java.util.List;

/**
 * 学校信息查询接口
 *
 * @author Liguiqing
 * @since V3.0
 */

public interface SchoolInfoApi {
    default Period getSchoolTermPeriod(SchoolId schoolId){
        return Term.defaultPeriodOfThisTerm();
    }

    TenantId getSchoolTenantId(SchoolId schoolId);

    List<PersonId> getAllStudentPersonIds(SchoolId schoolId);
}