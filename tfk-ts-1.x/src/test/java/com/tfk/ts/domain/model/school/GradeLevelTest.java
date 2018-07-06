package com.tfk.ts.domain.model.school;

import com.tfk.ts.domain.model.school.common.WLTypeNotFondException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class GradeLevelTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void testNext()throws Exception{
        GradeLevel one = GradeLevel.One;
        assertTrue(one.next() == GradeLevel.Two);
        assertTrue(one.next().next() == GradeLevel.Three);
        assertTrue(one.next().next().next() == GradeLevel.Four);
        assertTrue(one.next().next().next().next() == GradeLevel.Five);
        assertTrue(one.next().next().next().next().next() == GradeLevel.Six);
        assertTrue(one.next().next().next().next().next().next() == GradeLevel.Seven);
        assertTrue(one.next().next().next().next().next().next().next() == GradeLevel.Eight);
        assertTrue(one.next().next().next().next().next().next().next().next() == GradeLevel.Nine);
        assertTrue(one.next().next().next().next().next().next().next().next().next() == GradeLevel.Ten);
        assertTrue(one.next().next().next().next().next().next().next().next().next().next() == GradeLevel.Eleven);
        assertTrue(one.next().next().next().next().next().next().next().next().next().next().next() == GradeLevel.Twelve);

        assertNull(one.next().next().next().next().next().next().next().next().next().next().next().next() );
    }

    @Test
    public void testFromLevel()throws Exception{
        GradeLevel one = GradeLevel.fromLevel(1);
        assertTrue(one == GradeLevel.One);

        GradeLevel four = GradeLevel.fromLevel(4);
        assertTrue(four == GradeLevel.Four);

        GradeLevel ten = GradeLevel.fromLevel(10);
        assertTrue(ten == GradeLevel.Ten);

        GradeLevel twelve = GradeLevel.fromLevel(12);
        assertTrue(twelve == GradeLevel.Twelve);

    }

    @Test
    public void test0() throws Exception{
        thrown.expect(GradeNotFoundException.class);
        thrown.expectMessage("0" );
        GradeLevel.fromLevel(0);
    }

    @Test
    public void test13() throws Exception{
        thrown.expect(GradeNotFoundException.class);
        thrown.expectMessage("13" );
        GradeLevel.fromLevel(13);
    }

    @Test
    public void  testFromName()throws Exception{
        GradeLevel one = GradeLevel.fromName("One");
        assertTrue(one == GradeLevel.One);

        GradeLevel four = GradeLevel.fromName("Four");
        assertTrue(four == GradeLevel.Four);

        GradeLevel ten = GradeLevel.fromName("Ten");
        assertTrue(ten == GradeLevel.Ten);

        GradeLevel twelve = GradeLevel.fromName("Twelve");
        assertTrue(twelve == GradeLevel.Twelve);

    }

    @Test
    public void testNotName()throws Exception{
        thrown.expect(GradeNotFoundException.class);
        thrown.expectMessage("Zero" );
        GradeLevel.fromName("Zero");

    }

    @Test
    public void testNull()throws Exception{
        thrown.expect(GradeNotFoundException.class);
        thrown.expectMessage("" );
        GradeLevel thirteen = GradeLevel.fromName(null);

    }
}