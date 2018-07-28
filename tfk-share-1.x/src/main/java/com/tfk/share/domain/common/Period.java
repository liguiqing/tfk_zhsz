/*
 * Copyright (c) 2016,2018, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.share.domain.common;

import com.tfk.commons.AssertionConcerns;
import com.tfk.commons.util.DateUtilWrapper;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.Date;

/**
 * 时段
 *
 * @author Liguiqing
 * @since V3.0
 */

public class Period {

    private Date starts;

    private Date ends;

    public Period(Date starts, Date ends) {
        AssertionConcerns.assertArgumentNotNull(starts,"无效的起始时间");
        AssertionConcerns.assertArgumentNotNull(ends,"无效的终止时间");
        boolean b = DateUtilWrapper.lessThan(ends, starts);
        AssertionConcerns.assertArgumentTrue(!b,"起始时间不能大于终止时间");
        this.starts = starts;
        this.ends = ends;
    }


    public Date starts() {
        return starts;
    }

    public Date ends() {
        return ends;
    }

    @Override
    public String toString() {
        return this.starts + "-" + this.ends;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Period period = (Period) o;
        return Objects.equal(starts, period.starts) &&
                Objects.equal(ends, period.ends);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(starts, ends);
    }
}