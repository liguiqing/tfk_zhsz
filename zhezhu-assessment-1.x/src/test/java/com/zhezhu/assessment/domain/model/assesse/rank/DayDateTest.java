package com.zhezhu.assessment.domain.model.assesse.rank;

import com.zhezhu.assessment.domain.model.assesse.RankCategory;
import com.zhezhu.commons.util.DateUtilWrapper;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * Copyright (c) 2016,$today.year, 深圳市易考试乐学测评有限公司
 **/
public class DayDateTest {

    @Test
    public void test(){
        DayDate dayDate = new DayDate();
        assertTrue(dayDate.supports(RankCategory.Day));
        assertFalse(dayDate.supports(RankCategory.Weekend));
        assertFalse(dayDate.supports(RankCategory.Month));
        assertFalse(dayDate.supports(RankCategory.Year));
        LocalDate now = LocalDate.now();
        assertEquals(now.toString(),dayDate.node());
        String time = now.toString()+" 00:00:00";
        assertEquals(time, DateUtilWrapper.toString(dayDate.from(),"yyyy-MM-dd HH:mm:ss"));
        time = now.toString()+" 23:59:59";
        assertEquals(time, DateUtilWrapper.toString(dayDate.to(),"yyyy-MM-dd HH:mm:ss"));
    }
}