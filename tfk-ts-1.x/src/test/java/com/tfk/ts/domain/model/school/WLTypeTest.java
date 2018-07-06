package com.tfk.ts.domain.model.school;

import com.tfk.ts.domain.model.school.common.WLType;
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

public class WLTypeTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testFromName()throws Exception{
        WLType w = WLType.Liberal;
        WLType l = WLType.fromName("Liberal");
        WLType n = WLType.fromName("None");
        WLType s = WLType.fromName("Science");

        assertTrue(w == l);
        assertTrue(n == WLType.None);
        assertTrue(s == WLType.Science);

        assertTrue(l.getValue() == 1);
        assertTrue(s.getValue() == 2);
        assertTrue(n.getValue() == 0);
    }

    @Test
    public void testNoneName()throws Exception{
        thrown.expect(WLTypeNotFondException.class);
        thrown.expectMessage("错误的文理分类：df" );
        WLType none1 = WLType.fromName("df");
    }

    @Test
    public void testSpace()throws Exception{
        thrown.expect(WLTypeNotFondException.class);
        thrown.expectMessage("错误的文理分类：" );
        WLType none1 = WLType.fromName("");
    }

    @Test
    public void testNull()throws Exception{
        thrown.expect(WLTypeNotFondException.class);
        thrown.expectMessage("错误的文理分类：" );
        WLType none1 = WLType.fromName(null);
    }
}