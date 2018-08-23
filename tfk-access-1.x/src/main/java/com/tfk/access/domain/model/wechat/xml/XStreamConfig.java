package com.tfk.access.domain.model.wechat.xml;


import com.google.common.collect.Maps;
import com.tfk.access.domain.model.wechat.message.*;
import com.thoughtworks.xstream.XStream;

import java.util.Map;

/**
 *
 */
public class XStreamConfig {
    public static Map<Class, XStream> getConfig() {

        Map<Class, XStream> map = Maps.newHashMap();
        map.put(XmlMessage.class, config_WxXmlMessage());
        map.put(XmlOutNewsMessage.class, config_WxXmlOutNewsMessage());
        map.put(XmlOutTextMessage.class, config_WxXmlOutTextMessage());
        map.put(XmlOutImageMessage.class, config_WxXmlOutImageMessage());
        map.put(XmlOutVideoMessage.class, config_WxXmlOutVideoMessage());
        map.put(XmlOutVoiceMessage.class, config_WxXmlOutVoiceMessage());
        return map;
    }


    private static XStream config_WxXmlMessage() {
        XStream xstream = XStreamInitializer.getInstance();
        xstream.processAnnotations(XmlMessage.class);
        xstream.processAnnotations(XmlMessage.ScanCodeInfo.class);
        xstream.processAnnotations(XmlMessage.SendPicsInfo.class);
        xstream.processAnnotations(XmlMessage.SendPicsInfo.Item.class);
        xstream.processAnnotations(XmlMessage.SendLocationInfo.class);
        return xstream;
    }

    private static XStream config_WxXmlOutImageMessage() {
        XStream xstream = XStreamInitializer.getInstance();
        xstream.processAnnotations(XmlOutMessage.class);
        xstream.processAnnotations(XmlOutImageMessage.class);
        return xstream;
    }

    private static XStream config_WxXmlOutNewsMessage() {
        XStream xstream = XStreamInitializer.getInstance();
        xstream.processAnnotations(XmlOutMessage.class);
        xstream.processAnnotations(XmlOutNewsMessage.class);
        xstream.processAnnotations(XmlOutNewsMessage.Item.class);
        return xstream;
    }

    private static XStream config_WxXmlOutTextMessage() {
        XStream xstream = XStreamInitializer.getInstance();
        xstream.processAnnotations(XmlOutMessage.class);
        xstream.processAnnotations(XmlOutTextMessage.class);
        return xstream;
    }

    private static XStream config_WxXmlOutVideoMessage() {
        XStream xstream = XStreamInitializer.getInstance();
        xstream.processAnnotations(XmlOutMessage.class);
        xstream.processAnnotations(XmlOutVideoMessage.class);
        xstream.processAnnotations(XmlOutVideoMessage.Video.class);
        return xstream;
    }

    private static XStream config_WxXmlOutVoiceMessage() {
        XStream xstream = XStreamInitializer.getInstance();
        xstream.processAnnotations(XmlOutMessage.class);
        xstream.processAnnotations(XmlOutVoiceMessage.class);
        return xstream;
    }
}
