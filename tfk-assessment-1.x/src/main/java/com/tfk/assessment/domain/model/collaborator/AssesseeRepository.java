package com.tfk.assessment.domain.model.collaborator;

import com.tfk.commons.domain.EntityRepository;
import com.tfk.share.domain.id.assessment.AssesseeId;
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
}