package com.zhezhu.access.domain.model.wechat.audit;

import com.zhezhu.commons.domain.EntityRepository;
import com.zhezhu.share.domain.id.wechat.FollowApplyId;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Repository
public interface FollowApplyRepository extends EntityRepository<FollowApply,FollowApplyId> {

    default FollowApplyId nextIdentity(){
        return new FollowApplyId();
    }

    @Cacheable(value = "accessCache",key = "#p0.id",unless = "#result == null")
    @Query("From FollowApply where applyId=?1")
    FollowApply loadOf(FollowApplyId applyId);

    @Override
    @CacheEvict(value = "accessCache", key="#p0.applyId.id")
    void save(FollowApply apply);

    @CacheEvict(value = "accessCache",key="#p0.id")
    @Modifying
    @Query(value = "DELETE from FollowApply where applyId=?1")
    void delete(FollowApplyId applyId);

    @Query(value = "select a.* From ac_followapply a where a.auditId  is null LIMIT :page,:size",nativeQuery = true)
    List<FollowApply> findAllByAuditIdIsNull(@Param("page")int page, @Param("size")int size);

    @Query(value = "select a.* From ac_followapply a where a.auditId  is not null LIMIT :page,:size",nativeQuery = true)
    List<FollowApply> findAllByAuditIdIsNotNull(@Param("page")int page, @Param("size")int size);

}