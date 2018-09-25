package com.zhezhu.access.infrastructure.shiro;

import com.zhezhu.access.application.wechat.WeChatData;
import com.zhezhu.access.application.wechat.WeChatQueryService;
import com.zhezhu.access.domain.model.wechat.WebAccessToken;
import com.zhezhu.access.domain.model.wechat.WebAccessTokenFactory;
import com.zhezhu.commons.util.CollectionsUtilWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

import java.util.List;

/**
 * 来自微信的用户数据
 *
 * @author Liguiqing
 * @since V3.0
 */
@Slf4j
public class WeChatUserRealm extends AuthorizingRealm {
    private WebAccessTokenFactory webAccessTokenFactory;

    private WeChatQueryService queryService;

    public WeChatUserRealm(WebAccessTokenFactory webAccessTokenFactory, WeChatQueryService queryService) {
        this.webAccessTokenFactory = webAccessTokenFactory;
        this.queryService = queryService;
    }

    @Override
    public boolean supports(AuthenticationToken token){
        return  token instanceof WeChatToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return new SimpleAuthorizationInfo();
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.debug("Authentication from WeChat ");

        WeChatToken weChatToken = (WeChatToken) token;
        WebAccessToken webAccessToken = webAccessTokenFactory.newWebAccessToken(weChatToken.getCode());
        if(webAccessToken.isError()){
            log.debug("Authentication from weChat failure: {}",webAccessToken);
            throw new UnknownAccountException();
        }

        log.debug("Authentication from WeChat Success");
        SimplePrincipalCollection principalCollection = new SimplePrincipalCollection(webAccessToken, webAccessToken.getOpenId());
        WeChatAuthenticationInfo authenticationInfo = new WeChatAuthenticationInfo(principalCollection, webAccessToken.getOpenId());
        authenticationInfo.setCredentials(webAccessToken.getOpenId());
        return authenticationInfo;
    }
}