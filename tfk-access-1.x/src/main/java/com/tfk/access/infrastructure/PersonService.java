package com.tfk.access.infrastructure;

import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.person.Gender;

/**
 * @author Liguiqing
 * @since V3.0
 */

public interface PersonService {

    default PersonId getPersonId(String weChatOpenId){
        return new PersonId();
    }

    default PersonId getPersonId(String schoolId, String clazzId, String name, Gender gender,QueryTarget target){
        return new PersonId();
    }

    enum QueryTarget{
        Teacher,Student,Parent
    }
}