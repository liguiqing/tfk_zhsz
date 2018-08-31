package com.zhezhu.share.domain.person.contact;

import com.zhezhu.share.domain.person.Contact;

/**
 * QQ联系方式
 *
 * @author Liguiqing
 * @since V3.0
 */

public class QQ extends Contact {

    public QQ(String number){
        super("QQ号",number+"");
    }

    protected QQ() {}
}