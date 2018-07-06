/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.application.school.command;

import com.tfk.ts.domain.model.school.common.WLType;
import com.google.common.base.MoreObjects;

import java.util.Date;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class NewClazzCommand {
    private String name;

    private String clazzNo;//班号，学校自编，是班级连接性的标识

    private String createDate; //建班日期，使用时格式为YYYY-mm

    private String gradeName;

    private String gradeLevel;

    private String year;

    private String masterName;

    private String masterIdentity;

    private Date masterStarts;

    private Date masterEends;

    private String type;

    private String wlType;

    private String termId;

    public boolean hasMaster(){
        return this.masterName != null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClazzNo() {
        return clazzNo;
    }

    public void setClazzNo(String clazzNo) {
        this.clazzNo = clazzNo;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWlType() {
        return wlType == null? WLType.None.name():wlType;
    }

    public void setWlType(String wlType) {
        this.wlType = wlType;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("clazzNo", clazzNo)
                .add("createDate", createDate)
                .add("gradeName", gradeName)
                .add("gradeLevel", gradeLevel)
                .add("masterMame", masterName)
                .add("masterIdentity", masterIdentity)
                .add("masterStarts", masterStarts)
                .add("masterEends", masterEends)
                .add("type", type)
                .toString();
    }
}