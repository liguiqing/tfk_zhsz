package com.tfk.sm.application.teacher;

import com.tfk.share.domain.person.Gender;
import com.tfk.sm.application.data.Contacts;

import java.util.Date;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class NewTeacherCommand {
    private String schoolId;

    private Date joinDate;

    private Date offDate;

    private String name;

    private Date birthday;

    private Gender gender;

    private Contacts[] contacts;

    public NewTeacherCommand(){}

    public NewTeacherCommand(String schoolId, Date joinDate, Date offDate, String name,
                             Date birthday, Gender gender, Contacts[] contacts) {
        this.schoolId = schoolId;
        this.joinDate = joinDate;
        this.offDate = offDate;
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.contacts = contacts;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public Date getOffDate() {
        return offDate;
    }

    public void setOffDate(Date offDate) {
        this.offDate = offDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Contacts[] getContacts() {
        return contacts;
    }

    public void setContacts(Contacts[] contacts) {
        this.contacts = contacts;
    }
}