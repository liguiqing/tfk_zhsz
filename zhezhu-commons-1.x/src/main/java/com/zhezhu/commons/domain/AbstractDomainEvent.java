/*
 * Copyright (c) 2016,2017, zhezhu All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.zhezhu.commons.domain;

import java.util.Calendar;
import java.util.Date;

/**
 * 今天事件超类
 *
 * @author Liguiqing
 * @since V3.0
 */

public abstract class AbstractDomainEvent {
    private int eventVersion;

    private Date occurredOn;

    public AbstractDomainEvent() {
        this(1, Calendar.getInstance().getTime());
    }

    public AbstractDomainEvent(int eventVersion, Date occurredOn){
        this.eventVersion = eventVersion;
        this.occurredOn = occurredOn;
    }

    public int eventVersion() {
        return eventVersion;
    }

    public Date occurredOn() {
        return occurredOn;
    }
}