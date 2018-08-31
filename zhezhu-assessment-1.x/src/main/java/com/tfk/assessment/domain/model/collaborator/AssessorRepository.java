package com.tfk.assessment.domain.model.collaborator;

import com.tfk.commons.domain.EntityRepository;
import com.tfk.share.domain.id.assessment.AssessorId;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Liguiqing
 * @since V3.0
 */

@Repository("AssessorRepository")
public interface AssessorRepository extends EntityRepository<Assessor,AssessorId> {
    @Override
    default AssessorId nextIdentity() {
        return new AssessorId();
    }

    @Override
    @CacheEvict(value = "asCache", key="#p0.assessorId.id")
    void save(Assessor assessor);

    @Cacheable(value = "asCache",key = "#p0.id",unless = "#result == null")
    @Query("From Assessor where assessorId=?1")
    Assessor loadOf(AssessorId assessorId);

    @CacheEvict(value = "asCache",key="#p0.id")
    @Modifying
    @Query(value = "delete from  Assessor where assessorId=?1")
    void delete(AssessorId assessorId);
}