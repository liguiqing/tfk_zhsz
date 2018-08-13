package com.tfk.assessment.domain.model.medal;

import com.tfk.commons.domain.EntityRepository;
import com.tfk.share.domain.id.medal.MedalId;
import com.tfk.share.domain.id.school.SchoolId;

import java.util.List;

/**
 * @author Liguiqing
 * @since V3.0
 */

public interface MedalRepository extends EntityRepository<Medal,MedalId> {

    @Override
    default MedalId nextIdentity() {
        return new MedalId();
    }

    List<Medal> findMedalsBySchoolId(SchoolId schoolId);
}