package com.tfk.share.domain.person;

import com.google.common.collect.Sets;
import com.tfk.commons.domain.IdentifiedDomainObject;
import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.person.contact.Contact;

import java.util.Date;
import java.util.Set;

/**
 * @author Liguiqing
 * @since V3.0
 */

public abstract class  Person extends IdentifiedDomainObject {
    private PersonId personId;

    private String name;

    private Date birthday;

    private Gender gender;

    private Set<Contact> contacts;

    public PersonId personId() {
        return personId;
    }

    public void addContact(Contact contact){
        if(this.contacts == null)
            this.contacts = Sets.newHashSet();
        this.contacts.add(contact);
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

    public void name(String name) {
        this.name = name;
    }

    public void birthday(Date birthday) {
        this.birthday = birthday;
    }

    public void gender(Gender gender) {
        this.gender = gender;
    }
}