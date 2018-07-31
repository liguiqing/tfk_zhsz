package com.tfk.share.domain.person.contact;

/**
 * 微信
 *
 * @author Liguiqing
 * @since V3.0
 */

public class Weixin extends Contact {

    private int type = 4;

    public Weixin(String number){
        this.name = "微信号";
        this.info = number;
    }

}