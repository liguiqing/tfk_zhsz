/*
 * Copyright (c) 2016,2018, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.commons.util;

import java.text.NumberFormat;

/**
 * 数字处理工具
 *
 * @author Liguiqing
 * @since V3.0
 */

public class NumberUtilWrapper {

    /**
     * 两数相除后，转换为百分数
     * @param x 除数
     * @param y 被除数
     * @param scale 百分化后保留小数点位数
     * @return n.scale%
     */
    public static String formattedDecimalToPercentage(double x, double y,int scale) {
        double rate = rate(x,y);
        return formattedDecimalToPercentage(rate,scale);
    }

    /**
     * 将数字转换为百分数
     * @param decimal 除数
     * @param scale 百分化后保留小数点位数
     * @return n.scale%
     */
    public static String formattedDecimalToPercentage(double decimal,int scale) {
        if(scale < 1)
            scale = 1;

        NumberFormat nt = NumberFormat.getPercentInstance();
        nt.setMinimumFractionDigits(scale);
        return nt.format(decimal);
    }

    /**
     * 计算两数和比率
     * @param x
     * @param y
     * @return
     */
    public static double rate(double x,double y){
        if(y==0)
            return 0d;

        return (x*1d)/(y*1d);
    }

    /**
     * 生产两long类型数据间的随机数
     *
     * @param x
     * @param y
     * @return
     */
    public static long randomBetween(long x,long y){
        long rtn = x + (long) (Math.random() * (y - x));
        if (rtn == x || rtn == y) {
            return randomBetween(x, y);
        }
        return rtn;
    }

    /**
     * 生产两int类型数据间的随机数
     *
     * @param x
     * @param y
     * @return
     */
    public static int randomBetween(int x,int y){
        int rtn = x + (int) (Math.random() * (y - x));
        if (rtn == x || rtn == y) {
            return randomBetween(x, y);
        }
        return rtn;
    }

    /**
     * 生产两float类型数据间的随机数
     *
     * @param x
     * @param y
     * @return
     */
    public static float randomBetween(float x,float y){
        float rtn = x + (float) (Math.random() * (y - x));
        if (rtn == x || rtn == y) {
            return randomBetween(x, y);
        }
        return rtn;
    }
    /**
     * 生产两double类型数据间的随机数
     *
     * @param x
     * @param y
     * @return
     */
    public static double randomBetween(double x,double y){
        double rtn = x + (double) (Math.random() * (y - x));
        if (rtn == x || rtn == y) {
            return randomBetween(x, y);
        }
        return rtn;
    }

}