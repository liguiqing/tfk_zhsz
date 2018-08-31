package com.tfk.share.domain.school;

import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.id.school.SchoolId;
import com.tfk.share.domain.person.Gender;
import com.tfk.share.domain.person.Person;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

/**
 * 师生
 * @author Liguiqing
 * @since V3.0
 */
@Getter
@ToString
@EqualsAndHashCode(of = {"schoolId"},callSuper = false)
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

    protected Teadent(){}

}