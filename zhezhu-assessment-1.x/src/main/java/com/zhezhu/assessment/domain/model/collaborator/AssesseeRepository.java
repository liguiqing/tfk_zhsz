package com.zhezhu.assessment.domain.model.collaborator;

import com.zhezhu.commons.domain.EntityRepository;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.assessment.AssesseeId;
import com.zhezhu.share.domain.id.school.SchoolId;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Liguiqing
 * @since V3.0
 */

@Repository("AssesseeRepository")
public interface AssesseeRepository extends EntityRepository<Assessee,AssesseeId> {
    @Override
    default AssesseeId nextIdentity() {
        return new AssesseeId();
    }

    @Override
    @CacheEvict(value = "asCache", key="#p0.assesseeId.id")
    void save(Assessee assessee);

    @Cacheable(value = "asCache",key = "#p0.id",unless = "#result == null")
    @Query("From Assessee where assesseeId=?1")
    Assessee loadOf(AssesseeId assesseeId);

    @CacheEvict(value = "asCache",key="#p0.id")
    @Modifying
    @Query(value = "delete From Assessee where assesseeId=?1")
    void delete(AssesseeId assessorId);

    @Query(value = "From Assessee where collaborator.personId=?1 and collaborator.schoolId=?2")
    Assessee findByPersonIdAndSchoolId(PersonId personId, SchoolId schoolId);
}