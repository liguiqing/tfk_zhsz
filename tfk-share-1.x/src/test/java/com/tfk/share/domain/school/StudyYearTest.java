/*
 * Copyright (c) 2016,2018, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.share.domain.school;

import com.tfk.commons.util.DateUtilWrapper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class StudyYearTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testConstructor()throws Exception{
        StudyYear sy = new StudyYear(1, 2);
    }

    @Test
    public void testNow()throws Exception{
        StudyYear years = StudyYear.now();
        Date now = DateUtilWrapper.now();
        int thisYear = DateUtilWrapper.thisYear();
        int nextYear = DateUtilWrapper.nextYear(DateUtilWrapper.now());
        int prevYear = DateUtilWrapper.prevYear(now);

        int month = DateUtilWrapper.month(now);
        if(month < 8){
            assertEquals(years.yearStarts(), prevYear);
            assertEquals(years.yearEnds(), thisYear);
        }else{
            assertEquals(years.yearStarts(), thisYear);
            assertEquals(years.yearEnds(), nextYear);
        }
    }

    @Test
    public void testStringOf()throws Exception{
        StudyYear years = StudyYear.now();
        Date now = DateUtilWrapper.now();
        int thisYear = DateUtilWrapper.thisYear();
        int nextYear = DateUtilWrapper.nextYear(DateUtilWrapper.now());
        int prevYear = DateUtilWrapper.prevYear(now);

        int month = DateUtilWrapper.month(now);
        if(month < 8){
            assertEquals(years.stringOf(), prevYear+"-"+thisYear);
        }else{
            assertEquals(years.stringOf(), thisYear+"-"+nextYear);
        }
    }

    @Test
    public void testConstructorYearStartsUnexpected()throws Exception{
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("学年起始年度不能大于终止年度" );
        StudyYear sy = new StudyYear(1, 1);
    }

    @Test
    public void testConstructorYearEndsUnexpected()throws Exception{
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("学年起始年度与终止年度不匹配" );
        StudyYear sy = new StudyYear(1, 3);
    }

    @Test
    public void testConstructorYearEndsUnexpected2()throws Exception{
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("学年起始年度不能大于终止年度" );
        StudyYear sy = new StudyYear(4, 3);
    }
}