package com.zhezhu.access.domain.model.wechat;

import com.zhezhu.access.domain.model.wechat.config.WeChatConfig;
import com.zhezhu.commons.util.BrowserMocker;
import com.zhezhu.commons.util.HttpRequestResult;
import com.zhezhu.commons.util.HttpUtilWrapper;
import com.zhezhu.commons.util.JsonUtillWrapper;
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

    public WeChatAccount getWeChatAccount(WeChatConfig config) {
        if (account == null) {
            account = parseWeChatAccount(config);
        }
        return account;
    }

    public WeChatAccount parseWeChatAccount(WeChatConfig config) {
        WebAccessToken webAccessToken = getWebAccessToken(config);
        String url = WeChatContant.URL_OAUTH2_GET_USER_INFO.replace("ACCESS_TOKEN", webAccessToken.getAccessToken())
                .replace("OPENID", webAccessToken.getOpenId());
        HttpGet get = new HttpGet(url);
        get.addHeader("Content-type", "application/json; charset=utf-8");
        String content = getRequestContent(get);
        WeChatAccount weChatAccount = JsonUtillWrapper.from(content, WeChatAccount.class);
        return weChatAccount;
    }

    private String getRequestContent(HttpUriRequest httpUriRequest){
        BrowserMocker browser = HttpUtilWrapper.newBrowser();
        HttpRequestResult result = browser.execute(httpUriRequest);
        browser.close();
        return result.getContent();
    }
}