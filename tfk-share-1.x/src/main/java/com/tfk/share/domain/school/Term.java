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

    public Term(String name) {
        this.name = name;
    }

    public static Term First(){
        return new Term("第一学期");
    }

    public static Term Second(){
        Term t =  new Term("第二学期");
        t.seq = 2;
        return t;
    }

    public static Term Last(){
        return new Term("上学期");
    }

    public static Term Next(){
        Term t =  new Term("下学期");
        t.seq = 2;
        return t;
    }

    //Only 4 persistence
    protected Term(){}
}