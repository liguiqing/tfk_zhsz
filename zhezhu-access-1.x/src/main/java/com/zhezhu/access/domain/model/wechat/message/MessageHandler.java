package com.zhezhu.access.domain.model.wechat.message;

/**
 * @author Liguiqing
 * @since V3.0
 */

public interface MessageHandler {

    XmlOutMessage messageHandle(XmlMessage message);
}