package com.tfk.sm.domain.model.teacher;

import com.tfk.commons.domain.EntityRepository;
import com.tfk.share.domain.id.school.TeacherId;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Repository("TeacherRepository")
public interface TeacherRepository extends EntityRepository<Teacher,TeacherId> {

    @Override
    default TeacherId nextIdentity() {
        return new TeacherId();
    }

    @Cacheable(value = "smCache",key = "#p0.id",unless = "#result == null")
    @Query("from Teacher where teacherId=?1 and removed=0")
    Teacher loadOf(TeacherId teacherId);

    @Override
    @CacheEvict(value = "smCache", key="#p0.teacherId().id")
    void save(Teacher teacher);

    @CacheEvict(value = "smCache",key="#p0")
    @Modifying
    @Query(value = "update sm_teacher set removed = 1 where teacherId=:teacherId",nativeQuery = true)
    void delete(@Param("teacherId") String teacherId);
}