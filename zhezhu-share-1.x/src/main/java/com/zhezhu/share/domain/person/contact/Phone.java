package com.zhezhu.share.domain.person.contact;

import com.zhezhu.share.domain.person.Contact;

/**
 * 联系电话
 *
 * @author Liguiqing
 * @since V3.0
 */

public class Phone extends Contact {

    public Phone(String number) {
        super("电话号码",number);
    }

    public Phone(int prefix, int number) {
        super("电话号码",prefix+"-"+number);
    }

    protected Phone(){}
}