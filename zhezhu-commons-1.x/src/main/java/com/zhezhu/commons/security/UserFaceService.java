package com.zhezhu.commons.security;

import com.zhezhu.commons.shiro.ShiroHelper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Slf4j
public class UserFaceService {

    public UserFace getUser(){
        log.debug("Get current user");
        return ShiroHelper.getUser();
    }
}