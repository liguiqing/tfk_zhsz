/*
 * Copyright (c) 2016,2017, zhezhu All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.zhezhu.commons.domain;

/**
 * 领域事件消费者
 *
 * @author Liguiqing
 * @since V3.0
 */

public interface DomainEventSubscriber<T> {

    /**
     * 事件处理
     * @param aDomainEvent
     */
    public void handleEvent(final T aDomainEvent);

    /**
     * 被消费的事件类型
     *
     * @return
     */
    public Class<T> subscribedToEventType();
}