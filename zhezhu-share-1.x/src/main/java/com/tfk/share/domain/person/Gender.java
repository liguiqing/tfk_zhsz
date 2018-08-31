package com.tfk.share.domain.person;

/**
 * 性别
 *
 * @author Liguiqing
 * @since V3.0
 */

public enum  Gender {
    Male(1),Female(2),Unkow(0);

    private int value;

    private Gender(int value){
        this.value = value;
    }
}