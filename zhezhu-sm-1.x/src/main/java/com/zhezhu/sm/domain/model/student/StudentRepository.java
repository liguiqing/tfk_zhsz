package com.zhezhu.sm.domain.model.student;

import com.zhezhu.commons.domain.EntityRepository;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.domain.id.school.StudentId;
import com.zhezhu.share.domain.school.Grade;
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
@Repository("StudentRepository")
public interface StudentRepository extends EntityRepository<Student,StudentId> {

    default StudentId nextIdentity() {
        return new StudentId();
    }

    @Override
    @CacheEvict(value = "smCache", key="#p0.studentId.id")
    void save(Student student);

    @Cacheable(value = "smCache",key = "#p0.id",unless = "#result == null")
    @Query("FROM Student  where removed=0 and studentId=?1")
    Student loadOf(StudentId studentId);

    @CacheEvict(value = "smCache",key="#p0")
    @Modifying
    @Query(value = "update sm_student set removed = 1 where studentId=:studentId",nativeQuery = true)
    void delete(@Param("studentId") String studentId);

    @Query(value = "select a from Student a JOIN StudentManaged b on b.studentId=a.studentId where " +
            "a.schoolId=?1 and b.clazz.schoolId=?1 and b.clazz.clazzId=?2 and b.clazz.grade=?3")
    List<Student> findByManageds(SchoolId schoolId, ClazzId clazzId,Grade grade);

    @Query(value = "select a From Student a join Study b on b.studentId=a.studentId where " +
            "a.schoolId=?1 and b.clazz.schoolId=?1 and b.clazz.clazzId=?2 and b.clazz.grade=?3")
    List<Student> findByStudies(SchoolId schoolId, ClazzId clazzId,Grade grade);
}