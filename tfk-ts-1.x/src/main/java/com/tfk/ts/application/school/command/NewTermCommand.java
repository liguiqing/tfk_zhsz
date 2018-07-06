/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.application.school.command;

import com.google.common.base.MoreObjects;

import java.util.Date;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class NewTermCommand {

    private String termName;

    private String year;

    private String termOrder;

    private Date starts;

    private Date ends;

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTermOrder() {
        return termOrder;
    }

    public void setTermOrder(String termOrder) {
        this.termOrder = termOrder;
    }

    public Date getStarts() {
        return starts;
    }

    public void setStarts(Date starts) {
        this.starts = starts;
    }

    public Date getEnds() {
        return ends;
    }

    public void setEnds(Date ends) {
        this.ends = ends;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("termName", termName)
                .add("year", year)
                .add("termOrder", termOrder)
                .add("starts", starts)
                .add("ends", ends)
                .toString();
    }
}