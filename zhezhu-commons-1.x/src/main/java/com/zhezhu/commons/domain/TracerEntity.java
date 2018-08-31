/*
 * Copyright (c) 2016,2018, zhezhu All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.zhezhu.commons.domain;


/**
 * 可追踪变化的实体
 *
 * @author Liguiqing
 * @since V3.0
 */

public abstract class TracerEntity extends  Entity implements Traceable {

    private Tracer tracer;

    protected TracerEntity() {
        super();
    }

    protected void trace(){
        //通过Shiro自动生成追踪
    }

    @Override
    public void trace(Tracer tracer) {
        this.tracer = tracer;
    }
}