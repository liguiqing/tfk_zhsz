/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.clazz;

import com.tfk.commons.domain.AbstractId;

/**
 * 班级唯一标识
 *
 * @author Liguiqing
 * @since V3.0
 */

public class ClazzId extends AbstractId{

    public ClazzId(){super();}

    public ClazzId(String id){
        super(id);
    }
}