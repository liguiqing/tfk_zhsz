package com.tfk.share.domain.school;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.id.school.SchoolId;
import com.tfk.share.domain.person.Gender;
import com.tfk.share.domain.person.Person;

import java.util.Date;

/**
 * 师生
 * @author Liguiqing
 * @since V3.0
 */

public abstract class Teadent extends Person {
    private SchoolId schoolId;

    private Date joinDate; //入职日期

    private Date offDate; //离开日期

    public Teadent(SchoolId schoolId,PersonId personId, String name) {
        super(personId, name);
        this.schoolId = schoolId;
    }

    public Teadent(SchoolId schoolId,PersonId personId, String name, Date birthday, Gender gender) {
        super(personId, name, birthday, gender);
        this.schoolId = schoolId;
    }

    public void join(Date joinDate){
        this.joinDate = joinDate;
    }

    public void off(Date offDate){
        this.offDate = offDate;
    }

    public SchoolId schoolId() {
        return schoolId;
    }

    public Date joinDate() {
        return joinDate;
    }

    public Date offDate() {
        return offDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Teadent teadent = (Teadent) o;
        return Objects.equal(schoolId, teadent.schoolId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), schoolId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("schoolId", schoolId)
                .add("joinDate", joinDate)
                .add("offDate", offDate)
                .toString();
    }

    protected Teadent(){}

}