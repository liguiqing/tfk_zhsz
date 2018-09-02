package com.zhezhu.access.domain.model.wechat.message;

import com.zhezhu.access.domain.model.wechat.WeChatContant;
import com.zhezhu.access.domain.model.wechat.xml.XStreamMediaIdConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * 被动回复消息--回复图片消息体
 * 
 * 详情:http://mp.weixin.qq.com/wiki/1/6239b44c206cab9145b1d52c67e6c551.html
 * </pre>
 * @author antgan
 *
 */
@Getter
@Setter
@XStreamAlias("xml")
public class XmlOutImageMessage extends XmlOutMessage {

	@XStreamAlias("Image")
	@XStreamConverter(value = XStreamMediaIdConverter.class)
	private String mediaId;

	public XmlOutImageMessage() {
		this.msgType = WeChatContant.XML_MSG_IMAGE;
	}
}
