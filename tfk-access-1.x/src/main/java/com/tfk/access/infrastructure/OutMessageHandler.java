package com.tfk.access.infrastructure;

import com.tfk.access.domain.model.wechat.message.MessageHandler;
import com.tfk.access.domain.model.wechat.message.XmlMessage;
import com.tfk.access.domain.model.wechat.message.XmlOutMessage;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class OutMessageHandler implements MessageHandler {


    @Override
    public XmlOutMessage findOutMessage(XmlMessage message) {
        return null;
    }
}