package com.tfk.sm.application.data;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class CourseData {

    private String name;

    private String subjectId;

    private int gradeLevel;

    public CourseData(){}

    public CourseData(String name, String subjectId) {
        this.name = name;
        this.subjectId = subjectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public int getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(int gradeLevel) {
        this.gradeLevel = gradeLevel;
    }
}