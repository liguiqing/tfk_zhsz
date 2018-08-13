package com.tfk.assessment.infrastructure.school;

import com.tfk.share.domain.common.Period;
import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.id.identityaccess.TenantId;
import com.tfk.share.domain.id.school.SchoolId;
import com.tfk.share.domain.school.Term;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 学校数据服务
 *
 * @author Liguiqing
 * @since V3.0
 */

@Component
public class SchoolService {

    @Autowired(required = false)
    @Qualifier("schoolInfoApi")
    private SchoolInfoApi schoolInfoApi;

    @Autowired
    @Qualifier("defaulTchoolInfoApi")
    private SchoolInfoApi defaulTchoolInfoApi;

    private SchoolInfoApi api(){
        if(this.schoolInfoApi == null)
            return defaulTchoolInfoApi;
        return this.schoolInfoApi;
    }

    public Period getSchoolTermPeriod(SchoolId schoolId){
        return Term.defaultPeriodOfThisTerm();
    }

    public TenantId getSchoolTenantId(SchoolId schoolId){
        return api().getSchoolTenantId(schoolId);
    }

    public List<PersonId> getAllStudentPersonIds(SchoolId schoolId) {
        return api().getAllStudentPersonIds(schoolId);
    }
}