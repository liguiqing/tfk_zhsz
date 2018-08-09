package com.tfk.share.domain.person;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.tfk.commons.AssertionConcerns;
import com.tfk.commons.domain.IdentifiedDomainObject;
import com.tfk.share.domain.id.PersonId;
import com.tfk.share.infrastructure.validate.contact.ContactValidations;

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

    public Person(PersonId personId, String name) {
        this(personId, name, null, Gender.Unkow);
    }

    public Person(PersonId personId, String name, Date birthday, Gender gender) {
        this.personId = personId;
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
    }

    public PersonId personId() {
        return personId;
    }

    public void addContact(ContactValidations validations, Contact contact){
        AssertionConcerns.assertArgumentTrue(validations.validate(contact),"cm-01-001");

        if(this.contacts == null)
            this.contacts = Sets.newHashSet();
        contact.personId(this.personId);
        this.contacts.add(contact);
    }

    public Set<Contact> contacts(){
        return ImmutableSet.copyOf(this.contacts);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equal(personId, person.personId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(personId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("personId", personId)
                .add("name", name)
                .add("gender", gender)
                .toString();
    }

    protected Person() {
    }
}