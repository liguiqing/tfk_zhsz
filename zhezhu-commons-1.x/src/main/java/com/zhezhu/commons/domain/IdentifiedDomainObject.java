/*
 * Copyright (c) 2016,2017, zhezhu All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.zhezhu.commons.domain;

import java.io.Serializable;

/**
 * 在持久化映射时有主键的对象超类
 *
 * @author Liguiqing
 * @since V3.0
 */

public abstract class IdentifiedDomainObject implements Serializable {
    private static final long serialVersionUID = 1L;

    //使用整型持久化
    private long id;

    //使用字串持久化　,两者只能取其一
    private String uuid;

    protected IdentifiedDomainObject() {
        super();

        this.setId(-1);
    }

    protected long getId() {
        return this.id;
    }

    protected void setId(long anId) {
        this.id = anId;
    }

    protected String getUuid() {
        return uuid;
    }

    protected void setUuid(String uuid) {
        this.uuid = uuid;
    }

}