package com.tfk.access.infrastructure;

import com.tfk.access.domain.model.wechat.message.MessageHandler;
import com.tfk.access.domain.model.wechat.message.XmlMessage;
import com.tfk.access.domain.model.wechat.message.XmlOutMessage;
import com.tfk.access.domain.model.wechat.message.XmlOutTextMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Liguiqing
 * @since V3.0
 */

@Component
public class OutMessageHandler implements MessageHandler {

    @Value("${wechat.message.welcome}")
    private String defaultMessage = "";

    private List<XmlOutMessage> outMessages;

    @Override
    public XmlOutMessage messageHandle(XmlMessage message) {
        XmlOutMessage outMessage = getOutXmlOutMessage(message);
        if(outMessage == null){
            outMessage = XmlOutTextMessage.TEXT().content(defaultMessage).build();
            outMessage.setFromUserName(message.getToUserName());
            outMessage.setToUserName(message.getFromUserName());
        }
        return  outMessage;
    }

    private XmlOutMessage getOutXmlOutMessage(XmlMessage message){

        //TODO
        return null;
    }

}