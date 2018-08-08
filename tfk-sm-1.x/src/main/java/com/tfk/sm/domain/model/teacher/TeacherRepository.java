package com.tfk.sm.domain.model.teacher;

import com.tfk.commons.domain.EntityRepository;
import com.tfk.share.domain.id.school.TeacherId;
import org.springframework.data.jpa.repository.Query;
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

    @Query("from Teacher where teacherId=?1 and removed=0")
    Teacher loadOf(TeacherId teacherId);

    //@Query("")
    //List<ClazzTeaching> findTeaching(PersonId personId);

    //List<ClazzManagement> findManagement(PersonId personId);
}