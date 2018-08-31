package com.tfk.sm.domain.model.clazz;

import com.google.common.collect.Sets;
import com.tfk.commons.AssertionConcerns;
import com.tfk.commons.domain.Entity;
import com.tfk.commons.util.CollectionsUtilWrapper;
import com.tfk.commons.util.DateUtilWrapper;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.SchoolId;
import com.tfk.share.domain.school.Grade;
import com.tfk.share.domain.school.StudyYear;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
import java.util.Set;

/**
 * 班级
 *
 * @author Liguiqing
 * @since V1.0
 */
@Getter
@EqualsAndHashCode(of={"clazzId"},callSuper = false)
@ToString(exclude = {"histories"})
public abstract class Clazz extends Entity {
    private ClazzId clazzId;

    private SchoolId schoolId;

    private Date openedTime; //开班时间

    private Date closedTime; //结束时间,未结束为null,

    private Set<ClazzHistory> histories;

    public Clazz(ClazzId clazzId, SchoolId schoolId) {
        this.clazzId = clazzId;
        this.schoolId = schoolId;
    }

    public Clazz(ClazzId clazzId, SchoolId schoolId, Date openedTime) {
        this.clazzId = clazzId;
        this.schoolId = schoolId;
        this.openedTime = openedTime == null?DateUtilWrapper.now():openedTime;
    }

    public void addHistory(ClazzHistory aHistory){
        if(this.histories == null){
            this.histories = Sets.newHashSet();
        }
        aHistory.toSchool(this.schoolId);
        this.histories.add(aHistory);
    }

    public String getGradeFullName(Grade grade){
        if(CollectionsUtilWrapper.isNullOrEmpty(this.getHistories()))
            return "";
        for(ClazzHistory history:histories){
            if(history.sameGadeOf(grade)){
                return history.fullName();
            }
        }

        return "";
    }

    public void open(){
        this.open(DateUtilWrapper.now());
    }

    public void open(Date openedTime){
        if(this.isClosed()){
            boolean b = DateUtilWrapper.largeThanYYMMDD(this.closedTime,openedTime);
            AssertionConcerns.assertArgumentTrue(b,"结束时间不能小于开班时间");
        }
        this.openedTime = openedTime;
    }

    public void close(){
        this.close(DateUtilWrapper.now());
    }

    public void close(Date closedTime){
        boolean b = DateUtilWrapper.largeThanYYMMDD(closedTime,this.openedTime);
        AssertionConcerns.assertArgumentTrue(b,"结束时间不能小于开班时间");
        this.closedTime = closedTime;
    }

    public boolean isClosed(){
        return this.closedTime != null;
    }

    public abstract boolean canBeStudyAt();

    public abstract boolean canBeManagedAt();

    public Grade currentGrade(){
        StudyYear year = StudyYear.now();
        for(ClazzHistory history:this.histories){
            if(history.sameYearOf(year)){
                return history.getGrade();
            }
        }
        return null;
    }

    protected Clazz() {
    }

    protected boolean isTypeOf(Class<? extends Clazz> clazz){
        return this.getClass().equals(clazz.getClass());
    }
}