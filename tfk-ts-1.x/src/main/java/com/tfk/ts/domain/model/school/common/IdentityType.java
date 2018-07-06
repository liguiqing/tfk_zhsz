/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.common;

/**
 * 标识类型
 *
 * @author Liguiqing
 * @since V3.0
 */

public enum IdentityType {
    IDCard("身份证", 1), StudyNumber("学籍号", 2), SchoolNumber("学号", 3),EduID("教育云标识",4),
    QQ("QQ号",5),Weixin("微信",6), HKMATWIDCard("港澳台证件号", 7),
    JobNo("工号", 8),ExamNo("考号", 9),Other("其他", 99),Systems("系统生成",100);

    private final String name;

    private final int value;

    IdentityType(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String idname() {
        return this.name;
    }

    public int value() {
        return this.value;
    }

    public String toString() {
        return this.name + "-" + this.value;
    }
}