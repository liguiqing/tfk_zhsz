/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.application.school.data;

/**
 * 学校年级数据
 *
 * @author Liguiqing
 * @since V3.0
 */

public class GradeData {
    private String name;

    private int level;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public GradeData() {
    }

    public GradeData(String name, int level) {
        this.name = name;
        this.level = level;
    }
}