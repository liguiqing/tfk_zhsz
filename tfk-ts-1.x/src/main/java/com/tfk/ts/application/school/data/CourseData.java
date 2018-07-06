/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.application.school.data;

/**
 * 课程数据对象
 *
 * @author Liguiqing
 * @since V3.0
 */

public class CourseData {

    private String name;

    private String subjectId;

    private String gradeName;

    public CourseData() {
    }

    public CourseData(String name, String subjectId, String gradeName) {
        this.name = name;
        this.subjectId = subjectId;
        this.gradeName = gradeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }
}