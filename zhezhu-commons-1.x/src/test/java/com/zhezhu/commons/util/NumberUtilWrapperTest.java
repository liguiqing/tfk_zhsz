package com.zhezhu.commons.util;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Copyright (c) 2016,$today.year, 深圳市天定康科技有限公司
 **/
public class NumberUtilWrapperTest {

    @Test
    public void formattedDecimalToPercentage() {
    }

    @Test
    public void formattedDecimalToPercentage1() {
    }

    @Test
    public void rate() {
    }

    @Test
    public void randomBetweenLong() {
        for(int i=100;i<0;i--){
            long x=1;
            long y=10;
            long v = NumberUtilWrapper.randomBetween(1, 10);
            assertTrue(v >= x);
            assertTrue(v <= y);
        }
    }

    @Test
    public void randomBetweenInt() {
        for(int i=100;i<0;i--){
            int x=1;
            int y=10;
            int v = NumberUtilWrapper.randomBetween(1, 10);
            assertTrue(v >= x);
            assertTrue(v <= y);
        }
    }


    @Test
    public void randomBetweenFloat() {
        for(int i=100;i<0;i--){
            float x=1;
            float y=10;
            float v = NumberUtilWrapper.randomBetween(1, 10);
            assertTrue(v >= x);
            assertTrue(v <= y);
        }
    }

    @Test
    public void randomBetweenDouble() {
        for(int i=100;i<0;i--){
            double x=1;
            double y=10;
            double v = NumberUtilWrapper.randomBetween(1, 10);
            assertTrue(v >= x);
            assertTrue(v <= y);
        }
    }
}