package com.tfk.assessment.domain.model.assesse;

import com.tfk.commons.domain.EntityRepository;
import com.tfk.share.domain.id.assessment.AssessId;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Repository("AssesseRepository")
public interface AssessRepository extends EntityRepository<Assess,AssessId> {

    @Override
    default  AssessId nextIdentity() {
        return new AssessId();
    }

    @Override
    @CacheEvict(value = "asCache", key="#p0.assessId.id")
    void save(Assess assess);

    @Cacheable(value = "asCache",key = "#p0.id",unless = "#result == null")
    @Query("From Assess where assessId=?1 and removed=0")
    Assess loadOf(AssessId assessId);

    @CacheEvict(value = "asCache",key="#p0.id")
    @Modifying
    @Query(value = "update Assess set removed = 1 where assessId=?1")
    void delete(AssessId assessId);

}