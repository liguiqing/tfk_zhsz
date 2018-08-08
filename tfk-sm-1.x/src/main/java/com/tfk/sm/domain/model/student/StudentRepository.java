package com.tfk.sm.domain.model.student;

import com.tfk.commons.domain.EntityRepository;
import com.tfk.share.domain.id.school.StudentId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Repository
public interface StudentRepository extends EntityRepository<Student,StudentId> {

    default StudentId nextIdentity() {
        return new StudentId();
    }

    @Query("FROM Student  where removed=0 and studentId=?1")
    Student loadOf(StudentId studentId);

    //List<Study> findStudentStudies(Student student);
}