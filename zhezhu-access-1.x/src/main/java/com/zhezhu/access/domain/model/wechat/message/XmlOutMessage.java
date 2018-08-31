package com.zhezhu.access.domain.model.wechat.message;

import com.zhezhu.access.domain.model.wechat.config.WeChatConfig;
import com.zhezhu.access.domain.model.wechat.outer.*;
import com.zhezhu.access.domain.model.wechat.xml.XStreamCDataConverter;
import com.zhezhu.access.domain.model.wechat.xml.XStreamTransformer;
import com.zhezhu.commons.crypto.AesException;
import com.zhezhu.commons.crypto.WXBizMsgCrypt;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@Setter
@Getter
public abstract class XmlOutMessage {

    @XStreamAlias("ToUserName")
    @XStreamConverter(value = XStreamCDataConverter.class)
    protected String toUserName;

    @XStreamAlias("FromUserName")
    @XStreamConverter(value = XStreamCDataConverter.class)
    protected String fromUserName;

    @XStreamAlias("CreateTime")
    protected Long createTime;

    @XStreamAlias("MsgType")
    @XStreamConverter(value = XStreamCDataConverter.class)
    protected String msgType;

    public String toXml() {
        return XStreamTransformer.toXml((Class) this.getClass(), this);
    }

    /**
     * 转换成加密的xml格式
     * @return
     * @throws AesException
     */
    public static String encryptMsg(WeChatConfig weixinConfig, String replyMsg, String timeStamp, String nonce) throws AesException {
        WXBizMsgCrypt pc = new WXBizMsgCrypt(weixinConfig.getToken(), weixinConfig.getAesKey(), weixinConfig.getAppId());
        return pc.encryptMsg(replyMsg, timeStamp, nonce);
    }

    /**
     * 获得文本消息builder
     *
     * @return
     */
    public static TextBuilder TEXT() {
        return new TextBuilder();
    }

    /**
     * 获得图片消息builder
     *
     * @return
     */
    public static ImageBuilder IMAGE() {
        return new ImageBuilder();
    }

    /**
     * 获得语音消息builder
     *
     * @return
     */
    public static VoiceBuilder VOICE() {
        return new VoiceBuilder();
    }

    /**
     * 获得视频消息builder
     *
     * @return
     */
    public static VideoBuilder VIDEO() {
        return new VideoBuilder();
    }

    /**
     * 获得图文消息builder
     *
     * @return
     */
    public static NewsBuilder NEWS() {
        return new NewsBuilder();
    }

    /**
     * 获取音乐消息builder
     * @return
     */
    public static MusicBuilder MUSIC(){
        return new MusicBuilder();
    }

    @Override
    public String toString() {
        return "WxXmlOutMessage [toUserName=" + toUserName + ", fromUserName=" + fromUserName + ", createTime="
                + createTime + ", msgType=" + msgType + "]";
    }

}