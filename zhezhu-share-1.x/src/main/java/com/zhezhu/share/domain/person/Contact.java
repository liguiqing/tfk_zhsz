package com.zhezhu.share.domain.person;

import com.google.common.base.Objects;
import com.zhezhu.commons.domain.IdentifiedValueObject;
import com.zhezhu.share.domain.id.PersonId;

/**
 * 联系方式
 *
 * @author Liguiqing
 * @since V3.0
 */

public abstract class Contact extends IdentifiedValueObject {

    private PersonId personId;

    private String name;

    private String info;

    public Contact(String name, String info) {
        this.name = name;
        this.info = info;
    }

    protected void personId(PersonId personId){
        this.personId = personId;
    }

    public String info() {
        return info;
    }

    public void info(String info) {
        this.info = info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return  Objects.equal(name, contact.name) &&
                Objects.equal(info, contact.info);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, info);
    }

    @Override
    public String toString() {
        return this.name + ":"+ info;
    }

    protected Contact(){}
}