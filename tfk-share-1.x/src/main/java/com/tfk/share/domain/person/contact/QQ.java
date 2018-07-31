package com.tfk.share.domain.person.contact;

/**
 * QQ联系方式
 *
 * @author Liguiqing
 * @since V3.0
 */

public class QQ extends Contact {

    private int type = 3;

    public QQ(int number){
        this.name = "QQ号";
        this.info = number+"";
    }

}