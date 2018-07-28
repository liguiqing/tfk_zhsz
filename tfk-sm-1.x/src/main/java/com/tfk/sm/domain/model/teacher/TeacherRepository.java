package com.tfk.sm.domain.model.teacher;

import com.tfk.commons.domain.EntityRepository;
import com.tfk.share.domain.id.PersonId;

import java.util.List;

/**
 * @author Liguiqing
 * @since V3.0
 */

public interface TeacherRepository extends EntityRepository<Teacher,PersonId> {

    List<ClazzTeaching> findTeaching(PersonId personId);

    List<ClazzManagement> findManagement(PersonId personId);
}