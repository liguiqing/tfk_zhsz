/*
 * Copyright (c) 2016,2018, zhezhu All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.zhezhu.commons.domain;

import java.util.Date;

/**
 * 追踪者
 *
 * @author Liguiqing
 * @since V3.0
 */

public class Tracer {
    private String lastOperatorId;

    private String lastOperatorName;

    private Date lastUpdateTime;

    public Tracer(String lastOperatorId, String lastOperatorName, Date lastUpdateTime) {
        this.lastOperatorId = lastOperatorId;
        this.lastOperatorName = lastOperatorName;
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getLastOperatorId() {
        return lastOperatorId;
    }

    public String getLastOperatorName() {
        return lastOperatorName;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }
}