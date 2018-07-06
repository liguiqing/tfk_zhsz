package com.tfk.ts.domain.model.school;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class StudyYearTest {

    @Test
    public void testYearOfNow()throws Exception{
        StudyYear year = StudyYear.yearOfNow();
        Calendar now = Calendar.getInstance();
        int m = now.get(Calendar.MONTH);
        String s2 = year.year().split("-")[1];
        String s1 = year.year().split("-")[0];
        if(m<8 && m>0){
            assertEquals(now.get(Calendar.YEAR)+"",s2 );
            assertEquals(now.get(Calendar.YEAR)-1+"",s1 );
        }else{
            assertEquals(now.get(Calendar.YEAR)+"",s1 );
            assertEquals(now.get(Calendar.YEAR)+1+"",s2 );
        }
    }

    @Test
    public void testNextYear()throws  Exception{
        StudyYear year = new StudyYear(2017, 2018);
        StudyYear next = year.nextYear();
        assertEquals(next.year(),"2018-2019");
        assertTrue(next.startsYear() == 2018);
        assertTrue(next.endsYear() == 2019);
        assertEquals(year.toString(),"StudyYear{starsDate=2017, endsDate=2018}");

        year = new StudyYear("2017-2018");
        next = year.nextYear();
        assertEquals(next.year(),"2018-2019");
        assertTrue(next.startsYear() == 2018);
        assertTrue(next.endsYear() == 2019);

        assertEquals(year.toString(),"StudyYear{starsDate=2017, endsDate=2018}");
    }

    @Test
    public void testEqualsAndHashcode()throws Exception{
        StudyYear year = new StudyYear(2017, 2018);
        StudyYear next = year.nextYear();
        assertEquals(year,new StudyYear(2017, 2018));
        assertEquals(next,new StudyYear(2018, 2019));
        assertNotEquals(next,year);
        assertNotEquals(next,null);
        assertNotEquals(next,new Object());

        assertEquals(year.hashCode(),new StudyYear(2017, 2018).hashCode());
        assertEquals(next.hashCode(),new StudyYear(2018, 2019).hashCode());
    }
}