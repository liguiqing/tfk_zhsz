package com.tfk.share.domain.common;

import com.tfk.commons.util.DateUtilWrapper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Date;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class PeriodTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testConstructor()throws  Exception{
        Date starts = DateUtilWrapper.toDate("2018-06-01","yyyy-MM-dd");
        Date ends = DateUtilWrapper.toDate("2018-06-02","yyyy-MM-dd");
        new Period(starts,ends);

        starts = DateUtilWrapper.toDate("2018-06-01","yyyy-MM-dd");
        ends = DateUtilWrapper.toDate("2018-07-02","yyyy-MM-dd");
        new Period(starts,ends);

        starts = DateUtilWrapper.toDate("2018-06-01","yyyy-MM-dd");
        ends = DateUtilWrapper.toDate("2018-06-01","yyyy-MM-dd");
        new Period(starts,ends);

        starts = DateUtilWrapper.toDate("2018-05-31","yyyy-MM-dd");
        ends = DateUtilWrapper.toDate("2018-06-01","yyyy-MM-dd");
        new Period(starts,ends);

        starts = DateUtilWrapper.toDate("2017-12-31","yyyy-MM-dd");
        ends = DateUtilWrapper.toDate("2018-01-01","yyyy-MM-dd");
        new Period(starts,ends);
    }

    @Test
    public void testStartsLgEnds()throws Exception{
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("起始时间不能大于终止时间" );

        Date starts = DateUtilWrapper.toDate("2018-06-03","yyyy-MM-dd");
        Date ends = DateUtilWrapper.toDate("2018-06-02","yyyy-MM-dd");
        new Period(starts,ends);
    }

    @Test
    public void testStartsLgEndsMonth()throws Exception{
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("起始时间不能大于终止时间" );

        Date starts = DateUtilWrapper.toDate("2018-06-01","yyyy-MM-dd");
        Date ends = DateUtilWrapper.toDate("2018-05-31","yyyy-MM-dd");
        new Period(starts,ends);
    }

    @Test
    public void testStartsLgEndsYear()throws Exception{
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("起始时间不能大于终止时间" );

        Date starts = DateUtilWrapper.toDate("2018-01-01","yyyy-MM-dd");
        Date ends = DateUtilWrapper.toDate("2017-12-31","yyyy-MM-dd");
        new Period(starts,ends);
    }

}