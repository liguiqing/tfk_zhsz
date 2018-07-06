/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.application.school.command;

import java.util.Date;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class ChangeHeaderMasterCommand {

    private String identity;

    private Date newStarts;

    private Date newEnds;

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public Date getNewStarts() {
        return newStarts;
    }

    public void setNewStarts(Date newStarts) {
        this.newStarts = newStarts;
    }

    public Date getNewEnds() {
        return newEnds;
    }

    public void setNewEnds(Date newEnds) {
        this.newEnds = newEnds;
    }
}