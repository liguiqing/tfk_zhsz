package com.tfk.access.domain.model.wechat.message;

import com.tfk.access.domain.model.wechat.WeChatContant;
import com.tfk.access.domain.model.wechat.xml.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 被动回复消息--回复图文消息体
 * 
 * 详情:http://mp.weixin.qq.com/wiki/1/6239b44c206cab9145b1d52c67e6c551.html
 * </pre>
 * @author antgan
 *
 */
@XStreamAlias("xml")
public class XmlOutNewsMessage extends XmlOutMessage {

	@XStreamAlias("ArticleCount")
	protected int articleCount;

	@XStreamAlias("Articles")
	protected final List<Item> articles = new ArrayList<Item>();

	public XmlOutNewsMessage() {
		this.msgType = WeChatContant.XML_MSG_NEWS;
	}

	public int getArticleCount() {
		return articleCount;
	}

	public void addArticle(Item item) {
		this.articles.add(item);
		this.articleCount = this.articles.size();
	}

	public List<Item> getArticles() {
		return articles;
	}

	@Getter
	@Setter
	@XStreamAlias("item")
	public static class Item {

		@XStreamAlias("Title")
		@XStreamConverter(value = XStreamCDataConverter.class)
		private String Title;

		@XStreamAlias("Description")
		@XStreamConverter(value = XStreamCDataConverter.class)
		private String Description;

		@XStreamAlias("PicUrl")
		@XStreamConverter(value = XStreamCDataConverter.class)
		private String PicUrl;

		@XStreamAlias("Url")
		@XStreamConverter(value = XStreamCDataConverter.class)
		private String Url;
	}

}
