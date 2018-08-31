package com.tfk.share.domain.person;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.tfk.commons.AssertionConcerns;
import com.tfk.commons.domain.IdentifiedDomainObject;
import com.tfk.share.domain.id.PersonId;
import com.tfk.share.infrastructure.validate.contact.ContactValidations;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
import java.util.Set;

/**
 * @author Liguiqing
 * @since V3.0
 */

@Getter
@EqualsAndHashCode(of={"personId"},callSuper = false)
@ToString(of={"personId","name","gender"})
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

    public Set<Contact> getContacts(){
        return ImmutableSet.copyOf(this.contacts);
    }

    protected Person() {
    }
}