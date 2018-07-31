package com.tfk.share.domain.person.contact;

/**
 * 联系方式
 *
 * @author Liguiqing
 * @since V3.0
 */

public abstract class Contact {

    protected String name;

    protected String info;

    public String info() {
        return info;
    }

    public void info(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return this.name + ":"+ info;
    }
}