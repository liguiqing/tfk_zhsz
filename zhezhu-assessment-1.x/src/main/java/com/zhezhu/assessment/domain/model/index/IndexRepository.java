package com.zhezhu.assessment.domain.model.index;

import com.zhezhu.commons.domain.EntityRepository;
import com.zhezhu.share.domain.id.identityaccess.TenantId;
import com.zhezhu.share.domain.id.index.IndexId;
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
@Repository("IndexRepository")
public interface IndexRepository extends EntityRepository<Index,IndexId> {

    @Override
    default IndexId nextIdentity(){
        return new IndexId();
    }

    @Cacheable(value = "asCache",key = "#p0.id",unless = "#result == null")
    @Query("From Index where indexId=?1 and removed=0")
    Index loadOf(IndexId indexId);

    @Override
    @CacheEvict(value = "asCache", key="#p0.indexId.id")
    void save(Index index);

    @CacheEvict(value = "asCache",key="#p0")
    @Modifying
    @Query(value = "update as_Index set removed = 1 where indexId=:indexId",nativeQuery = true)
    void delete(@Param("indexId")String indexId);

    List<Index> findAllByNameAndOwnerAndCategory(String name, TenantId owner, IndexCategory category);

    List<Index> findAllByOwnerAndParentIsNull(TenantId owner);

    List<Index> findAllByOwnerAndParentIsNullAndGroup(TenantId owner,String group);

    List<Index> findAllByNameAndCategoryAndOwnerIsNull(String name, IndexCategory category);

    List<Index> findAllByNameAndCategoryAndOwnerIsNullAndParentIsNull(String name,IndexCategory category);

    List<Index> findAllByOwnerIsNullAndParentIsNull();
}