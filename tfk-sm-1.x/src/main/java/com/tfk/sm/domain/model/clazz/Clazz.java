package com.tfk.sm.domain.model.clazz;

import com.google.common.base.Objects;
import com.tfk.commons.AssertionConcerns;
import com.tfk.commons.domain.Entity;
import com.tfk.commons.util.DateUtilWrapper;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.SchoolId;
import com.tfk.share.domain.school.Grade;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

/**
 * 班级
 *
 * @author Liguiqing
 * @since V1.0
 */

public abstract class Clazz extends Entity {
    private ClazzId clazzId;

    private SchoolId schoolId;

    private Date openedTime; //开班时间

    private Date closedTime; //结束时间,未结束为null,

    private List<ClazzHistory> histories;


    public Clazz(ClazzId clazzId, SchoolId schoolId) {
        this.clazzId = clazzId;
        this.schoolId = schoolId;
    }

    public Clazz(ClazzId clazzId, SchoolId schoolId, Date openedTime) {
        this.clazzId = clazzId;
        this.schoolId = schoolId;
        this.openedTime = openedTime;
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
        //TODO
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clazz clazz = (Clazz) o;
        return Objects.equal(clazzId, clazz.clazzId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(clazzId);
    }

    public ClazzId clazzId() {
        return clazzId;
    }

    public SchoolId schoolId() {
        return schoolId;
    }

    public Date openedTime() {
        return openedTime;
    }

    public Date closedTime() {
        return closedTime;
    }

    protected Clazz() {
    }
}