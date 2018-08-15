/*
 * Copyright (c) 2016,2017, easytnt All Rights Reserved. 深圳市易考试乐学测评有限公司 版权所有.
 */

package com.tfk.share.domain.id.school;


import com.tfk.commons.domain.AbstractId;
import com.tfk.commons.domain.Identities;

import static com.tfk.share.domain.id.IdPrefixes.SchoolIdPrefix;

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