package com.tfk.share.domain.person.contact;

import com.tfk.share.domain.person.Contact;

/**
 * 电子信箱
 *
 * @author Liguiqing
 * @since V3.0
 */

public class Email extends Contact {

    public Email(String number) {
        super("电子邮箱",number);
    }

    protected Email(){}
}