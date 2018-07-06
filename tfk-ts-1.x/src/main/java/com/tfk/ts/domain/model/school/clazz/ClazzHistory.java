/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.clazz;

import com.tfk.commons.domain.IdentifiedValueObject;
import com.tfk.ts.domain.model.school.Grade;
import com.tfk.ts.domain.model.school.common.Period;
import com.tfk.ts.domain.model.school.common.WLType;
import com.tfk.ts.domain.model.school.term.Term;
import com.tfk.ts.domain.model.school.term.TermId;
import com.tfk.ts.domain.model.school.term.TermOrder;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * 班级历史
 *
 * @author Liguiqing
 * @since V3.0
 */

public class ClazzHistory extends IdentifiedValueObject implements Comparable<ClazzHistory>{

    private ClazzId clazzId;

    private TermId termId;

    private TermOrder termOrder;

    private Period period;

    private Grade grade; //年级

    private WLType wl;

    protected ClazzHistory(ClazzId clazzId, Term term, Grade grade, WLType wl) {
        this.clazzId = clazzId;
        this.termId = term.termId();
        this.period = term.converToPeriod();
        this.grade = grade;
        this.wl = wl;
        this.termOrder = term.order();
    }

    public boolean isInPeriod(Period aPeriod){
        return this.period.contains(aPeriod);
    }

    public ClazzId clazzId() {
        return clazzId;
    }

    public TermId termId() {
        return termId;
    }

    public Grade grade() {
        return grade;
    }

    public WLType wl() {
        return wl;
    }

    public TermOrder termOrder() {
        return termOrder;
    }

    public Period period() {
        return period;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClazzHistory that = (ClazzHistory) o;
        return Objects.equal(clazzId, that.clazzId) &&
                Objects.equal(termId, that.termId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(clazzId, termId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("clazzId", clazzId)
                .add("termId", termId)
                .add("grade", grade)
                .toString();
    }

    protected ClazzHistory(){
        //Only 4 persistent
    }


    @Override
    public int compareTo(ClazzHistory other) {
        return  (this.grade.seq().getSeq() * 100 + this.termOrder.getOrder())
                  - (other.grade.seq().getSeq() * 100 +other.termOrder.getOrder());
    }

}