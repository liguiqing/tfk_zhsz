package com.tfk.sm.domain.model.student;

import com.tfk.commons.domain.EntityRepository;
import com.tfk.share.domain.id.PersonId;

import java.util.List;

/**
 * @author Liguiqing
 * @since V3.0
 */

public interface StudentRepository extends EntityRepository<Student,PersonId> {

    List<Study> findStudentStudies(Student student);
}