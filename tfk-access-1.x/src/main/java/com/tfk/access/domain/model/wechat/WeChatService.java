package com.tfk.access.domain.model.wechat;

import com.tfk.access.domain.model.wechat.config.WeChatConfig;
import com.tfk.commons.util.BrowserMocker;
import com.tfk.commons.util.HttpRequestResult;
import com.tfk.commons.util.HttpUtilWrapper;
import com.tfk.commons.util.JsonUtillWrapper;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

/**
 * 微信接口调用服务
 * @author Liguiqing
 * @since V3.0
 */

public class WeChatService {

    private String code;

    private WeChatAccount account;

    private WebAccessToken webAccessToken;

    public WeChatService(String code) {
        this.code = code;
    }

    public WebAccessToken getWebAccessToken(WeChatConfig config) {
        if (webAccessToken == null) {
            webAccessToken = parseWebAccessToken(config);
        }
        return webAccessToken;
    }

    private WebAccessToken parseWebAccessToken(WeChatConfig config) {
        String url = WeChatContant.URL_OAUTH2_GET_ACCESSTOKEN.replace("APPID", config.getAppId())
                .replace("SECRET", config.getAppSecret())
                .replace("CODE", code);
        HttpGet get = new HttpGet(url);
        get.addHeader("Content-type", "application/json; charset=utf-8");
        String content = getRequestContent(get);
        WebAccessToken webAccessToken = JsonUtillWrapper.from(content, WebAccessToken.class);
        return webAccessToken;
    }

    public WeChatAccount getWeixinAccount(WeChatConfig config) {
        if (account == null) {
            account = parseWeixinAccount(config);
        }
        return account;
    }

    public WeChatAccount parseWeixinAccount(WeChatConfig config) {
        WebAccessToken webAccessToken = getWebAccessToken(config);
        String url = WeChatContant.URL_OAUTH2_GET_USER_INFO.replace("ACCESS_TOKEN", webAccessToken.getAccessToken())
                .replace("OPENID", webAccessToken.getOpenId());
        HttpGet get = new HttpGet(url);
        get.addHeader("Content-type", "application/json; charset=utf-8");
        String content = getRequestContent(get);
        WeChatAccount webUserInfo = JsonUtillWrapper.from(content, WeChatAccount.class);
        return webUserInfo;
    }

    private String getRequestContent(HttpUriRequest httpUriRequest){
        BrowserMocker browser = HttpUtilWrapper.newBrowser();
        HttpRequestResult result = browser.execute(httpUriRequest);
        browser.close();
        return result.getContent();
    }
}