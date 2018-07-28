package com.tfk.sm.domain.model.school;

import com.tfk.commons.domain.Entity;
import com.tfk.commons.util.DateUtilWrapper;
import com.tfk.share.domain.common.Period;
import com.tfk.share.domain.id.school.SchoolId;
import com.tfk.share.domain.school.Term;

/**
 * 学校
 *
 * @author Liguiqing
 * @since V1.0
 */

public class School extends Entity {
    private SchoolId schoolId;

    private String name;

    public Period  getThisTermStartsAndEnds(){
        //TODO
        return new Period(DateUtilWrapper.now(), DateUtilWrapper.tomorrow());
    }

}