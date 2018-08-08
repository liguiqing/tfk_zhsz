package com.tfk.sm.application.clazz;

import com.google.common.base.MoreObjects;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.SchoolId;
import com.tfk.share.domain.school.Grade;
import com.tfk.share.domain.school.StudyYear;
import com.tfk.sm.domain.model.clazz.Clazz;
import com.tfk.sm.domain.model.clazz.ClazzHistory;
import com.tfk.sm.domain.model.clazz.UnitedClazz;

import java.util.Date;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class NewClazzCommand {
    private String schoolId;

    private Date openedTime; //开班时间

    private String clazzName;

    private int yearStarts;

    private int yearEnds;

    private int gradeLevel;

    public NewClazzCommand(){}

    public NewClazzCommand(String schoolId, Date openedTime, String clazzName, int yearStarts, int yearEnds, int gradeLevel) {
        this.schoolId = schoolId;
        this.openedTime = openedTime;
        this.clazzName = clazzName;
        this.yearStarts = yearStarts;
        this.yearEnds = yearEnds;
        this.gradeLevel = gradeLevel;
    }


    public Clazz toClazz() {
        //TODO other Clazz
        return toUnitedClazz();
    }

    private  UnitedClazz toUnitedClazz(){
        ClazzId clazzId = new ClazzId();
        UnitedClazz clazz = new UnitedClazz(clazzId,new SchoolId(this.schoolId),this.openedTime);
        ClazzHistory history = new ClazzHistory(clazzId,grade(),studyYear(),this.clazzName);
        clazz.addHistory(history);
        return clazz;
    }

    public Grade grade(){
        return Grade.newWithLevel(this.gradeLevel);
    }

    public StudyYear studyYear(){
        return new StudyYear(yearStarts, yearEnds);
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public Date getOpenedTime() {
        return openedTime;
    }

    public void setOpenedTime(Date openedTime) {
        this.openedTime = openedTime;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public int getYearStarts() {
        return yearStarts;
    }

    public void setYearStarts(int yearStarts) {
        this.yearStarts = yearStarts;
    }

    public int getYearEnds() {
        return yearEnds;
    }

    public void setYearEnds(int yearEnds) {
        this.yearEnds = yearEnds;
    }

    public int getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(int gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("schoolId", schoolId)
                .add("openedTime", openedTime)
                .add("clazzName", clazzName)
                .add("yearStarts", yearStarts)
                .add("yearEnds", yearEnds)
                .add("gradeLevel", gradeLevel)
                .toString();
    }

}