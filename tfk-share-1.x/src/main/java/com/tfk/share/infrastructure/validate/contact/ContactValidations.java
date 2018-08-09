package com.tfk.share.infrastructure.validate.contact;

import com.tfk.share.domain.person.Contact;
import com.tfk.share.domain.person.Person;

import java.util.List;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class ContactValidations {

    private List<ContactValidator> validators;

    public ContactValidations(List<ContactValidator> validtors) {
        this.validators = validtors;
    }

    public boolean validate(Contact contact){
        for(ContactValidator validator:this.validators){
            if(validator.supports(contact)){
                return validator.validate(contact);
            }
        }
        return false;
    }

}