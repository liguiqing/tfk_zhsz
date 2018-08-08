package com.tfk.sm.application.data;

import com.tfk.share.domain.person.Contact;
import com.tfk.share.domain.person.contact.*;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class Contacts {

    private String category ;

    private String value;

    public Contacts(String category, String value) {
        this.category = category;
        this.value = value;
    }

    public Contacts( ) {}

    public Contact toContact(){
        switch (this.category){
            case "QQ":return new QQ(this.value);
            case "Weixin":return new Weixin(this.value);
            case "Email":return new Email(this.value);
            case "Mobile":return new Mobile(this.value);
            case "Phone":return new Phone(this.value);
            default:return null;
        }
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}