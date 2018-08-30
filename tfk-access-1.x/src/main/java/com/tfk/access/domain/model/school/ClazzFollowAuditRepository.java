package com.tfk.access.domain.model.school;

import com.tfk.commons.domain.EntityRepository;
import com.tfk.share.domain.id.access.ClazzFollowAuditId;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Repository
public interface ClazzFollowAuditRepository extends EntityRepository<ClazzFollowAudit, ClazzFollowAuditId> {

    default ClazzFollowAuditId nextIdentity(){
        return new ClazzFollowAuditId();
    }

    @Cacheable(value = "accessCache",key = "#p0.id",unless = "#result == null")
    @Query("From ClazzFollowAudit where applyId=?1")
    ClazzFollowAudit loadOf(ClazzFollowAuditId auditId);

    @Override
    @CacheEvict(value = "accessCache", key="#p0.auditId.id")
    void save(ClazzFollowAudit audit);

    @CacheEvict(value = "accessCache",key="#p0.id")
    @Modifying
    @Query(value = "DELETE from FollowApply where applyId=?1")
    void delete(ClazzFollowAuditId auditId);
}