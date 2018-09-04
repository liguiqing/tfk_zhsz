package com.zhezhu.boot.port.adapter.http.controller;

import com.alibaba.druid.pool.DruidDataSource;
import com.zhezhu.commons.port.adaptor.http.controller.AbstractHttpController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Slf4j
@Controller
public class MainController extends AbstractHttpController {


    @RequestMapping("/index")
    public ModelAndView onIndex(){
        log.debug("url index");
        return newModelAndViewBuilder("/index").creat();
    }

    @RequestMapping(value = "/test/db")
    public ModelAndView onTestDb(@RequestParam String url,
                                 @RequestParam String username,
                                 @RequestParam String password)throws Exception{
        log.debug("URL /test/db url:{} username:{} password:{}",url,username,password);

        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        druidDataSource.setInitialSize(10);
        druidDataSource.setMinIdle(1);
        druidDataSource.setMaxActive(20);
        druidDataSource.setMaxWait(60000);
        druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
        druidDataSource.setMinEvictableIdleTimeMillis(30000);
        druidDataSource.setValidationQuery("SELECT 'X'");
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestOnReturn(false);
        druidDataSource.setPoolPreparedStatements(true);
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(100);
        druidDataSource.setFilters("stat");
        Connection cn  = druidDataSource.getConnection();
        PreparedStatement st = cn.prepareStatement("Select 1 from dural");
        st.execute();
        return newModelAndViewBuilder("/index").creat();
    }
}