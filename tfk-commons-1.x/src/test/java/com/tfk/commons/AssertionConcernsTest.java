/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.commons;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * AssertionConcerns Test
 *
 * @author Liguiqing
 * @since V3.0
 */

public class AssertionConcernsTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test(expected = IllegalArgumentException.class)
    public void testEqualsAssertion()throws Exception{
        AssertionConcerns.assertArgumentEquals(new A("a"),new A("a"),"");
        AssertionConcerns.assertArgumentEquals(1,1,"");
        AssertionConcerns.assertArgumentEquals(new A("a"),new A("b"),"两个对象不相等");
    }

    @Test
    public void testNotEqualsAssertion()throws Exception{
        AssertionConcerns.assertArgumentNotEquals(new A("a"),new A("A"),"");
        AssertionConcerns.assertArgumentNotEquals(1,2,"");

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("两个对象相等");
        AssertionConcerns.assertArgumentNotEquals(new A("a"),new A("a"),"两个对象相等");

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("两个数据相等");
        AssertionConcerns.assertArgumentNotEquals(1,1,"两个数据相等");
    }

    @Test
    public void testEqualsAssertionNotNull()throws Exception{
        AssertionConcerns.assertArgumentNotNull(new A(""),"两个数据不相等");
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("对象为空");
        AssertionConcerns.assertArgumentNotNull(null,"对象为空");
    }

    @Test
    public void testEqualsAssertionNull()throws Exception{
        AssertionConcerns.assertArgumentNull(null,"对象不为空");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("对象不为空");
        AssertionConcerns.assertArgumentNull(new A(""),"对象不为空");
    }


    @Test
    public void testEqualsAssertionFalse()throws Exception{
        AssertionConcerns.assertArgumentNotEquals(new A(""),new A("a"),"两个对象不相等");

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("两个对象相等");
        AssertionConcerns.assertArgumentNotEquals(new A("A"),new A("A"),"两个对象相等");
    }

    @Test
    public void testAssertArgumentFalse()throws Exception{
        AssertionConcerns.assertArgumentFalse(false,"");

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("伪");
        AssertionConcerns.assertArgumentFalse(true,"伪");
    }

    @Test
    public void testAssertArgumentTrue()throws Exception{
        AssertionConcerns.assertArgumentTrue(true,"");

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("真");
        AssertionConcerns.assertArgumentTrue(false,"真");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAssertArgumentLength()throws Exception{
        AssertionConcerns.assertArgumentLength("",1,"");
        AssertionConcerns.assertArgumentLength("",0,"");
        AssertionConcerns.assertArgumentLength(" ",0,"");
        AssertionConcerns.assertArgumentLength("1 ",1,"");
        AssertionConcerns.assertArgumentLength(" 1",1,"");
        AssertionConcerns.assertArgumentLength(" 1 ",1,"");
        AssertionConcerns.assertArgumentLength("1",0,"");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAssertArgumentLengthMinMax()throws Exception{
        AssertionConcerns.assertArgumentLength("",0,1,"");
        AssertionConcerns.assertArgumentLength("1",0,1,"");
        AssertionConcerns.assertArgumentLength("12345",2,6,"");
        AssertionConcerns.assertArgumentLength("1",0,0,"");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAssertArgumentNotEmpty()throws Exception{
        AssertionConcerns.assertArgumentNotEmpty("123","");
        AssertionConcerns.assertArgumentNotEmpty("  ","");
        AssertionConcerns.assertArgumentNotEmpty("","");
        AssertionConcerns.assertArgumentNotEmpty(null,"");
    }

    @Test(expected = NullPointerException.class)
    public void testAssertArgumentNotNull()throws Exception{
        AssertionConcerns.assertArgumentNotNull("","");
        AssertionConcerns.assertArgumentNotNull(new A(""),"");
        AssertionConcerns.assertArgumentNotNull(null,"");
    }

    @Test
    public void testAssertArgumentRangeFloat()throws Exception{
        AssertionConcerns.assertArgumentRange(1.0f,0.5f,1.0f,"");
        AssertionConcerns.assertArgumentRange(0.5f,0.5f,1.0f,"");
        AssertionConcerns.assertArgumentRange(0.6f,0.5f,1.0f,"");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("aaa");
        AssertionConcerns.assertArgumentRange(1.1f,0.5f,1.0f,"aaa");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("bb");
        AssertionConcerns.assertArgumentRange(0.49f,0.5f,1.0f,"bb");
    }

    @Test
    public void testAssertArgumentRangeDouble()throws Exception{
        AssertionConcerns.assertArgumentRange(1.0d,0.5d,1.0d,"");
        AssertionConcerns.assertArgumentRange(0.5d,0.5d,1.0d,"");
        AssertionConcerns.assertArgumentRange(0.6d,0.5d,1.0d,"");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("aaa");
        AssertionConcerns.assertArgumentRange(1.1d,0.5d,1.0d,"aaa");
    }

    @Test
    public void testAssertArgumentRangeInt()throws Exception{
        AssertionConcerns.assertArgumentRange(1,0,1,"");
        AssertionConcerns.assertArgumentRange(0,0,1,"");
        AssertionConcerns.assertArgumentRange(1,0,2,"");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("aaa");
        AssertionConcerns.assertArgumentRange(1,0,0,"aaa");
        thrown.expect(IllegalArgumentException.class);
    }

    @Test
    public void testAssertArgumentRangeLong()throws Exception{
        AssertionConcerns.assertArgumentRange(1l,0l,1l,"");
        AssertionConcerns.assertArgumentRange(0l,0l,1l,"");
        AssertionConcerns.assertArgumentRange(1l,0l,2l,"");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("aaa");
        AssertionConcerns.assertArgumentRange(1l,0l,0l,"aaa");
        thrown.expect(IllegalArgumentException.class);
    }

    @Test
    public void testAssertStateFalse()throws Exception{
        AssertionConcerns.assertStateFalse(false,"");
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("aaa");
        AssertionConcerns.assertStateFalse(true,"aaa");
    }

    @Test
    public void testAssertStateTrue()throws Exception{
        AssertionConcerns.assertStateTrue(true,"");
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("aaa");
        AssertionConcerns.assertStateTrue(false,"aaa");
    }

    private static class A{
        String s = "";
        protected  A(String s){
            this.s = s;
        }

        public boolean equals(Object o){
            if(o != null && o instanceof  A){
                A a = (A)o;
                return this.s.equals(a.s);
            }
            return  false;
        }
    }
}