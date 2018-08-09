package com.tfk.share.domain.school;

import com.google.common.base.Objects;
import com.tfk.commons.domain.ValueObject;
import com.tfk.share.domain.id.SubjectId;

/**
 * 课程
 *
 * @author Liguiqing
 * @since V3.0
 */

public class Course extends ValueObject {
    private String name;

    private String aliais;

    private SubjectId subjectId;

    public Course(String name, SubjectId subjectId) {
        this.name = name;
        this.subjectId = subjectId;
    }

    public Course(String name,String aliais, SubjectId subjectId) {
        this(name,subjectId);
        this.aliais = aliais;
    }

    public String name() {
        return name;
    }

    public String aliais(){
        if(this.aliais != null)
            return this.aliais;
        return this.name();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equal(subjectId, course.subjectId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode( subjectId);
    }

    protected Course(){}
}