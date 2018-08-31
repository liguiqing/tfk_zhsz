/*
 * Copyright (c) 2016,2017, zhezhu All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.zhezhu.commons.util;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * DateUtilWrapper Test
 *
 * @author Liguiqing
 * @since V3.0
 */

public class DateUtilWrapperTest {

    @Test
    public void testToday()throws  Exception{
        Date today1 = Calendar.getInstance().getTime();
        Date today2 = DateUtilWrapper.today();
        assertEquals(today1.getYear(),today2.getYear());
        assertEquals(today1.getMonth(),today2.getMonth());
        assertEquals(today1.getDay(),today2.getDay());
    }

    @Test
    public void testYestoday()throws  Exception{
        Calendar today = Calendar.getInstance();
        today.set(Calendar.DAY_OF_MONTH,today.get(Calendar.DAY_OF_MONTH)-1);
        Date yestoday1 = today.getTime();
        Date yestoday2 = DateUtilWrapper.yestoday();
        assertEquals(yestoday1.getYear(),yestoday2.getYear());
        assertEquals(yestoday1.getMonth(),yestoday2.getMonth());
        assertEquals(yestoday1.getDay(),yestoday2.getDay());
    }

    @Test
    public void testTomorrow()throws  Exception{
        Calendar today = Calendar.getInstance();
        today.set(Calendar.DAY_OF_MONTH,today.get(Calendar.DAY_OF_MONTH)+1);
        Date tomorrow1 = today.getTime();
        Date tomorrow2 = DateUtilWrapper.tomorrow();
        assertEquals(tomorrow1.getYear(),tomorrow2.getYear());
        assertEquals(tomorrow1.getMonth(),tomorrow2.getMonth());
        assertEquals(tomorrow1.getDay(),tomorrow2.getDay());
    }

    @Test
    public void testGreaterThan() throws Exception{
        Date today = DateUtilWrapper.today();
        Date tomorrow = DateUtilWrapper.tomorrow();
        Date yestoday = DateUtilWrapper.yestoday();
        assertTrue(DateUtilWrapper.largeThan(tomorrow,today));
        assertTrue(DateUtilWrapper.largeThan(today,yestoday));
        assertFalse(DateUtilWrapper.largeThan(tomorrow,tomorrow));
        assertFalse(DateUtilWrapper.largeThan(yestoday,yestoday));
        assertFalse(DateUtilWrapper.largeThan(today,tomorrow));
        assertFalse(DateUtilWrapper.largeThan(yestoday,today));
    }

    @Test
    public void testToString()throws Exception{
        Calendar c = Calendar.getInstance();
        c.set(2017,0,1,1,0,0);
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        String f = sd.format(c.getTime());
        assertEquals("2017-01",DateUtilWrapper.toString(c.getTime(),"yyyy-MM"));
        assertEquals("2017-01-01",DateUtilWrapper.toString(c.getTime(),"yyyy-MM-dd"));
        assertEquals("20170101",DateUtilWrapper.toString(c.getTime(),"yyyyMMdd"));
        assertEquals("201701",DateUtilWrapper.toString(c.getTime(),"yyyyMM"));
        assertEquals("20170101 01:00:00",DateUtilWrapper.toString(c.getTime(),"yyyyMMdd HH:mm:ss"));
        assertEquals("2017-01-01 01:00:00",DateUtilWrapper.toString(c.getTime(),"yyyy-MM-dd HH:mm:ss"));
    }

    @Test
    public void testGreaterThanYYMMDD()throws Exception{
        Date today = DateUtilWrapper.today();
        Date tomorrow = DateUtilWrapper.tomorrow();
        Date yestoday = DateUtilWrapper.yestoday();
        assertTrue(DateUtilWrapper.largeThanYYMMDD(today,yestoday));
        assertTrue(DateUtilWrapper.largeThanYYMMDD(tomorrow,today));
        assertTrue(DateUtilWrapper.largeThanYYMMDD(tomorrow,yestoday));
        assertFalse(DateUtilWrapper.largeThanYYMMDD(today,today));
    }

