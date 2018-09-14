package com.zhezhu.access.domain.model.wechat.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.zhezhu.access.domain.model.wechat.WeChatContant;
import com.zhezhu.access.domain.model.wechat.xml.XStreamMediaIdConverter;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * 被动回复消息--回复语音消息体
 * 
 * 详情:http://mp.weixin.qq.com/wiki/1/6239b44c206cab9145b1d52c67e6c551.html
 * </pre>
 * @author antgan
 *
 */
@Setter
@Getter
@XStreamAlias("xml")
public class XmlOutVoiceMessage extends XmlOutMessage {

	@XStreamAlias("Voice")
	@XStreamConverter(value = XStreamMediaIdConverter.class)
	private String mediaId;

	public XmlOutVoiceMessage() {
		this.msgType = WeChatContant.XML_MSG_VOICE;
	}

}
