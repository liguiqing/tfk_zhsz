package com.zhezhu.access.domain.model.school;

import com.zhezhu.commons.domain.EntityRepository;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.access.ClazzFollowApplyId;
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
public interface ClazzFollowApplyRepository extends EntityRepository<ClazzFollowApply, ClazzFollowApplyId> {

    default ClazzFollowApplyId nextIdentity(){
        return new ClazzFollowApplyId();
    }

    @Cacheable(value = "accessCache",key = "#p0.id",unless = "#result == null")
    @Query("From ClazzFollowApply where applyId=?1")
    ClazzFollowApply loadOf(ClazzFollowApplyId applyId);

    @Override
    @CacheEvict(value = "accessCache", key="#p0.applyId.id")
    void save(ClazzFollowApply apply);

    @CacheEvict(value = "accessCache",key="#p0.id")
    @Modifying
    @Query(value = "DELETE from ClazzFollowApply where applyId=?1")
    void delete(ClazzFollowApplyId applyId);

    List<ClazzFollowApply> findAllByApplierIdAndAuditIdIsNotNull(PersonId applierId);

    List<ClazzFollowApply> findAllByApplierIdAndAuditIdIsNull(PersonId applierId);

    @Query(value = "select * from ac_clazzfollowapply where auditId is null LIMIT :page,:size",nativeQuery = true)
    List<ClazzFollowApply> findAuditingByLimit(@Param("page")int page, @Param("size")int size);
}