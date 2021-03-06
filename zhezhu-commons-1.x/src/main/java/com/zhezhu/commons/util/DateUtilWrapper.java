/*
 * Copyright (c) 2016,2017, zhezhu All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.zhezhu.commons.util;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import sun.util.resources.zh.TimeZoneNames_zh_CN;

import java.time.*;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间工具类包装器，使用到不同组件的时间日期处理部件
 * 如google
 *
 * @author Liguiqing
 * @since V3.0
 */

public class DateUtilWrapper {
    public final static Date Now = now();

    public final static Date Today = today();

    public final static Date Yesterday = yestoday();

    public final static Date Tomorrow = tomorrow();

    public final static int ThisWeek = LocalDate.now().get(WeekFields.ISO.weekOfYear());

    public final static int LastWeek = ThisWeek==1?54:ThisWeek-1;

    public final static int NextWeek = ThisWeek==54?1:ThisWeek+1;

    public final static int ThisYear = LocalDate.now().getYear();

    public final static int LastYear = LocalDate.now().getYear()-1;

    public final static int NextYear = LocalDate.now().getYear()+1;

    /**
     * 现在 yyyy-MM-dd HH:mm:sss
     *
     * @return {@link Date}
     */
    public static Date now() {
        return  Calendar.getInstance().getTime();
    }

    /**
     * 今天 yyyy-MM-dd
     *
     * @return
     */
    public static Date today() {
        return fromLocalDate(LocalDate.now());
    }

    /**
     * 明天
     *
     * @return
     */
    public static Date yestoday() {
        Date now = DateUtilWrapper.today();
        return DateUtilWrapper.prevDay(now);
    }

    /**
     * 昨天
     *
     * @return
     */
    public static Date tomorrow() {
        return fromLocalDate(LocalDate.now().plusDays(1));
    }

    /**
     * 某天的下一天
     *
     * @param date
     * @return
     */
    public static Date nextDay(Date date) {
        return org.apache.commons.lang3.time.DateUtils.addDays(date, 1);
    }

    /**
     * 某天的前一天
     *
     * @param date
     * @return
     */
    public static Date prevDay(Date date) {
        return org.apache.commons.lang3.time.DateUtils.addDays(date, -1);
    }


    /**
     * 今年的年份
     *
     * @return
     */
    public static int thisYear() {
        return DateUtilWrapper.year(DateUtilWrapper.today());
    }

    /**
     *某个日期的下一年份
     *
     * @param date
     * @return
     */
    public static int nextYear(Date date){
        return DateUtilWrapper.year(date) + 1;
    }

    /**
     * 某个日期的上一年份
     *
     * @param date
     * @return
     */
    public static int prevYear(Date date){
        return DateUtilWrapper.year(date) - 1;
    }

