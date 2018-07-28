package com.tfk.share.domain.person;

import com.tfk.commons.domain.Entity;
import com.tfk.share.domain.id.PersonId;

import java.util.Date;

/**
 * @author Liguiqing
 * @since V3.0
 */

public abstract class  Person extends Entity {
    private PersonId personId;

    private String name;

    private Date birthday;

    private Gender gender;

    public PersonId personId() {
        return personId;
    }

    public String name() {
        return name;
    }

    public Date birthday() {
        return birthday;
    }

    public Gender gender() {
        return gender;
    }
}