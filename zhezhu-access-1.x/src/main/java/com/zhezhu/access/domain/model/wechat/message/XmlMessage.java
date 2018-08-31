package com.zhezhu.access.domain.model.wechat.message;

import com.zhezhu.access.domain.model.wechat.WeChatContant;
import com.zhezhu.access.domain.model.wechat.config.WeChatConfig;
import com.zhezhu.access.domain.model.wechat.xml.XStreamCDataConverter;
import com.zhezhu.access.domain.model.wechat.xml.XStreamTransformer;
import com.zhezhu.commons.crypto.AesException;
import com.zhezhu.commons.crypto.WXBizMsgCrypt;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 微信推送过来的消息，也是同步回复给用户的消息，xml格式
 * 相关字段的解释看微信开发者文档：
 * http://mp.weixin.qq.com/wiki/17/f298879f8fb29ab98b2f2971d42552fd.html
 * http://mp.weixin.qq.com/wiki/7/9f89d962eba4c5924ed95b513ba69d9b.html
 *
 * </pre>
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@Getter
@Setter
public class XmlMessage {
///////////////////////
    // 以下都是微信推送过来的消息的xml的element所对应的属性
    ///////////////////////

    @XStreamAlias("ToUserName")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String toUserName;

    @XStreamAlias("FromUserName")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String fromUserName;

    @XStreamAlias("CreateTime")
    private Long createTime;



    /**
     * <pre>
     * 当接受用户消息时，可能会获得以下值：
     * {@link WeChatContant#XML_MSG_TEXT}
     * {@link WeChatContant#XML_MSG_IMAGE}
     * {@link WeChatContant#XML_MSG_VOICE}
     * {@link WeChatContant#XML_MSG_VIDEO}
     * {@link WeChatContant#XML_MSG_LOCATION}
     * {@link WeChatContant#XML_MSG_LINK}
     * {@link WeChatContant#XML_MSG_EVENT}
     * </pre>
     *
     * 当发送消息的时候使用：
     * {@link WeChatContant#XML_MSG_TEXT}
     * {@link WeChatContant#XML_MSG_IMAGE}
     * {@link WeChatContant#XML_MSG_VOICE}
     * {@link WeChatContant#XML_MSG_VIDEO}
     * {@link WeChatContant#XML_MSG_NEWS}
     * </pre>
     *
     */
    @XStreamAlias("MsgType")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String msgType;

    @XStreamAlias("Content")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String content;

    @XStreamAlias("MsgId")
    private Long msgId;

    @XStreamAlias("PicUrl")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String picUrl;

    @XStreamAlias("MediaId")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String mediaId;

    @XStreamAlias("Format")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String format;

    @XStreamAlias("ThumbMediaId")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String thumbMediaId;

    @XStreamAlias("Location_X")
    private Double locationX;

    @XStreamAlias("Location_Y")
    private Double locationY;

    @XStreamAlias("Scale")
    private Double scale;

    @XStreamAlias("Label")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String label;

    @XStreamAlias("Title")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String title;

    @XStreamAlias("Description")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String description;

    @XStreamAlias("Url")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String url;

    @XStreamAlias("Event")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String event;

    @XStreamAlias("EventKey")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String eventKey;

    @XStreamAlias("Ticket")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String ticket;

    @XStreamAlias("Latitude")
    private Double latitude;

    @XStreamAlias("Longitude")
    private Double longitude;

    @XStreamAlias("Precision")
    private Double precision;

    @XStreamAlias("Recognition")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String recognition;

    @XStreamAlias("ExpiredTime")
    private String expiredTime;

    @XStreamAlias("FailTime")
    private String failTime;

    @XStreamAlias("FailReason")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String failReason;

    @XStreamAlias("TransInfo")
    private CustomerService kf;

    @XStreamAlias("KfAccount")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String kfAccount;

    @XStreamAlias("FromKfAccount")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String fromKfAccount;

    @XStreamAlias("ToKfAccount")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String toKfAccount;


    ///////////////////////////////////////
    // 群发消息返回的结果
    ///////////////////////////////////////
    /**
     * 群发的结果
     */
    @XStreamAlias("Status")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String status;
    /**
     * group_id下粉丝数；或者openid_list中的粉丝数
     */
    @XStreamAlias("TotalCount")
    private Integer totalCount;
    /**
     * 过滤（过滤是指特定地区、性别的过滤、用户设置拒收的过滤，用户接收已超4条的过滤）后，准备发送的粉丝数，原则上，filterCount =
     * sentCount + errorCount
     */
    @XStreamAlias("FilterCount")
    private Integer filterCount;
    /**
     * 发送成功的粉丝数
     */
    @XStreamAlias("SentCount")
    private Integer sentCount;
    /**
     * 发送失败的粉丝数
     */
    @XStreamAlias("ErrorCount")
    private Integer errorCount;

    @XStreamAlias("ScanCodeInfo")
    private ScanCodeInfo scanCodeInfo = new ScanCodeInfo();

    @XStreamAlias("SendPicsInfo")
    private SendPicsInfo sendPicsInfo = new SendPicsInfo();

    @XStreamAlias("SendLocationInfo")
    private SendLocationInfo sendLocationInfo = new SendLocationInfo();


    protected static XmlMessage fromXml(String xml) {
        return XStreamTransformer.fromXml(XmlMessage.class, xml);
    }

    protected static XmlMessage fromXml(InputStream is) {
        return XStreamTransformer.fromXml(XmlMessage.class, is);
    }

