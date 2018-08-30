package com.tfk.access.domain.model.school;

import com.tfk.commons.domain.EntityRepository;
import com.tfk.share.domain.id.access.ClazzFollowApplyId;
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
public interface ClazzFollowApplyRepository extends EntityRepository<ClazzFollowApply, ClazzFollowApplyId> {

    default ClazzFollowApplyId nextIdentity(){
        return new ClazzFollowApplyId();
    }


    @Cacheable(value = "accessCache",key = "#p0.id",unless = "#result == null")
    @Query("From ClazzFollowApply where applyId=?1")
    public abstract ClazzFollowApply loadOf(ClazzFollowApplyId applyId);

    @Override
    @CacheEvict(value = "accessCache", key="#p0.applyId.id")
    public abstract void save(ClazzFollowApply apply);

    @CacheEvict(value = "accessCache",key="#p0.id")
    @Modifying
    @Query(value = "DELETE from ClazzFollowApply where applyId=?1")
    public abstract void delete(ClazzFollowApplyId applyId);
}