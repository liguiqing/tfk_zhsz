package com.tfk.sm.application.teacher;

import com.tfk.sm.application.data.CourseData;

import java.util.Date;
/**
 * @author Liguiqing
 * @since V3.0
 */

public class TeacherArrangingCommand {
    private String teacherId;

    private Date dateStarts;

    private Date dateEnds;

    private CourseData[] courses;

    private String[] managementClazzIds;

    private String[] teachingClazzIds;

    public TeacherArrangingCommand(){}

    public TeacherArrangingCommand(String teacherId, CourseData[] courses,
                                   String[] managementClazzIds, String[] teachingClazzIds) {
        this.teacherId = teacherId;
        this.courses = courses;
        this.managementClazzIds = managementClazzIds;
        this.teachingClazzIds = teachingClazzIds;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public CourseData[] getCourses() {
        return courses;
    }

    public void setCourses(CourseData[] courses) {
        this.courses = courses;
    }

    public String[] getManagementClazzIds() {
        return managementClazzIds;
    }

    public void setManagementClazzIds(String[] managementClazzIds) {
        this.managementClazzIds = managementClazzIds;
    }

    public String[] getTeachingClazzIds() {
        return teachingClazzIds;
    }

    public void setTeachingClazzIds(String[] teachingClazzIds) {
        this.teachingClazzIds = teachingClazzIds;
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
}