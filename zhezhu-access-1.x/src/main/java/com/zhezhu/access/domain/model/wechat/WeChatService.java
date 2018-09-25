package com.zhezhu.access.domain.model.wechat;

import com.zhezhu.access.domain.model.wechat.config.WeChatConfig;
import com.zhezhu.commons.util.BrowserMocker;
import com.zhezhu.commons.util.HttpRequestResult;
import com.zhezhu.commons.util.HttpUtilWrapper;
import com.zhezhu.commons.util.JsonUtillWrapper;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import java.util.UUID;

/**
 * 微信接口调用服务
 *
 * @author Liguiqing
 * @since V3.0
 */

public class WeChatService {

    private String code;

    public WeChatService(String code) {
        this.code = code;
    }

    public WebAccessToken getWebAccessToken(WeChatConfig config) {
        WebAccessToken webAccessToken = fromOauth2(config);

        if(webAccessToken.isError()){
            webAccessToken = fromCode2Session(config);
        }
        return webAccessToken;
    }

    public WebAccessToken fromOauth2(WeChatConfig config){
        String url = WeChatContant.URL_OAUTH2_GET_ACCESSTOKEN.replace("APPID", config.getAppId())
                .replace("SECRET", config.getAppSecret())
                .replace("CODE", code);
        return parseWebAccessToken(url);
    }

    public WebAccessToken fromCode2Session(WeChatConfig config){
        String url = WeChatContant.URL_CODE2SESSION.replace("APPID", config.getAppId())
                .replace("SECRET", config.getAppSecret())
                .replace("CODE", code);
        return parseWebAccessToken(url);
    }

    private WebAccessToken parseWebAccessToken(String url) {
        HttpGet get = new HttpGet(url);
        get.addHeader("Content-type", "application/json; charset=utf-8");
        String content = getRequestContent(get);
        WebAccessToken webAccessToken = JsonUtillWrapper.from(content, WebAccessToken.class);
        webAccessToken.setError();
        return webAccessToken;
        //return WebAccessToken.builder().openId("TestOpenId").sessionKey(UUID.randomUUID().toString()).accessToken(UUID.randomUUID().toString()).expiresIn(7200).build();
    }

    public WeChatAccount getWeChatAccount(WeChatConfig config) {
        return parseWeChatAccount(config);
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