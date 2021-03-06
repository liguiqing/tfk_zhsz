package com.zhezhu.access.infrastructure;

import com.zhezhu.access.domain.model.wechat.message.MessageHandler;
import com.zhezhu.access.domain.model.wechat.message.XmlMessage;
import com.zhezhu.access.domain.model.wechat.message.XmlOutMessage;
import com.zhezhu.access.domain.model.wechat.message.XmlOutTextMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Liguiqing
 * @since V3.0
 */

@Component
public class OutMessageHandler implements MessageHandler {

    @Value("${wechat.message.welcome:Welcome}")
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