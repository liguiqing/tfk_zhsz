package com.tfk.sm.domain.model.school;

import com.tfk.commons.domain.EntityRepository;
import com.tfk.share.domain.id.school.SchoolId;
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
@Repository("SchoolRepository")
public interface SchoolRepository extends EntityRepository<School,SchoolId> {

    default SchoolId nextIdentity() {
        return new SchoolId();
    }

    @Override
    @CacheEvict(value = "smCache", key="#p0.schoolId.id")
    void save(School school);

    @Cacheable(value = "smCache",key = "#p0.id",unless = "#result == null")
    @Query(value = "from School where schoolId=?1 and removed=0")
    School loadOf(SchoolId schoolId);

    School findSchoolByNameEquals(String name);

    @Modifying
    @Query(value = "update sm_school set removed = 1 where schoolId=:schoolId",nativeQuery = true)
    @CacheEvict(value = "smCache",key="#p0")
    void delete(@Param("schoolId") String schoolId);

    @Query(value = "select * from sm_School where removed=0 LIMIT :page,:size",nativeQuery = true)
    List<School> findByLimit(@Param("page")int page,@Param("size")int size);
}