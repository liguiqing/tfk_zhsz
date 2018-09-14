package com.zhezhu.access.domain.model.wechat.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.zhezhu.access.domain.model.wechat.WeChatContant;
import com.zhezhu.access.domain.model.wechat.xml.XStreamCDataConverter;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * 被动回复消息--回复音乐消息体
 * 
 * 详情:http://mp.weixin.qq.com/wiki/1/6239b44c206cab9145b1d52c67e6c551.html
 * </pre>
 * @author antgan
 *
 */
public class XmlOutMusicMessage extends XmlOutMessage {
	
	@XStreamAlias("Music")
	protected final Music music = new Music();
	
	public XmlOutMusicMessage() {
		this.msgType = WeChatContant.XML_MSG_MUSIC;
	}
	
	public String getTitle() {
		return music.title;
	}

	public void setTitle(String title) {
		music.title = title;
	}

	public String getDescription() {
		return music.description;
	}

	public void setDescription(String description) {
		music.description = description;
	}

	public String getMusicUrl() {
		return music.musicUrl;
	}

	public void setMusicUrl(String musicUrl) {
		music.musicUrl = musicUrl;
	}

	public String gethQMusicUrl() {
		return music.hQMusicUrl;
	}

	public void sethQMusicUrl(String hQMusicUrl) {
		music.hQMusicUrl = hQMusicUrl;
	}

	public String getThumbMediaId() {
		return music.thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		music.thumbMediaId = thumbMediaId;
	}

	@Setter
	@Getter
	@XStreamAlias("Music")
	public static class Music{
		@XStreamAlias("Title")
		@XStreamConverter(value = XStreamCDataConverter.class)
		private String title;

		@XStreamAlias("Description")
		@XStreamConverter(value = XStreamCDataConverter.class)
		private String description;
		
		@XStreamAlias("MusicUrl")
		@XStreamConverter(value = XStreamCDataConverter.class)
		private String musicUrl;
		
		@XStreamAlias("HQMusicUrl")
		@XStreamConverter(value = XStreamCDataConverter.class)
		private String hQMusicUrl;
		
		@XStreamAlias("ThumbMediaId")
		@XStreamConverter(value = XStreamCDataConverter.class)
		private String thumbMediaId;
		
	} 
}
