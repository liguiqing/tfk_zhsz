/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.common;

import com.tfk.commons.AssertionConcerns;
import com.tfk.commons.util.DateUtilWrapper;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.Date;

/**
 * 时期段
 *
 * @author Liguiqing
 * @since V3.0
 */

public class Period {

    private Date starts;

    private Date ends;

    public Period(Date starts, Date ends) {
        AssertionConcerns.assertArgumentNotNull(starts,"请提供开始时间");
        if(ends != null){
            AssertionConcerns.assertArgumentTrue(DateUtilWrapper.lessThan(starts,ends),"开始时间不能大于结束时间");
        }
        this.starts = starts;
        this.ends = ends;
    }

    public static Period now(){
        return new Period(DateUtilWrapper.today(), DateUtilWrapper.today());
    }

    public boolean gratherThan(Date ends){
        return DateUtilWrapper.largeThanYYMMDD(this.ends,ends);
    }

    public boolean isBetween(Date aDate){
        return DateUtilWrapper.lseThanYYMMDD(this.ends,aDate) && DateUtilWrapper.lgeThanYYMMDD(this.starts,aDate);
    }

    public boolean contains(Period aPeriod){
        return this.isBetween(aPeriod.ends) && this.isBetween(aPeriod.starts);
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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("starts", starts)
                .add("ends", ends)
                .toString();
    }

    public String formatString(){
        return DateUtilWrapper.toString(this.starts,"yyyy-MM-dd") + "-" +
                DateUtilWrapper.toString(this.starts,"yyyy-MM-dd");
    }

    public Date starts() {
        return starts;
    }

    public Date ends() {
        return ends;
    }

    public boolean isOver() {
        if(this.ends == null )
            return false;
        return DateUtilWrapper.lgeThanYYMMDD(DateUtilWrapper.today(),this.ends);
    }

    protected Period() {
    }
}