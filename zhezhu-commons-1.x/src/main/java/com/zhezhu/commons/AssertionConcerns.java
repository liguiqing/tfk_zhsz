package com.zhezhu.commons;

import com.google.common.base.Preconditions;

/**
 * 断言守卫者，用于变量验证
 *
 * @author Liguiqing
 * @since V3.0
 */

public class AssertionConcerns {

    /**
     * 通过对象的equals方法验证两个参数是否相等
     *
     * @param anObject1
     * @param anObject2
     * @param aMessage 不相等时抛出的信息 如果anObject1是null使用NullPointException封装;否则使用IllegalArgumentException封装
     */
    public static void assertArgumentEquals(Object anObject1, Object anObject2, String aMessage) {
        Preconditions.checkNotNull(anObject1,aMessage);
        Preconditions.checkArgument(anObject1.equals(anObject2),aMessage);
    }

    /**
     * 通过对象的equals方法验证两个参数是否不相等
     *
     * @param anObject1
     * @param anObject2
     * @param aMessage 相等时抛出的信息 如果anObject1是null使用NullPointException封装;否则使用IllegalArgumentException封装
     */
    public static void assertArgumentNotEquals(Object anObject1, Object anObject2, String aMessage) {
        Preconditions.checkNotNull(anObject1,aMessage);
        Preconditions.checkArgument(!anObject1.equals(anObject2),aMessage);
    }

    /**
     *验证参数是否为True
     * @param aBoolean
     * @param aMessage 被验证的参数为False时抛出的错误信息，使用IllegalArgumentException封装
     */
    public static void assertArgumentTrue(boolean aBoolean, String aMessage) {
        Preconditions.checkArgument(aBoolean,aMessage);
    }

    /**
     *验证参数是否为False
     * @param aBoolean
     * @param aMessage 被验证的参数为True时抛出的错误信息，使用IllegalArgumentException封装
     */
    public static void assertArgumentFalse(boolean aBoolean, String aMessage) {
        Preconditions.checkArgument(!aBoolean,aMessage);
    }

    /**
     * 验证String 参数是否小于规定的长度
     *
     * @param aString 如果String参数前后有空格会自动去除
     * @param aMaximum
     * @param aMessage 小于指定长度时抛出的信息，使用IllegalArgumentException封装
     */
    public static void assertArgumentLength(String aString, int aMaximum, String aMessage) {
        int length = aString.trim().length();
        Preconditions.checkArgument(length > aMaximum,aMessage);
    }

    /**
     * 验证String参数是否在指定范围内
     *
     * @param aString 如果String参数前后有空格会自动去除
     * @param aMinimum
     * @param aMaximum
     * @param aMessage 不在长度范围内是抛出的信息，使用IllegalArgumentException封装
     */
    public static void assertArgumentLength(String aString, int aMinimum, int aMaximum, String aMessage) {
        int length = aString.trim().length();
        Preconditions.checkArgument(length > aMinimum && length < aMaximum,aMessage);
    }

    /**
     *验证String参数是否不为空，计算长度时自动去除前后空格
     *1、为Null 时
     *2、去除空格后长度=0时
     * @param aString
     * @param aMessage 不在长度范围内是抛出的信息，使用IllegalArgumentException封装
     */
    public static void assertArgumentNotEmpty(String aString, String aMessage) {
        Preconditions.checkArgument( (aString != null && aString.trim().length() > 0),aMessage);
    }

    /**
     * 验证Object不为空
     *
     * @param anObject
     * @param aMessage 不在长度范围内是抛出的信息，使用NullPointException封装
     */
    public static void assertArgumentNotNull(Object anObject, String aMessage) {
        Preconditions.checkNotNull(anObject,aMessage);
    }

    /**
     * 验证Object是为空
     *
     * @param anObject
     * @param aMessage 不在长度范围内是抛出的信息，使用NullPointException封装
     */
    public static void assertArgumentNull(Object anObject, String aMessage) {
        Preconditions.checkArgument(anObject==null,aMessage);
    }

    /**
     * 验证double类型的参数是在指定范围内（闭区间）
     *
     * @param aValue
     * @param aMinimum 最小值
     * @param aMaximum 最大值
     * @param aMessage 不在指定范围内是抛出的信息，使用IllegalArgumentException封装
     */
    public static void assertArgumentRange(double aValue, double aMinimum, double aMaximum, String aMessage) {
        Preconditions.checkArgument(!(aValue < aMinimum || aValue > aMaximum),aMessage);
    }

    /**
     * 验证float类型的参数是在指定范围内（开区间）
     *
     * @param aValue
     * @param aMinimum 最小值
     * @param aMaximum 最大值
     * @param aMessage 不在指定范围内是抛出的信息，使用IllegalArgumentException封装
     */
    public static void assertArgumentRange(float aValue, float aMinimum, float aMaximum, String aMessage) {
        Preconditions.checkArgument(!(aValue < aMinimum || aValue > aMaximum),aMessage);
    }

    /**
     * 验证int类型的参数是在指定范围内（开区间）
     *
     * @param aValue
     * @param aMinimum 最小值
     * @param aMaximum 最大值
     * @param aMessage 不在指定范围内是抛出的信息，使用IllegalArgumentException封装
     */
    public static void assertArgumentRange(int aValue, int aMinimum, int aMaximum, String aMessage) {
        Preconditions.checkArgument(!(aValue < aMinimum || aValue > aMaximum),aMessage);
    }

    /**
     * 验证long类型的参数是在指定范围内（开区间）
     *
     * @param aValue
     * @param aMinimum 最小值
     * @param aMaximum 最大值
     * @param aMessage 不在指定范围内是抛出的信息，使用IllegalArgumentException封装
     */
    public static void assertArgumentRange(long aValue, long aMinimum, long aMaximum, String aMessage) {
        Preconditions.checkArgument(!(aValue < aMinimum || aValue > aMaximum),aMessage);
    }

    /**
     * 验证参数状态是否为False
     *
     * @param aBoolean
     * @param aMessage 状态为True的信息，使用IllegalStateException封装
     */
    public static void assertStateFalse(boolean aBoolean, String aMessage) {
        Preconditions.checkState(!aBoolean,aMessage);
    }

    /**
     * 验证参数状态是否为True
     *
     * @param aBoolean
     * @param aMessage 状态为False的信息，使用IllegalStateException封装
     */
    public static void assertStateTrue(boolean aBoolean, String aMessage) {
        Preconditions.checkState(aBoolean,aMessage);
    }
}
