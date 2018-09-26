package com.zhezhu.share.infrastructure.person;

import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.person.Gender;

/**
 * @author Liguiqing
 * @since V3.0
 */

public interface PersonService {

    default PersonId getPersonId(String weChatOpenId){
        return new PersonId();
    }

    default PersonId getPersonId(String schoolId, String clazzId, String name, Gender gender, QueryTarget target){
        return new PersonId();
    }

    String getName(String personId, QueryTarget target);

    enum QueryTarget{
        Teacher,Student,Parent
    }
}