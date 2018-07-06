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

public class NewHeaderMasterCommand {

    private String identity;

    private Date starts;

    private Date ends;

    public NewHeaderMasterCommand(String identity, Date starts, Date ends) {
        this.identity = identity;
        this.starts = starts;
        this.ends = ends;
    }

    public NewHeaderMasterCommand() {
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
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
                .add("identity", identity)
                .add("starts", starts)
                .add("ends", ends)
                .toString();
    }
}