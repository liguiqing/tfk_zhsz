/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.staff;

import com.tfk.ts.domain.model.school.SchoolId;
import com.tfk.ts.domain.model.school.common.Period;

/**
 * 校长
 *
 * @author Liguiqing
 * @since V3.0
 */

public class HeadMaster extends Position {

    private String post = "校长";

    protected HeadMaster(SchoolId schoolId,String name, String identity, Period period) {
        super(schoolId,name, identity, period);
    }

    @Override
    public Position renew(Period newPerid) {
        return new HeadMaster(this.schoolId(),this.name(),this.identity(),newPerid);
    }

    @Override
    public Position rename(String newName) {
        return new HeadMaster(this.schoolId(),newName,this.identity(),this.period());
    }

    @Override
    public String positionName() {
        return this.post;
    }

}