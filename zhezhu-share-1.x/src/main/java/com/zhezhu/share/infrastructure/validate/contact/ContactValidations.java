package com.zhezhu.share.infrastructure.validate.contact;

import com.google.common.collect.Lists;
import com.zhezhu.share.domain.person.Contact;

import java.util.List;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class ContactValidations {

    private List<ContactValidator> validators;

    public ContactValidations (){}

    public ContactValidations(List<ContactValidator> validtors) {
        this.validators = validtors;
    }

    public ContactValidations addValidator(ContactValidator validator){
        if(this.validators == null)
            this.validators = Lists.newArrayList();
        this.validators.add(validator);
        return this;
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