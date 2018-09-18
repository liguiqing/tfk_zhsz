package com.zhezhu.assessment.domain.model.assesse.rank;

import com.zhezhu.assessment.domain.model.assesse.RankCategory;
import com.zhezhu.commons.util.DateUtilWrapper;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Copyright (c) 2016,$today.year, 深圳市易考试乐学测评有限公司
 **/
public class WeekendDateTest {

    @Test
    public void test() {
        WeekendDate weekendDate = new WeekendDate();
        assertTrue(weekendDate.supports(RankCategory.Weekend));
        assertFalse(weekendDate.supports(RankCategory.Day));
        assertFalse(weekendDate.supports(RankCategory.Term));
        assertFalse(weekendDate.supports(RankCategory.Month));
        assertFalse(weekendDate.supports(RankCategory.Year));

        LocalDate now = LocalDate.now();
        int day = LocalDate.now().getDayOfWeek().getValue();
        LocalDate date = now.minusDays(day-1);
        String time = date.toString()+" 00:00:00";
        assertEquals(time, DateUtilWrapper.toString(weekendDate.from(),"yyyy-MM-dd HH:mm:ss"));
        if(day <5){
            date = now.plusDays((5-day));
        }else if(day == 5){
            date = now;
        }else{
            date = now.minusDays((day-5));
        }

        time = date.toString()+" 23:59:59";
        assertEquals(time, DateUtilWrapper.toString(weekendDate.to(),"yyyy-MM-dd HH:mm:ss"));

        String node = new WeekendDate().node();
        int week = Integer.valueOf(node);
        assertTrue(1 <= week && week <= 54);
    }

}