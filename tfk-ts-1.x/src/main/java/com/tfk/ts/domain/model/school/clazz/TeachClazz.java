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
 * 教学班,只能进行单学科教学，不管理学生
 *
 * @author Liguiqing
 * @since V3.0
 */

public class TeachClazz extends Clazz {


    public TeachClazz(SchoolId schoolId, ClazzId clazzId, String name,
                      String clazzNo, Date createDate, Grade grade, Term term) {
        super(schoolId, clazzId, name, clazzNo, createDate, grade, term);
    }

    public TeachClazz(SchoolId schoolId, ClazzId clazzId, String name,
                      String clazzNo, Date createDate, Grade grade, WLType wl, Term term) {
        super(schoolId, clazzId, name, clazzNo, createDate, grade, wl, term);
    }

    public TeachClazz(SchoolId schoolId, ClazzId clazzId, String name,
                      String clazzNo, String createDate, Grade grade, WLType wl, Term term) {
        super(schoolId, clazzId, name, clazzNo, createDate, grade, wl, term);
    }

    @Override
    public boolean canBeStudyAndTeachIn() {
        return true;
    }

    @Override
    public boolean canBeManaged() {
        return false;
    }

    @Override
    public boolean canBeStudied() {
        return true;
    }

    protected TeachClazz(){
        super();
        //Only 4 persistent
    }
}