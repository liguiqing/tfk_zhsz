package com.tfk.access.domain.model.wechat.message;

/**
 * @author Liguiqing
 * @since V3.0
 */

public interface MessageHandler {

    XmlOutMessage findOutMessage(XmlMessage message);
}