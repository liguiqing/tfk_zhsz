package com.tfk.share.domain.person.contact;

/**
 * 联系电话
 *
 * @author Liguiqing
 * @since V3.0
 */

public class Phone extends Contact {

    private int Type = 1;

    public Phone(String number) {
        this.name = "电话号码";
        this.info = number;
    }

    public Phone(int prefix, int number) {
        this.name = "电话号码";
        this.info = prefix+"-"+number;
    }

}