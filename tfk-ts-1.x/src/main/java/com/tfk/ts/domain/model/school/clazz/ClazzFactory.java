/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.clazz;

import com.tfk.commons.util.DateUtilWrapper;
import com.tfk.ts.domain.model.school.Grade;
import com.tfk.ts.domain.model.school.SchoolId;
import com.tfk.ts.domain.model.school.common.WLType;
import com.tfk.ts.domain.model.school.term.Term;

import java.util.Date;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class ClazzFactory {

    private static AdminClazz  clazz1 = new AdminClazz();

    private static TeachClazz clazz2 = new TeachClazz();

    private static MixtureClazz clazz3 = new MixtureClazz();

    public static Clazz newClazz(SchoolId schoolId, ClazzId clazzId, String name, String clazzNo,
                                 String createDate, Grade grade, String type, Term term){
        return ClazzFactory.newClazz(schoolId, clazzId, name, clazzNo, createDate, grade,type, WLType.None, term);
    }

    public static Clazz newClazz(SchoolId schoolId, ClazzId clazzId, String name, String clazzNo,
                                 Date createDate, Grade grade, String type, Term term){
        return ClazzFactory.newClazz(schoolId, clazzId, name, clazzNo, createDate, grade,type, WLType.None, term);
    }

    public static Clazz newClazz(SchoolId schoolId, ClazzId clazzId, String name, String clazzNo,
                                 String createDate, Grade grade, String type, WLType wl, Term term){
        if(clazz1.classOf(type)){
            return new AdminClazz(schoolId, clazzId, name, clazzNo, createDate, grade, wl, term);
        }

        if(clazz2.classOf(type)){
            return new TeachClazz(schoolId, clazzId, name, clazzNo, createDate, grade, wl, term);
        }

        if(clazz3.classOf(type)){
            return new MixtureClazz(schoolId, clazzId, name, clazzNo, createDate, grade, wl, term);
        }

        throw new ClazzTypeNotFoundException(type);
    }

    public static Clazz newClazz(SchoolId schoolId, ClazzId clazzId, String name, String clazzNo,
                                 Date createDate, Grade grade, String type, WLType wl, Term term){
        if(clazz1.classOf(type)){
            return new AdminClazz(schoolId, clazzId, name, clazzNo, createDate, grade, wl, term);
        }

        if(clazz2.classOf(type)){
            return new TeachClazz(schoolId, clazzId, name, clazzNo, createDate, grade, wl, term);
        }

        if(clazz3.classOf(type)){
            return new MixtureClazz(schoolId, clazzId, name, clazzNo, createDate, grade, wl, term);
        }

        throw new ClazzTypeNotFoundException(type);
    }

}