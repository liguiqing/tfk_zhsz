package com.zhezhu.access.domain.model.wechat.audit;

import com.zhezhu.commons.domain.EntityRepository;
import com.zhezhu.share.domain.id.wechat.FollowAuditId;
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
public interface FollowAuditRepository extends EntityRepository<FollowAudit,FollowAuditId> {

    @Override
    default FollowAuditId nextIdentity(){
        return new FollowAuditId();
    }

    @Cacheable(value = "accessCache",key = "#p0.id",unless = "#result == null")
    @Query("From FollowAudit where auditId=?1")
    FollowAudit loadOf(FollowAuditId auditId);

    @Override
    @CacheEvict(value = "accessCache", key="#p0.auditId.id")
    void save(FollowAudit audit);


    @CacheEvict(value = "accessCache",key="#p0.id")
    @Modifying
    @Query(value = "DELETE from FollowAudit where auditId=?1")
    void delete(FollowAuditId auditId);
}