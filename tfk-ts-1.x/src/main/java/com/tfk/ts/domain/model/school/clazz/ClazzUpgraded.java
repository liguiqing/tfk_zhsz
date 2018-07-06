/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.clazz;

import com.tfk.commons.domain.AbstractDomainEvent;
import com.tfk.ts.domain.model.school.Grade;
import com.tfk.ts.domain.model.school.SchoolId;

import java.util.Date;

/**
 * 班级升一个年级
 *
 * @author Liguiqing
 * @since V3.0
 */

public class ClazzUpgraded extends AbstractDomainEvent {
    private SchoolId schoolId;

    private ClazzId clazzId;

    private Grade old;

    private Grade now;

    public ClazzUpgraded(SchoolId schoolId, ClazzId clazzId, Grade old, Grade now) {
        super();
        this.schoolId = schoolId;
        this.clazzId = clazzId;
        this.old = old;
        this.now = now;
    }

    public ClazzUpgraded(int eventVersion, Date occurredOn, SchoolId schoolId, ClazzId clazzId, Grade old, Grade now) {
        super(eventVersion, occurredOn);
        this.schoolId = schoolId;
        this.clazzId = clazzId;
        this.old = old;
        this.now = now;
    }

    public SchoolId schoolId() {
        return schoolId;
    }

    public ClazzId clazzId() {
        return clazzId;
    }

    public Grade old() {
        return old;
    }

    public Grade now() {
        return now;
    }
}