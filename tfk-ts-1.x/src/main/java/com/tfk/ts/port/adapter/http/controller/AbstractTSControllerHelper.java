/*
 * Copyright (c) 2016,2018, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.port.adapter.http.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * http port 超类，所有http port须继承此类
 * @author Liguiqing
 * @since V3.0
 */

@Controller
@RequestMapping(value = "/ts")
public abstract class AbstractTSControllerHelper {
    protected static Logger logger = LoggerFactory.getLogger(AbstractTSControllerHelper.class);

}