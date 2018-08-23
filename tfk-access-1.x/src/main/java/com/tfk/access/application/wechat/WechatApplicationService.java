package com.tfk.access.application.wechat;

import com.tfk.access.domain.model.wechat.WeChatAccessToken;
import com.tfk.access.domain.model.wechat.WeChatFollow;
import com.tfk.access.domain.model.wechat.WeChatService;
import com.tfk.access.domain.model.wechat.WebAccessToken;
import com.tfk.access.domain.model.wechat.config.WeChatConfig;
import com.tfk.access.domain.model.wechat.message.MessageHandler;
import com.tfk.access.domain.model.wechat.message.XmlMessage;
import com.tfk.access.domain.model.wechat.message.XmlOutMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Slf4j
@Service
public class WechatApplicationService {

    @Autowired
    private MessageHandler messageHandler;

    @Autowired
    private WeChatConfig weChatConfig;

    public String follow(Map<String,String> params){
        log.debug("WeChat Follow Success!");

        WeChatFollow follow = new WeChatFollow();
        follow.create(weChatConfig,params);
        return follow.getContent();
    }

    public String dialog(XmlMessage message){
        log.debug("WeChat Query!");

        XmlOutMessage outMessage = messageHandler.findOutMessage(message);
        return outMessage.toXml();
    }

    @Transactional(rollbackFor = Exception.class)
    public void bind(BindCommand command) {
        log.debug("WeChat Bind {} ",command);
    }

    public WebAccessToken getWeChatAccessToken(String code){
        WeChatService weChatService = new WeChatService(code);
        return weChatService.getWebAccessToken(weChatConfig);
    }
}