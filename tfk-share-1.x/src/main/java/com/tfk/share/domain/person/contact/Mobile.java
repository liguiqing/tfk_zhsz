package com.tfk.share.domain.person.contact;

/**
 * 移动电话
 *
 * @author Liguiqing
 * @since V3.0
 */

public class Mobile extends Contact {

    private int type = 2;

    public Mobile(String number) {
        this.name = "手机号码";
        this.info = number;
    }

}