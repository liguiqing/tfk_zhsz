/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.clazz;

import com.tfk.ts.domain.model.school.Grade;
import com.tfk.ts.domain.model.school.SchoolId;
import com.tfk.ts.domain.model.school.common.Period;
import com.tfk.ts.domain.model.school.common.WLType;
import com.tfk.ts.domain.model.school.term.Term;

import java.util.Date;

/**
 * 行政班，只能管理学生，不参与教学
 *
 * @author Liguiqing
 * @since V3.0
 */

public class AdminClazz extends Clazz {

    public AdminClazz(SchoolId schoolId, ClazzId clazzId, String name,
                      String clazzNo, Date createDate, Grade grade, Term term) {
        super(schoolId, clazzId, name, clazzNo, createDate, grade, term);
    }

    public AdminClazz(SchoolId schoolId, ClazzId clazzId, String name,
                      String clazzNo, Date createDate, Grade grade, WLType wl, Term term) {
        super(schoolId, clazzId, name, clazzNo, createDate, grade, wl, term);
    }

    public AdminClazz(SchoolId schoolId, ClazzId clazzId, String name,
                      String clazzNo, String createDate, Grade grade, WLType wl, Term term) {
        super(schoolId, clazzId, name, clazzNo, createDate, grade, wl, term);
    }

    public MixtureClazz convertToMixture(Term term){
        Grade grade = this.periodGrade(Period.now());
        WLType wl = this.termWL(term);
        return new MixtureClazz(this.schoolId(), this.clazzId(), this.name(), this.clazzNo(), this.createDate(), grade,wl, term);
    }

    @Override
    public boolean canBeStudyAndTeachIn() {
        return false;
    }

    @Override
    public boolean canBeManaged() {
        return true;
    }

    @Override
    public boolean canBeStudied() {
        return false;
    }

    protected AdminClazz(){
        //Only 4 persistance
    }
}