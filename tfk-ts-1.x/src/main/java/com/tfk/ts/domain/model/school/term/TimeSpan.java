/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.term;

import com.tfk.commons.AssertionConcerns;
import com.tfk.commons.domain.ValueObject;
import com.tfk.commons.util.DateUtilWrapper;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.Date;

/**
 * 学期跨越的时间段
 *
 * @author Liguiqing
 * @since V3.0
 */

public class TimeSpan extends ValueObject{

    private Date starts; //开学日期

    private Date ends;   //放假日期

    public TimeSpan(Date starts, Date ends){
        AssertionConcerns.assertArgumentNotNull(starts,"请安排学期开学时间");
        AssertionConcerns.assertArgumentNotNull(ends,"请安排学期结束时间");
        int c = ends.compareTo(starts);
        AssertionConcerns.assertArgumentTrue(c>0,"学期结束时间不能在开学时间之前");
        this.starts = starts;
        this.ends = ends;
    }

    public boolean betweensOf(Date date){
        if(DateUtilWrapper.equalsYYMMDD(date,this.starts) || DateUtilWrapper.equalsYYMMDD(date,this.ends)){
            return true;
        }
        return DateUtilWrapper.lessThanYYMMDD(date,this.ends) && DateUtilWrapper.largeThanYYMMDD(date,this.starts);
    }

    /**
     * 计算当前周，开学日期所在周为第1周
     * @return
     */
    public int weekOfNow(){
        //TODO
        return 1;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.starts,this.ends);
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof TimeSpan) {
            TimeSpan that = (TimeSpan) o;
            return Objects.equal(this.starts, that.starts) && Objects.equal(this.ends,that.ends) ;
        }
        return false;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("starsDate",this.starts)
                .add("ends",this.ends).toString();
    }

    public Date starts() {
        return starts;
    }

    public Date ends() {
        return ends;
    }

    protected TimeSpan(){

    }
}