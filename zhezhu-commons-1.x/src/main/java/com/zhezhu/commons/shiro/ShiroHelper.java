/*
 * Copyright (c) 2016,2018, zhezhu All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.zhezhu.commons.shiro;

import com.zhezhu.commons.security.UserFace;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http　port的超类
 * @author Liguiqing
 * @since V3.0
 */

public  class ShiroHelper {
    private static Logger logger = LoggerFactory.getLogger(ShiroHelper.class);

    public static  Subject getSubject(){
        Subject subject = SecurityUtils.getSubject();
        logger.debug(subject.toString());
        return subject;
    }

    public static boolean isAuthenticated(){
        Subject subject = getSubject();
        return subject.isAuthenticated();
    }

    public static UserFace getUser(){
        Subject subject = getSubject();
        return (UserFace) subject.getPrincipal();
    }

}