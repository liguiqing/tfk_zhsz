/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school;


import com.tfk.commons.domain.AbstractId;

/**
 * 学校唯一标识
 *
 * @author Liguiqing
 * @since V3.0
 */

public class SchoolId extends AbstractId {
    public SchoolId(String id){
        super(id);
    }

    protected  SchoolId(){
        super();
    }
}