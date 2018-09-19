package com.zhezhu.commons.security;

import lombok.*;

import java.util.Map;

/**
 * 系统用户
 *
 * @author Liguiqing
 * @since V3.0
 */
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of={"userName"},callSuper = false)
@ToString(exclude = {"password"})
public class InnerUser implements UserFace{
    private String userId;

    private String userName;

    private String userRealName;

    private String userPersonId;

    private String password;

    /**
     * 此方法只能调用一次
     *
     * @return
     */
    public String pwd(){
        String pwd = this.password;
        this.password = "";
        return pwd;
    }

    @Override
    public String getUserId() {
        return this.userId;
    }

    @Override
    public String getUserName() {
        return this.userName;
    }

    @Override
    public String getUserRealName() {
        return this.userRealName;
    }

    @Override
    public String getUserPersonId() {
        return this.userPersonId;
    }
}