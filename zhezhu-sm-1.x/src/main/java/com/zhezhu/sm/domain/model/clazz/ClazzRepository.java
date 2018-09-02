package com.zhezhu.sm.domain.model.clazz;

import com.zhezhu.commons.domain.EntityRepository;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.domain.school.Grade;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Liguiqing
 * @since V3.0
 */
@NoRepositoryBean
public interface ClazzRepository<T extends Clazz> extends EntityRepository<T,ClazzId> {

    default  ClazzId nextIdentity(){
        return new ClazzId();
    }

    boolean supports(Clazz clazz);

    boolean supports(Class<? extends T> clazz);

    @Override
    @CacheEvict(value = "smCache", key="#p0.clazzId.id")
    void save(T clazz);

    @Cacheable(value = "smCache", key="#p0.id",unless = "#result == null")
    @Query(value = "from Clazz where clazzId=?1 and removed=0")
    T loadOf(ClazzId clazzId);

    @Modifying
    @Query(value = "update sm_clazz set removed = 1 where clazzId=:clazzId",nativeQuery = true)
    @CacheEvict(value = "smCache",key="#p0")
    void delete(@Param("clazzId") String clazzId);


    //Attention:No used removed
    @Query(value = "from Clazz c JOIN ClazzHistory b on b.clazzId=c.clazzId " +
            "where c.schoolId=?1  and b.grade=?2 ")
    List<Clazz> findClazzCanBeManagedOf(SchoolId SchoolId,Grade grade);

    @Query(value = "From Clazz where schoolId=?1")
    List<Clazz> findAllBySchoolId(SchoolId schoolId);
}