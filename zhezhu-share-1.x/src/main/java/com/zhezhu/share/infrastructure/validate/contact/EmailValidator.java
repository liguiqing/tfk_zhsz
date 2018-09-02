package com.zhezhu.share.infrastructure.validate.contact;

import com.zhezhu.share.domain.person.Contact;
import com.zhezhu.share.domain.person.contact.Email;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Email格式验证器
 *
 * @author Liguiqing
 * @since V3.0
 */

public class EmailValidator implements ContactValidator {

    private Pattern emailPattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

    @Override
    public boolean supports(Contact contact) {
        return contact.getClass().equals(Email.class);
    }

    @Override
    public boolean validate(Contact contact) {

        Matcher matcher = emailPattern.matcher(contact.info());
        if(matcher.find()){
            return true;
        }
        return false;
    }
}