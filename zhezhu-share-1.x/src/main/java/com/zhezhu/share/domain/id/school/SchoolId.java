/*
 * Copyright (c) 2016,2017, easytnt All Rights Reserved. Liguiqing 版权所有.
 */

package com.zhezhu.share.domain.id.school;


import com.zhezhu.commons.domain.AbstractId;
import com.zhezhu.commons.domain.Identities;

import static com.zhezhu.share.domain.id.IdPrefixes.SchoolIdPrefix;

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

    public  SchoolId(){
        super(Identities.genIdNone(SchoolIdPrefix));
    }

}