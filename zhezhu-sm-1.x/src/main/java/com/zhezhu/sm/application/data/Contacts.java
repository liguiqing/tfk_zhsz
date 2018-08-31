package com.zhezhu.sm.application.data;

import com.zhezhu.share.domain.person.Contact;
import com.zhezhu.share.domain.person.contact.*;
import lombok.*;

/**
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Contacts {

    private String category ;

    private String value;

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
}