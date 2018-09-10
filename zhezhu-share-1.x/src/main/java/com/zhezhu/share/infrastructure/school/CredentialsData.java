package com.zhezhu.share.infrastructure.school;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Liguiqing
 * @since V3.0
 */
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CredentialsData {
    private String name;

    private String value;

    public boolean sameAs(String name,String value){
        return this.name.equals(name) && this.value.equals(value);
    }

}