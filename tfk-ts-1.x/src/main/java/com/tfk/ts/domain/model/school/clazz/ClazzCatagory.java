/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.clazz;

import com.tfk.commons.AssertionConcerns;
import com.tfk.commons.domain.IdentifiedValueObject;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * 班级分类，重点班，实验班，民族班等
 *
 * @author Liguiqing
 * @since V3.0
 */

public class ClazzCatagory extends IdentifiedValueObject {
    private String code;

    private String name;

    private ClazzId clazzId;

    protected ClazzCatagory(ClazzId clazzId,String code, String name) {
        AssertionConcerns.assertArgumentNotNull(code,"班级分类代码不能为空");
        AssertionConcerns.assertArgumentNotNull(name,"班级分类名称不能为空");
        this.code = code;
        this.name = name;
        this.clazzId = clazzId;
    }

    public boolean sameCodeOf(String code){
        return this.code().equals(code);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.code);
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof ClazzCatagory) {
            ClazzCatagory that = (ClazzCatagory) o;
            return Objects.equal(this.code, that.code);
        }
        return false;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("name", this.name)
                .add("code", this.code).toString();
    }

    public String code() {
        return code;
    }

    public String name() {
        return name;
    }

    public ClazzId clazzId() {
        return clazzId;
    }

    protected ClazzCatagory() {
    }
}