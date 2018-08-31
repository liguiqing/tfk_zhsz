package com.tfk.share.infrastructure.validate.contact;

import com.tfk.share.domain.person.Contact;
import com.tfk.share.domain.person.contact.Phone;

/**
 * 电话号码验证器
 *
 * @author Liguiqing
 * @since V3.0
 */

public class PhoneValidator implements ContactValidator {


    @Override
    public boolean supports(Contact contact) {
        return Phone.class.equals(contact.getClass());
    }

    @Override
    public boolean validate(Contact contact) {
        //TODO
        return true;
    }
}