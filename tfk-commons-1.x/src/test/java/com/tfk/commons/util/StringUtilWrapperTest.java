package com.tfk.commons.util;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Copyright (c) 2016,$today.year, 深圳市易考试乐学测评有限公司
 **/
public class StringUtilWrapperTest {

    @Test
    public void chineseTranslateToSpelling() {
        String name = "赵钱孙李";
        String spelling = StringUtilWrapper.chineseTranslateToSpelling(name,true);
        assertEquals("Z", StringUtils.substring(spelling,0,1));
        assertEquals("Q", StringUtils.substring(spelling,4,5));
    }
}