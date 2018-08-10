package com.tfk.sm.application.student;

import com.tfk.sm.application.data.StudyData;

import java.util.Date;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class ArrangeStudentCommand {

    private String studentId;

    private Date dateStarts;

    private Date dateEnds;

    private StudyData[] courses;

    private String managedClazzId;

    public ArrangeStudentCommand(){}

    public ArrangeStudentCommand(String studentId, Date dateStarts, Date dateEnds, StudyData[] courses,
                                 String managedClazzId) {
        this.studentId = studentId;
        this.dateStarts = dateStarts;
        this.dateEnds = dateEnds;
        this.courses = courses;
        this.managedClazzId = managedClazzId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Date getDateStarts() {
        return dateStarts;
    }

    public void setDateStarts(Date dateStarts) {
        this.dateStarts = dateStarts;
    }

    public Date getDateEnds() {
        return dateEnds;
    }

    public void setDateEnds(Date dateEnds) {
        this.dateEnds = dateEnds;
    }

    public StudyData[] getCourses() {
        return courses;
    }

    public void setCourses(StudyData[] courses) {
        this.courses = courses;
    }

    public String getManagedClazzId() {
        return managedClazzId;
    }

    public void setManagedClazzId(String managedClazzId) {
        this.managedClazzId = managedClazzId;
    }

}