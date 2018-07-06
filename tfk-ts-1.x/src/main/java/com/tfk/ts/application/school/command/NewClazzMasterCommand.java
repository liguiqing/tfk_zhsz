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

public class NewClazzMasterCommand {
    private String staffId;

    private String clazzId;

    private Date starts;

    private Date ends;

    private String name; //教职工姓名

    private String identity; //唯一身份标识，关联到staff.Staff时就是Staff.staffId

    public NewClazzMasterCommand(String staffId, String clazzId, Date starts, Date ends, String name, String identity) {
        this.staffId = staffId;
        this.clazzId = clazzId;
        this.starts = starts;
        this.ends = ends;
        this.name = name;
        this.identity = identity;
    }

    public NewClazzMasterCommand() {
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getClazzId() {
        return clazzId;
    }

    public void setClazzId(String clazzId) {
        this.clazzId = clazzId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("staffId", staffId)
                .add("clazzId", clazzId)
                .add("starts", starts)
                .add("ends", ends)
                .add("name", name)
                .add("identity", identity)
                .toString();
    }
}