package com.zhezhu.boot.infrastructure;

import com.zhezhu.boot.confing.BootTestConfiguration;
import com.zhezhu.boot.infrastructure.init.DbInitService;
import com.zhezhu.commons.config.CommonsConfiguration;
import com.zhezhu.share.config.ShareConfiguration;
import com.zhezhu.sm.config.SmApplicationConfiguration;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

/**
 * Copyright (c) 2016,$today.year, 深圳市易考试乐学测评有限公司
 **/

@ContextConfiguration(classes = {DbInitService.class,ShareConfiguration.class,
        BootTestConfiguration.class, SmApplicationConfiguration.class, CommonsConfiguration.class})
@Transactional
@Rollback
public class DbInitServiceTest  extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private DbInitService service;

    @Test
    public void doInit() throws Exception{
        service.doInit();
    }
}