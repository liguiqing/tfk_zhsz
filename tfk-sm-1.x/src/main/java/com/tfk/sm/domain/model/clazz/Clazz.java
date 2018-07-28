package com.tfk.sm.domain.model.clazz;

import com.tfk.commons.domain.Entity;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.SchoolId;
import com.tfk.share.domain.school.Grade;

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

    public abstract boolean canBeStudyAt();

    public abstract boolean canBeManagedAt();

    public Grade currentGrade(){
        //TODO
        return null;
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
}