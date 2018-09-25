package com.zhezhu.access.infrastructure.shiro;

import com.zhezhu.access.application.wechat.WeChatData;
import com.zhezhu.access.domain.model.wechat.WebAccessToken;
import com.zhezhu.access.domain.model.wechat.WebAccessTokenFactory;
import com.zhezhu.commons.lang.Closer;
import com.zhezhu.commons.lang.Throwables;
import com.zhezhu.commons.port.adaptor.http.controller.MessageSource;
import com.zhezhu.commons.port.adaptor.http.controller.MessageSourceFactory;
import com.zhezhu.commons.port.adaptor.http.controller.ModelAndViewBuilder;
import com.zhezhu.commons.util.JsonUtillWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 微信认证过滤
 * @author Liguiqing
 * @since V3.0
 */
@Slf4j
public class WeChatAuthenticationFilter extends AuthenticatingFilter {

    private MessageSourceFactory messageSourceFactory;

    private WebAccessTokenFactory webAccessTokenFactory;

    public WeChatAuthenticationFilter(MessageSourceFactory messageSourceFactory,
                                      WebAccessTokenFactory webAccessTokenFactory) {
        this.messageSourceFactory = messageSourceFactory;
        this.webAccessTokenFactory = webAccessTokenFactory;
    }


    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        String code = servletRequest.getParameter("code");
        return new WeChatToken(code,false);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        if(isWeChatRequest(servletRequest)){
            return executeLogin(servletRequest, servletResponse);
        }
        return false;
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
                                     ServletResponse response) throws Exception {
        log.debug("WeChat login success ");

        PrincipalCollection userPrincipalCollection = subject.getPrincipals();
        WebAccessToken webAccessToken = (WebAccessToken)userPrincipalCollection.getPrimaryPrincipal();

        Session session = subject.getSession();
        session.setAttribute("WeChatSession",webAccessToken.getSessionKey());
        request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, "Stateless request");
        request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, session.getId());
        request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);

        MessageSource messageSource = messageSourceFactory.lookup(request.getParameter("local"));
        ModelAndViewBuilder modelAndViewBuilder =  new ModelAndViewBuilder("",messageSource);
        modelAndViewBuilder.withData("accessToken", webAccessToken);

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setCharacterEncoding("UTF-8");
        PrintWriter out = httpServletResponse.getWriter();
        out.println(JsonUtillWrapper.toJson(modelAndViewBuilder.creat().getModelMap()));
        out.flush();
        Closer.close(out);
        return false;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
                                     ServletResponse response) {
        log.debug("WeChat login failure ");
        MessageSource messageSource = messageSourceFactory.lookup(request.getParameter("local"));
        ModelAndViewBuilder modelAndViewBuilder =  new ModelAndViewBuilder("",messageSource).failure().withCode("000").withData("error",Throwables.toString(e));

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        try {
            out = httpServletResponse.getWriter();
            out.println(JsonUtillWrapper.toJson(modelAndViewBuilder.creat().getModelMap()));
            out.flush();
        } catch (IOException e1) {
            log.error(Throwables.toString(e1));
        }finally {
            Closer.close(out);
        }
        return false;
    }

    private boolean isWeChatRequest(ServletRequest servletRequest){
        return true;
    }
}