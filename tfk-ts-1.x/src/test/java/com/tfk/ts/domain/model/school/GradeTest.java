package com.tfk.ts.domain.model.school;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Liguiqing
 * @since V3.0
 */

@RunWith(PowerMockRunner.class)
public class GradeTest {

    @Test
    public void testNext()throws Exception{
        GradeNameGenerateStrategy nameGenerateStrategy = mock(GradeNameGenerateStrategy.class);
        Grade g1 = new Grade("一年级",GradeLevel.One,new StudyYear("2017-2018"));
        when(nameGenerateStrategy.genGradeName(GradeLevel.Two)).thenReturn("二年级");
        Grade g2 = g1.next(nameGenerateStrategy);
        assertEquals(g2.name(),"二年级");
        assertEquals(g2.year().year(),"2018-2019");
        assertTrue(g2.seq()==GradeLevel.Two);
        assertEquals(g1.year().year(),"2017-2018");
        assertEquals(g1.name(),"一年级");
        assertTrue(g1.seq()==GradeLevel.One);

        when(nameGenerateStrategy.genGradeName(GradeLevel.Three)).thenReturn("三年级");
        Grade g3 = g2.next(nameGenerateStrategy);
        assertEquals(g2.name(),"二年级");
        assertEquals(g2.year().year(),"2018-2019");
        assertEquals(g3.name(),"三年级");
        assertEquals(g3.year().year(),"2019-2020");

        when(nameGenerateStrategy.genGradeName(GradeLevel.Eleven)).thenReturn("高二");
        Grade g10 = new Grade("高一",GradeLevel.Ten,new StudyYear("2017-2018"));
        Grade g11 = g10.next(nameGenerateStrategy);
        assertEquals(g11.name(),"高二");
        assertEquals(g11.year().year(),"2018-2019");
        assertEquals(g10.name(),"高一");
        assertEquals(g10.year().year(),"2017-2018");

        when(nameGenerateStrategy.genGradeName(GradeLevel.Twelve)).thenReturn("高三").thenReturn("高三");
        Grade g12 = g11.next(nameGenerateStrategy);
        assertEquals(g11.name(),"高二");
        assertEquals(g11.year().year(),"2018-2019");
        assertEquals(g12.name(),"高三");
        assertEquals(g12.year().year(),"2019-2020");

        Grade g12_ = g12.next(nameGenerateStrategy);
        assertEquals(g12.name(),"高三");
        assertEquals(g12.year().year(),"2019-2020");
        assertEquals(g12,g12_);
    }

    @Test
    public void testEqualsAndHashcode()throws Exception{
        Grade g1 = new Grade("一年级",GradeLevel.One,new StudyYear("2017-2018"));
        Grade g11 = new Grade("一年级",GradeLevel.One,new StudyYear("2017-2018"));
        assertTrue(g11.equals(g1));
        assertTrue(g1.hashCode() == g1.hashCode());

        GradeNameGenerateStrategy nameGenerateStrategy = mock(GradeNameGenerateStrategy.class);
        when(nameGenerateStrategy.genGradeName(GradeLevel.Two)).thenReturn("二年级");
        Grade g2 = g1.next(nameGenerateStrategy);

        assertFalse(g11.equals(g2));
        assertFalse(g11.hashCode() == g2.hashCode());

    }
}