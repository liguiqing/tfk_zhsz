package com.zhezhu.access.infrastructure.shiro;

import lombok.*;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

/**
 * @author Liguiqing
 * @since V3.0
 */
@AllArgsConstructor
@EqualsAndHashCode(of={"code"},callSuper = false)
@ToString
@Getter
public class WeChatToken implements AuthenticationToken, RememberMeAuthenticationToken {
    private String code;

    private boolean rememberMe = false;

    @Override
    public Object getPrincipal() {
        return getCode();
    }

    @Override
    public Object getCredentials() {
        return getCode();
    }

    @Override
    public boolean isRememberMe() {
        return rememberMe;
    }
}