package com.tfk.sm.domain.model.clazz;

import com.tfk.commons.persistence.EntityRepository;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.school.Course;

import java.util.List;

/**
 * @author Liguiqing
 * @since V3.0
 */

public interface ClazzRepository extends EntityRepository<ClazzId,Clazz> {

    List<Course> findCanBeStudedCourses(ClazzId clazzId);
}