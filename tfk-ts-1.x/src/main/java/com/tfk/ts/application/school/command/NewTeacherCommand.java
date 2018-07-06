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

public class NewTeacherCommand {

    private String name;

    private String identity;

    private Date starts;

    private Date ends;

    private String courseName;

    private String subjectId;

    public NewTeacherCommand(String name, String identity, Date starts, Date ends, String courseName, String subjectId) {
        this.name = name;
        this.identity = identity;
        this.starts = starts;
        this.ends = ends;
        this.courseName = courseName;
        this.subjectId = subjectId;
    }

    public NewTeacherCommand() {
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

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("identity", identity)
                .add("starts", starts)
                .add("ends", ends)
                .add("courseName", courseName)
                .add("subjectId", subjectId)
                .toString();
    }
}