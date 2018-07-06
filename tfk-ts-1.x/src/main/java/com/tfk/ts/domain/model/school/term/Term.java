/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.term;

import com.tfk.commons.domain.Entity;
import com.tfk.ts.domain.model.school.SchoolId;
import com.tfk.ts.domain.model.school.StudyYear;
import com.tfk.ts.domain.model.school.common.Period;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * 学期
 *
 * @author Liguiqing
 * @since V3.0
 */

public class Term extends Entity {

    private TermId termId;

    private String name;

    private StudyYear termYear;

    private TermOrder order;

    private TimeSpan timeSpan;

    private SchoolId schoolId;

    public Term(TermId termId,String name, StudyYear termYear, TermOrder order, TimeSpan timeSpan, SchoolId schoolId) {
        this.termId = termId;
        this.name = name;
        this.termYear = termYear;
        this.order = order;
        this.timeSpan = timeSpan;
        this.schoolId = schoolId;
    }

    public Period converToPeriod(){
        return new Period(this.timeSpan().starts(), this.timeSpan().ends());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Term term = (Term) o;
        return Objects.equal(termYear, term.termYear) &&
                order == term.order &&
                Objects.equal(schoolId, term.schoolId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(termYear, order, schoolId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("termYear", termYear)
                .add("order", order)
                .add("timeSpan", timeSpan)
                .add("schoolId", schoolId)
                .toString();
    }

    public TermId termId() {
        return termId;
    }

    public String name() {
        return name;
    }

    public StudyYear termYear() {
        return termYear;
    }

    public TermOrder order() {
        return order;
    }

    public TimeSpan timeSpan() {
        return timeSpan;
    }

    public SchoolId schoolId() {
        return schoolId;
    }

    protected Term() {
    }
}