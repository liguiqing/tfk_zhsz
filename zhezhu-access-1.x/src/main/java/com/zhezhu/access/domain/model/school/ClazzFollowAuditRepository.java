package com.zhezhu.access.domain.model.school;

import com.zhezhu.commons.domain.EntityRepository;
import com.zhezhu.share.domain.id.access.ClazzFollowAuditId;
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
    @Query("From ClazzFollowAudit where auditId=?1")
    ClazzFollowAudit loadOf(ClazzFollowAuditId auditId);

    @Override
    @CacheEvict(value = "accessCache", key="#p0.auditId.id")
    void save(ClazzFollowAudit audit);

    @CacheEvict(value = "accessCache",key="#p0.id")
    @Modifying
    @Query(value = "DELETE from ClazzFollowAudit where auditId=?1")
    void delete(ClazzFollowAuditId auditId);
}