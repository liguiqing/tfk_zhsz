package com.zhezhu.access.domain.model.wechat.message;

import com.zhezhu.access.domain.model.wechat.WeChatContant;
import com.zhezhu.access.domain.model.wechat.xml.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <pre>
 * 被动回复消息--回复文本消息体
 * 
 * 详情:http://mp.weixin.qq.com/wiki/1/6239b44c206cab9145b1d52c67e6c551.html
 * </pre>
 * @author antgan
 *
 */

@Getter
@Setter
@ToString(of={"content"},callSuper = false)
@XStreamAlias("xml")
public class XmlOutTextMessage extends XmlOutMessage {

	@XStreamAlias("Content")
	@XStreamConverter(value = XStreamCDataConverter.class)
	private String content;

	public XmlOutTextMessage() {
		this.msgType = WeChatContant.XML_MSG_TEXT;
	}
	
}
