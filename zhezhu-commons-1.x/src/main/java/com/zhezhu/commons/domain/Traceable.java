/*
 * Copyright (c) 2016,2018, zhezhu All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.zhezhu.commons.domain;

/**
 * 可追踪对象接口
 *
 * @author Liguiqing
 * @since V3.0
 */

public interface Traceable {

    public void trace(Tracer tracer);
}