    /***
     * 密文转明文
     * @param encryptedXml
     * @param WeixinConfig
     * @param timestamp
     * @param nonce
     * @param msgSignature
     * @return
     * @throws AesException
     */
    public static XmlMessage decryptMsg(String encryptedXml, WeChatConfig WeixinConfig,
                                          String timestamp, String nonce, String msgSignature) throws AesException {
        WXBizMsgCrypt pc = new WXBizMsgCrypt(WeixinConfig.getToken(), WeixinConfig.getAesKey(), WeixinConfig.getAppId());
        String plainText = pc.decryptMsg(msgSignature, timestamp, nonce, encryptedXml);
        return fromXml(plainText);
    }
    /**
     * 密文转明文
     * @param is
     * @param WeixinConfig
     * @param timestamp
     * @param nonce
     * @param msgSignature
     * @return
     * @throws AesException
     */
    public static XmlMessage decryptMsg(InputStream is, WeChatConfig WeixinConfig, String timestamp,
                                        String nonce, String msgSignature) throws AesException {
        try {
            return decryptMsg(IOUtils.toString(is, "UTF-8"), WeixinConfig, timestamp, nonce, msgSignature);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//
//    public XmlMessage.ScanCodeInfo getScanCodeInfo() {
//        return scanCodeInfo;
//    }
//
//    public void setScanCodeInfo(XmlMessage.ScanCodeInfo scanCodeInfo) {
//        this.scanCodeInfo = scanCodeInfo;
//    }
//
//    public XmlMessage.SendPicsInfo getSendPicsInfo() {
//        return sendPicsInfo;
//    }
//
//    public void setSendPicsInfo(XmlMessage.SendPicsInfo sendPicsInfo) {
//        this.sendPicsInfo = sendPicsInfo;
//    }
//
//    public XmlMessage.SendLocationInfo getSendLocationInfo() {
//        return sendLocationInfo;
//    }
//
//    public void setSendLocationInfo(XmlMessage.SendLocationInfo sendLocationInfo) {
//        this.sendLocationInfo = sendLocationInfo;
//    }
//
//    public CustomerService getKf() {
//        return kf;
//    }
//
//    public void setKf(CustomerService kf) {
//        this.kf = kf;
//    }
//
//    public String getKfAccount() {
//        return kfAccount;
//    }
//
//    public void setKfAccount(String kfAccount) {
//        this.kfAccount = kfAccount;
//    }
//
//    public String getFromKfAccount() {
//        return fromKfAccount;
//    }
//
//    public void setFromKfAccount(String fromKfAccount) {
//        this.fromKfAccount = fromKfAccount;
//    }
//
//    public String getToKfAccount() {
//        return toKfAccount;
//    }
//
//    public void setToKfAccount(String toKfAccount) {
//        this.toKfAccount = toKfAccount;
//    }

    @Override
    public String toString() {
        return "XmlMessage [toUserName=" + toUserName + ", fromUserName=" + fromUserName + ", createTime="
                + createTime + ", msgType=" + msgType + ", content=" + content + ", msgId=" + msgId + ", picUrl="
                + picUrl + ", mediaId=" + mediaId + ", format=" + format + ", thumbMediaId=" + thumbMediaId
                + ", locationX=" + locationX + ", locationY=" + locationY + ", scale=" + scale + ", label=" + label
                + ", title=" + title + ", description=" + description + ", url=" + url + ", event=" + event
                + ", eventKey=" + eventKey + ", ticket=" + ticket + ", latitude=" + latitude + ", longitude="
                + longitude + ", precision=" + precision + ", recognition=" + recognition + ", expiredTime="
                + expiredTime + ", failTime=" + failTime + ", failReason=" + failReason + ", status=" + status
                + ", totalCount=" + totalCount + ", filterCount=" + filterCount + ", sentCount=" + sentCount
                + ", errorCount=" + errorCount + ", scanCodeInfo=" + scanCodeInfo + ", sendPicsInfo=" + sendPicsInfo
                + ", sendLocationInfo=" + sendLocationInfo + "]";
    }

    /**
     * 微信客服
     * @author antgan
     *
     */
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class CustomerService {
        @XStreamAlias("KfAccount")
        @XStreamConverter(value = XStreamCDataConverter.class)
        private String kfAccount;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    @XStreamAlias("ScanCodeInfo")
    public static class ScanCodeInfo {

        @XStreamAlias("ScanType")
        @XStreamConverter(value = XStreamCDataConverter.class)
        private String scanType; //扫描类型，一般是qrcode

        @XStreamAlias("ScanResult")
        @XStreamConverter(value = XStreamCDataConverter.class)
        private String scanResult; //扫描结果，即二维码对应的字符串信息

    }

    @NoArgsConstructor
    @Getter
    @Setter
    @XStreamAlias("SendPicsInfo")
    public static class SendPicsInfo {

        @XStreamAlias("Count")
        private Long count;

        @XStreamAlias("PicList")
        protected final List<Item> picList = new ArrayList<Item>();

        @NoArgsConstructor
        @Getter
        @Setter
        @XStreamAlias("item")
        public static class Item {

            @XStreamAlias("PicMd5Sum")
            @XStreamConverter(value = XStreamCDataConverter.class)
            private String PicMd5Sum;

        }
    }

    @NoArgsConstructor
    @Getter
    @Setter
    @XStreamAlias("SendLocationInfo")
    public static class SendLocationInfo {

        @XStreamAlias("Location_X")
        @XStreamConverter(value = XStreamCDataConverter.class)
        private String locationX;

        @XStreamAlias("Location_Y")
        @XStreamConverter(value = XStreamCDataConverter.class)
        private String locationY;

        @XStreamAlias("Scale")
        @XStreamConverter(value = XStreamCDataConverter.class)
        private String scale;

        @XStreamAlias("Label")
        @XStreamConverter(value = XStreamCDataConverter.class)
        private String label;

        @XStreamAlias("Poiname")
        @XStreamConverter(value = XStreamCDataConverter.class)
        private String poiname;
    }

}