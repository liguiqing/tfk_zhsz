package com.zhezhu.assessment.domain.model.assesse.rank;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Copyright (c) 2016,$today.year, 深圳市易考试乐学测评有限公司
 **/
public class SameScoreSamRankStrategyTest {

    @Test
    public void getRank() {
        SameScoreSamRankStrategy strategy = new SameScoreSamRankStrategy();
        assertEquals(1,strategy.getRank(3));
        assertEquals(1,strategy.getRank(3));
        assertEquals(2,strategy.getRank(5));
        assertEquals(3,strategy.getRank(6));
        assertEquals(4,strategy.getRank(3));
    }
}