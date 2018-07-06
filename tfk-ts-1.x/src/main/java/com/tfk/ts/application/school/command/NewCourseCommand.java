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

public class NewCourseCommand {
    private String subjectId;

    private String name;

    private String gradeName;

    private String gradeLevel;

    private String masterName;

    private String masterIdentity;

    private Date masterStarts;

    private Date masterEends;

    public boolean hasMaster(){
        return this.masterName != null;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public String getMasterIdentity() {
        return masterIdentity;
    }

    public void setMasterIdentity(String masterIdentity) {
        this.masterIdentity = masterIdentity;
    }

    public Date getMasterStarts() {
        return masterStarts;
    }

    public void setMasterStarts(Date masterStarts) {
        this.masterStarts = masterStarts;
    }

    public Date getMasterEends() {
        return masterEends;
    }

    public void setMasterEends(Date masterEends) {
        this.masterEends = masterEends;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("subjectId", subjectId)
                .add("name", name)
                .add("gradeName", gradeName)
                .add("gradeLevel", gradeLevel)
                .add("masterName", masterName)
                .add("masterIdentity", masterIdentity)
                .add("masterStarts", masterStarts)
                .add("masterEends", masterEends)
                .toString();
    }
}