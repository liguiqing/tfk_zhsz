package com.zhezhu.assessment.domain.model.assesse.rank;

import com.zhezhu.assessment.domain.model.assesse.RankCategory;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Copyright (c) 2016,$today.year, 深圳市易考试乐学测评有限公司
 **/
public class WeekendDateTest {

    @Test
    public void supports() {
        WeekendDate weekendDate = new WeekendDate();
        assertTrue(weekendDate.supports(RankCategory.Weekend));
        assertFalse(weekendDate.supports(RankCategory.Day));
        assertFalse(weekendDate.supports(RankCategory.Term));
        assertFalse(weekendDate.supports(RankCategory.Month));
        assertFalse(weekendDate.supports(RankCategory.Year));
    }

    @Test
    public void from() {
        WeekendDate weekendDate = new WeekendDate();
        Date from = weekendDate.from();

    }

    @Test
    public void to() {
        WeekendDate weekendDate = new WeekendDate();
        Date to = weekendDate.to();
    }
}