    /**
     * 某个日期的年份
     *
     * @param date
     * @return
     */
    public static int year(Date date){
        if(date == null)
            return LocalDate.now().getYear();

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    /**
     * 日期是否大于另外的日期。
     * 使用精确的时间进行比较
     *
     * @param aDate
     * @param otherDate
     * @return
     */
    public static boolean largeThan(Date aDate, Date otherDate){
        if(aDate == null || otherDate == null)
            return false;

        return aDate.compareTo(otherDate) > 0;
    }

    /**
     * 日期是否大于另外的日期
     * 取日期中的年月日进行比较
     *
     * @param aDate
     * @param otherDate
     * @return
     */
    public static boolean largeThanYYMMDD(Date aDate, Date otherDate){
        if(aDate == null )
            return false;
        if(otherDate == null)
            return true;

        String d1 = toString(aDate,"yyyyMMdd");
        String d2 = toString(otherDate,"yyyyMMdd");
        int id1 = Integer.valueOf(d1);
        int id2 = Integer.valueOf(d2);

        return (id1-id2) > 0;
    }

    /**
     * 日期是否大于等于另外的日期
     * 取日期中的年月日进行比较
     *
     * @param aDate
     * @param otherDate
     * @return
     */
    public static boolean lgeThanYYMMDD(Date aDate, Date otherDate){
        if(aDate == null )
            return false;
        if(otherDate == null)
            return true;

        String d1 = toString(aDate,"yyyyMMdd");
        String d2 = toString(otherDate,"yyyyMMdd");
        int id1 = Integer.valueOf(d1);
        int id2 = Integer.valueOf(d2);

        return (id1-id2) >= 0;
    }

    /**
     * 日期是否小于另外的日期。
     * 使用精确的时间进行比较
     *
     * @param aDate
     * @param otherDate
     * @return
     */
    public static boolean lessThan(Date aDate,Date otherDate){
        if(aDate == null || otherDate == null)
            return false;

        return aDate.compareTo(otherDate) < 0;
    }

    /**
     * 日期是否小于另外的日期
     * 取日期中的年月日进行比较
     *
     * @param aDate
     * @param otherDate
     * @return
     */
    public static boolean lessThanYYMMDD(Date aDate,Date otherDate){
        if(aDate == null || otherDate == null)
            return false;

        String d1 = toString(aDate,"yyyyMMdd");
        String d2 = toString(otherDate,"yyyyMMdd");
        int id1 = Integer.valueOf(d1);
        int id2 = Integer.valueOf(d2);

        return (id1-id2) < 0;
    }

    /**
     * 日期是否小于等于另外的日期
     * 取日期中的年月日进行比较
     *
     * @param aDate
     * @param otherDate
     * @return
     */
    public static boolean lseThanYYMMDD(Date aDate,Date otherDate){
        if(aDate == null || otherDate == null)
            return false;

        String d1 = toString(aDate,"yyyyMMdd");
        String d2 = toString(otherDate,"yyyyMMdd");
        int id1 = Integer.valueOf(d1);
        int id2 = Integer.valueOf(d2);

        return (id1-id2) <= 0;
    }


    /**
     * 日期是否与另外的日期相同
     * 取日期中的年月日进行比较
     *
     * @param aDate
     * @param otherDate
     * @return
     */
    public static boolean equalsYYMMDD(Date aDate,Date otherDate){
        if(aDate == null || otherDate == null)
            return false;

        String d1 = toString(aDate,"yyyyMMdd");
        String d2 = toString(otherDate,"yyyyMMdd");
        int id1 = Integer.valueOf(d1);
        int id2 = Integer.valueOf(d2);

        return (id1-id2) == 0;
    }

    /**
     * 日期格式化
     * @param aDate
     * @param pattern @see org.apache.commons.lang3.time.format
     * @return
     */
    public static String toString(Date aDate,String pattern){
        return DateFormatUtils.format(aDate,pattern);
    }

    /**
     * 字符串转换为日期
     * @param date
     * @return
     */
    public static Date toDate(String date,String... pattern){
        try {
           return  DateUtils.parseDate(date, pattern);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 将日期转换成新的格式日期
     * 如将 2018-01-01 12:58:58 -> 2018-01-01 12
     * @param date
     * @param pattern @see org.apache.commons.lang3.time.format
     * @return
     */
    public static Date toDate(Date date,String pattern){
        String sDate = toString(date, pattern);
        return toDate(sDate);
    }

    /**
     * 取日期的月份
     * @param aDate
     * @return
     */
    public static int month(Date aDate){
        Calendar c = Calendar.getInstance();
        c.setTime(aDate);
        return c.get(Calendar.MONTH)+1;
    }

    /**
     * 将时间增加second秒
     * 如果是负数，返回原时间；如果需要将时间减少n秒，请使用<code>subSecondTo</code>
     * @param date
     * @param second
     * @return
     */
    public static Date addSecondTo(Date date, int second){
        if(second < 0 )
            return date;
        long time = date.getTime();
        return new Date(time+second*1000);
    }

    /**
     * 将时间减少second秒
     * 如果是负数，返回原时间；如果需要将时间增加n秒，请使用<code>addSecondTo</code>
     * @param date
     * @param second
     * @return
     */
    public static Date subSecondTo(Date date, int second){
        if(second < 0 )
            return date;
        long time = date.getTime();
        return new Date(time - second*1000);
    }

    /**
     * 计算日期在一年中的周次
     *
     * @param date
     * @return
     */
    public static int weekOfYear(Date date){
        Calendar day =  Calendar.getInstance();
        day.setTime(date);
        return day.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 计算当天是本周的第几天
     *
     * @return
     */
    public static int dayOfWeek(){
        LocalDate today = LocalDate.now();
        return today.get(ChronoField.DAY_OF_WEEK);
    }

    /**
     *  取时间所在周第一天
     * @param date
     * @return
     */
    public static Date getStartDayOfWeek(Date date) {
        TemporalField fieldISO = WeekFields.of(Locale.CHINA).dayOfWeek();
        LocalDate localDate = LocalDate.from(toLocalDate(date));
        localDate.with(fieldISO, 1);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 取时间所在周最后一天
     *
     * @param date
     * @return
     */
    public static Date getEndDayOfWeek(Date date) {
        TemporalField fieldISO = WeekFields.of(Locale.CHINA).dayOfWeek();
        LocalDate localDate = LocalDate.from(toLocalDate(date));
        localDate.with(fieldISO, 7);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).plusDays(1L).minusNanos(1L).toInstant());
    }



    /**
     * 将日期转换到 LocalDate
     * @param date
     * @return
     */
    public static LocalDate toLocalDate(Date date){
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime.toLocalDate();
    }

    /**
     * 将日期转换到LocalTime
     *
     * @param date
     * @return
     */
    public static LocalTime toLocalTime(Date date){
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime.toLocalTime();
    }

    /**
     * 将LocalTime 转换到 Date
     * @param time
     * @return
     */
    public static Date fromLocalTime(LocalDateTime time){
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = time.atZone(zoneId);
        Date date = Date.from(zdt.toInstant());
        return date;
    }

    public static Date fromLocalDate(LocalDate localDate){
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        Instant instant1 = zonedDateTime.toInstant();
        Date from = Date.from(instant1);
        return  from;
    }


    /**
     * 获得某天最大时间 2018-10-15 23:59:59
     * @param date {@link Date}
     * @return
     */
    public static Date endOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获得某天最小时间 2018-10-15 00:00:00
     * @param date
     * @return
     */
    public static Date startOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }
}