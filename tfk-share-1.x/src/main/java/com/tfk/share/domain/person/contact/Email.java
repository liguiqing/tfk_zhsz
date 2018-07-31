package com.tfk.share.domain.person.contact;

/**
 * 电子信箱
 *
 * @author Liguiqing
 * @since V3.0
 */

public class Email extends Contact {

    private int type = 5;

    public Email(String number) {
        this.name = "电子邮箱";
        this.info = number;
    }
}