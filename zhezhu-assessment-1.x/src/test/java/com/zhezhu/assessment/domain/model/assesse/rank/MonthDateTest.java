package com.zhezhu.assessment.domain.model.assesse.rank;

import com.zhezhu.assessment.domain.model.assesse.RankCategory;
import com.zhezhu.commons.util.DateUtilWrapper;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.*;

/**
 * Copyright (c) 2016,$today.year, 深圳市易考试乐学测评有限公司
 **/
public class MonthDateTest {

    @Test
    public void test(){
        MonthDate data = new MonthDate();
        assertTrue(data.supports(RankCategory.Month));
        assertFalse(data.supports(RankCategory.Day));
        assertFalse(data.supports(RankCategory.Term));
        assertFalse(data.supports(RankCategory.Weekend));
        assertFalse(data.supports(RankCategory.Year));

        LocalDate now = LocalDate.now();
        int d = now.getDayOfMonth();
        Month month = now.getMonth();
        int m = month.maxLength();
        LocalDate mDay = now.plusDays(m-d);
        assertEquals(mDay+" 23:59:59", DateUtilWrapper.toString(data.to(),"yyyy-MM-dd HH:mm:ss"));
        mDay = now.minusDays(d-1);
        assertEquals(mDay+" 00:00:00", DateUtilWrapper.toString(data.from(),"yyyy-MM-dd HH:mm:ss"));
    }
}