    @Test
    public void testEqualsYYMMDD()throws Exception{
        Date today = DateUtilWrapper.today();
        Date tomorrow = DateUtilWrapper.tomorrow();
        Date yestoday = DateUtilWrapper.yestoday();
        assertFalse(DateUtilWrapper.equalsYYMMDD(today,yestoday));
        assertFalse(DateUtilWrapper.equalsYYMMDD(tomorrow,today));
        assertFalse(DateUtilWrapper.equalsYYMMDD(tomorrow,yestoday));
        assertTrue(DateUtilWrapper.equalsYYMMDD(today,today));
        assertTrue(DateUtilWrapper.equalsYYMMDD(tomorrow,tomorrow));
        assertTrue(DateUtilWrapper.equalsYYMMDD(yestoday,yestoday));
    }

    @Test
    public void testToDate()throws Exception{

        Date dd1 = DateUtilWrapper.toDate("2017", "yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(dd1);
        assertEquals(c.get(Calendar.YEAR),2017);


        Date dd2 = DateUtilWrapper.toDate("1899", "yyyy");
        Calendar c2 = Calendar.getInstance();
        c2.setTime(dd2);
        assertEquals(c2.get(Calendar.YEAR),1899);

        Date dd3 = DateUtilWrapper.toDate("1", "yyyy");
        Calendar c3 = Calendar.getInstance();
        c3.setTime(dd3);
        assertEquals(c3.get(Calendar.YEAR),1);

        Date dd4 = DateUtilWrapper.toDate("2017-01", "yyyy-MM");
        Calendar c4 = Calendar.getInstance();
        c4.setTime(dd4);
        assertEquals(c4.get(Calendar.MONTH),0);

        Date dd5 = DateUtilWrapper.toDate("2017-12", "yyyy-MM");
        Calendar c5 = Calendar.getInstance();
        c5.setTime(dd5);
        assertEquals(c5.get(Calendar.MONTH),11);
    }

    @Test
    public void testMonth()throws Exception{
        Calendar c = Calendar.getInstance();
        c.set(2017,0,1,1,0,0);
        int m1 = DateUtilWrapper.month(c.getTime());
        assertEquals(m1,1);

        c.set(2017,11,1,1,0,0);
        int m2 = DateUtilWrapper.month(c.getTime());
        assertEquals(m2,12);

        c.set(2017,6,1,1,0,0);
        int m3 = DateUtilWrapper.month(c.getTime());
        assertEquals(m3,7);
    }

    @Test
    public void testIncreamentSecondTo()throws Exception{
        Date now = DateUtilWrapper.now();

        Date lgNow = DateUtilWrapper.addSecondTo(now,0);
        assertEquals(0*1000,(lgNow.getTime() - now.getTime()));

        lgNow = DateUtilWrapper.addSecondTo(now,-1);
        assertEquals(0*1000,(lgNow.getTime() - now.getTime()));

        lgNow = DateUtilWrapper.addSecondTo(now,-105);
        assertEquals(0*1000,(lgNow.getTime() - now.getTime()));

        lgNow = DateUtilWrapper.addSecondTo(now,20);
        assertEquals(20*1000,(lgNow.getTime() - now.getTime()));

        lgNow = DateUtilWrapper.addSecondTo(now,1);
        assertEquals(1*1000,(lgNow.getTime() - now.getTime()));

        Date date = DateUtilWrapper.toDate("2018-06-29 12:12:12","yyyy-MM-dd HH:mm:ss");
        lgNow = DateUtilWrapper.addSecondTo(date,1);
        String s = DateUtilWrapper.toString(lgNow, "yyyy-MM-dd HH:mm:ss");
        assertEquals("2018-06-29 12:12:13",s);

        date = DateUtilWrapper.toDate("2018-06-29 12:12:51","yyyy-MM-dd HH:mm:ss");
        lgNow = DateUtilWrapper.addSecondTo(date,8);
        s = DateUtilWrapper.toString(lgNow, "yyyy-MM-dd HH:mm:ss");
        assertEquals("2018-06-29 12:12:59",s);

        date = DateUtilWrapper.toDate("2018-06-29 12:12:51","yyyy-MM-dd HH:mm:ss");
        lgNow = DateUtilWrapper.addSecondTo(date,9);
        s = DateUtilWrapper.toString(lgNow, "yyyy-MM-dd HH:mm:ss");
        assertEquals("2018-06-29 12:13:00",s);

        date = DateUtilWrapper.toDate("2018-06-29 12:12:51","yyyy-MM-dd HH:mm:ss");
        lgNow = DateUtilWrapper.addSecondTo(date,60);
        s = DateUtilWrapper.toString(lgNow, "yyyy-MM-dd HH:mm:ss");
        assertEquals("2018-06-29 12:13:51",s);

        date = DateUtilWrapper.toDate("2018-06-29 12:12:51","yyyy-MM-dd HH:mm:ss");
        lgNow = DateUtilWrapper.addSecondTo(date,61);
        s = DateUtilWrapper.toString(lgNow, "yyyy-MM-dd HH:mm:ss");
        assertEquals("2018-06-29 12:13:52",s);

        date = DateUtilWrapper.toDate("2018-06-29 12:12:51","yyyy-MM-dd HH:mm:ss");
        lgNow = DateUtilWrapper.addSecondTo(date,61);
        s = DateUtilWrapper.toString(lgNow, "yyyy-MM-dd HH:mm:ss");
        assertEquals("2018-06-29 12:13:52",s);

        date = DateUtilWrapper.toDate("2018-06-29 12:12:51","yyyy-MM-dd HH:mm:ss");
        lgNow = DateUtilWrapper.addSecondTo(date,3600);
        s = DateUtilWrapper.toString(lgNow, "yyyy-MM-dd HH:mm:ss");
        assertEquals("2018-06-29 13:12:51",s);

        date = DateUtilWrapper.toDate("2018-06-29 12:12:51","yyyy-MM-dd HH:mm:ss");
        lgNow = DateUtilWrapper.addSecondTo(date,3601);
        s = DateUtilWrapper.toString(lgNow, "yyyy-MM-dd HH:mm:ss");
        assertEquals("2018-06-29 13:12:52",s);

        date = DateUtilWrapper.toDate("2018-06-29 12:12:51","yyyy-MM-dd HH:mm:ss");
        lgNow = DateUtilWrapper.addSecondTo(date,3599);
        s = DateUtilWrapper.toString(lgNow, "yyyy-MM-dd HH:mm:ss");
        assertEquals("2018-06-29 13:12:50",s);

        date = DateUtilWrapper.toDate("2018-06-29 23:59:59","yyyy-MM-dd HH:mm:ss");
        lgNow = DateUtilWrapper.addSecondTo(date,1);
        s = DateUtilWrapper.toString(lgNow, "yyyy-MM-dd HH:mm:ss");
        assertEquals("2018-06-30 00:00:00",s);

        date = DateUtilWrapper.toDate("2018-06-29 23:59:59","yyyy-MM-dd HH:mm:ss");
        lgNow = DateUtilWrapper.addSecondTo(date,2);
        s = DateUtilWrapper.toString(lgNow, "yyyy-MM-dd HH:mm:ss");
        assertEquals("2018-06-30 00:00:01",s);

        date = DateUtilWrapper.toDate("2018-06-29 23:59:59","yyyy-MM-dd HH:mm:ss");
        lgNow = DateUtilWrapper.addSecondTo(date,60);
        s = DateUtilWrapper.toString(lgNow, "yyyy-MM-dd HH:mm:ss");
        assertEquals("2018-06-30 00:00:59",s);
    }

    @Test
    public void toDate()throws Exception{
        Date date = DateUtilWrapper.toDate("2018-06-29 12:12:12","yyyy-MM-dd HH:mm:ss");
        //Date newDate = DateUtilWrapper.toDate(date,"yyyy-MM-dd");

    }
}