package com.zhezhu.share.domain.person.contact;

import com.zhezhu.share.domain.person.Contact;

/**
 * 移动电话
 *
 * @author Liguiqing
 * @since V3.0
 */

public class Mobile extends Contact {

    public Mobile(String number) {
        super("手机号码",number);
    }

    protected Mobile(){}
}