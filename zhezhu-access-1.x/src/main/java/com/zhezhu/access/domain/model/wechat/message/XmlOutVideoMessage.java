package com.zhezhu.access.domain.model.wechat.message;

import com.zhezhu.access.domain.model.wechat.WeChatContant;
import com.zhezhu.access.domain.model.wechat.xml.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * 被动回复消息--回复视频消息体
 * 
 * 详情:http://mp.weixin.qq.com/wiki/1/6239b44c206cab9145b1d52c67e6c551.html
 * </pre>
 * @author antgan
 *
 */
@XStreamAlias("xml")
public class XmlOutVideoMessage extends XmlOutMessage {

	@XStreamAlias("Video")
	protected final Video video = new Video();

	public XmlOutVideoMessage() {
		this.msgType = WeChatContant.XML_MSG_VIDEO;
	}

	public String getMediaId() {
		return video.getMediaId();
	}

	public void setMediaId(String mediaId) {
		video.setMediaId(mediaId);
	}

	public String getTitle() {
		return video.getTitle();
	}

	public void setTitle(String title) {
		video.setTitle(title);
	}

	public String getDescription() {
		return video.getDescription();
	}

	public void setDescription(String description) {
		video.setDescription(description);
	}

	@Setter
	@Getter
	@XStreamAlias("Video")
	public static class Video {

		@XStreamAlias("MediaId")
		@XStreamConverter(value = XStreamCDataConverter.class)
		private String mediaId;

		@XStreamAlias("Title")
		@XStreamConverter(value = XStreamCDataConverter.class)
		private String title;

		@XStreamAlias("Description")
		@XStreamConverter(value = XStreamCDataConverter.class)
		private String description;
	}

}
