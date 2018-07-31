/*
 * Copyright (c) 2016,2018, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.share.domain.school;

import com.tfk.commons.domain.ValueObject;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class Term extends ValueObject{

    private String name;

    private int seq = 1;

    public Term(String name,Sequence seq) {
        this.name = name;
        this.seq = seq.seq;
    }

    public static Term First(){
        return new Term("第一学期",Sequence.One);
    }

    public static Term Second(){
        Term t =  new Term("第二学期",Sequence.Two);
        return t;
    }

    public static Term Last(){
        return new Term("上学期",Sequence.One);
    }

    public static Term Next(){
        Term t =  new Term("下学期",Sequence.Two);
        return t;
    }

    public String name() {
        return name;
    }

    public int seq() {
        return seq;
    }

    //Only 4 persistence
    protected Term(){}

    public static enum Sequence{
        One(1),Two(2);
        private int seq;
        private Sequence(int seq){
            this.seq = seq;
        }
    }
}