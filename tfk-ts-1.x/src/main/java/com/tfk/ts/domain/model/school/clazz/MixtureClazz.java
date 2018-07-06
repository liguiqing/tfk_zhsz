/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.clazz;

import com.tfk.ts.domain.model.school.Grade;
import com.tfk.ts.domain.model.school.SchoolId;
import com.tfk.ts.domain.model.school.common.WLType;
import com.tfk.ts.domain.model.school.term.Term;

import java.util.Date;

/**
 *
 * 混合班级，教学与管理都在一个班级
 *
 * @author Liguiqing
 * @since V3.0
 */

public class MixtureClazz extends Clazz {
    public MixtureClazz(SchoolId schoolId, ClazzId clazzId, String name,
                        String clazzNo, Date createDate, Grade grade, Term term) {
        super(schoolId, clazzId, name, clazzNo, createDate, grade, term);
    }

    public MixtureClazz(SchoolId schoolId, ClazzId clazzId, String name,
                        String clazzNo, Date createDate, Grade grade, WLType wl, Term term) {
        super(schoolId, clazzId, name, clazzNo, createDate, grade, wl, term);
    }

    public MixtureClazz(SchoolId schoolId, ClazzId clazzId, String name,
                        String clazzNo, String createDate, Grade grade, WLType wl, Term term) {
        super(schoolId, clazzId, name, clazzNo, createDate, grade, wl, term);
    }

    protected MixtureClazz() {
        //Only 4 persistent
    }

    @Override
    public boolean canBeStudyAndTeachIn() {
        return true;
    }

    @Override
    public boolean canBeManaged() {
        return true;
    }

    @Override
    public boolean canBeStudied() {
        return true;
    }


}