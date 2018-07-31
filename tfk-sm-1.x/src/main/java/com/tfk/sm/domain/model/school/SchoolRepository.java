package com.tfk.sm.domain.model.school;

import com.tfk.commons.domain.EntityRepository;
import com.tfk.share.domain.id.school.SchoolId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * @author Liguiqing
 * @since V3.0
 */
@Repository
public interface SchoolRepository extends EntityRepository<School,SchoolId> {

    default SchoolId nextIdentity() {
        return new SchoolId();
    }

    @Query(value = "from School where schoolId=?1 and removed=0")
    School loadOf(SchoolId schoolId);

    @Modifying
    @Query(value = "update sm_school set removed = 1 where schoolId=:schoolId",nativeQuery = true)
    void delete(@Param("schoolId") String schoolId);
}