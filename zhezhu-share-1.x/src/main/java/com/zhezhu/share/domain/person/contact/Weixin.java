package com.zhezhu.share.domain.person.contact;

import com.zhezhu.share.domain.person.Contact;

/**
 * 微信
 *
 * @author Liguiqing
 * @since V3.0
 */

public class Weixin extends Contact {

    public Weixin(String number){
        super("微信号", number);
    }

    protected Weixin() {}
}