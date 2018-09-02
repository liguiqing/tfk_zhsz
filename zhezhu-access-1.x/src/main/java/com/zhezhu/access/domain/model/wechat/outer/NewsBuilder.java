package com.zhezhu.access.domain.model.wechat.outer;


import com.zhezhu.access.domain.model.wechat.message.XmlOutNewsMessage;
import com.zhezhu.access.domain.model.wechat.message.XmlOutNewsMessage.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * 图文消息builder
 *
 * @author antgan
 */
public final class NewsBuilder extends BaseBuilder<NewsBuilder, XmlOutNewsMessage> {

    protected final List<Item> articles = new ArrayList<Item>();

    public NewsBuilder addArticle(Item item) {
        this.articles.add(item);
        return this;
    }

    public XmlOutNewsMessage build() {
        XmlOutNewsMessage m = new XmlOutNewsMessage();
        for (Item item : articles) {
            m.addArticle(item);
        }
        setCommon(m);
        return m;
    }

}
