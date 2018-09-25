package com.zhezhu.access.domain.model.wechat;

import com.zhezhu.access.domain.model.wechat.config.WeChatConfig;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class WebAccessTokenFactory {

    private WeChatConfig weChatConfig;

    public WebAccessTokenFactory(WeChatConfig weChatConfig){
        this.weChatConfig = weChatConfig;
    }

    public WebAccessToken newWebAccessToken(String code){
        return new WeChatService(code).fromCode2Session(weChatConfig);
    }

}