package com.zhezhu.access.infrastructure.shiro;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.core.annotation.Order;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 微信认证过滤
 * @author Liguiqing
 * @since V3.0
 */

public class WeChatAuthenticationFilter extends AuthenticatingFilter {

    @Override
    public String getSuccessUrl(){
        return "/ysyp/wechat/home";
    }

    @Override
    public String getLoginUrl(){
        return "/ysyp/wechat/login";
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return null;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return false;
    }
}