/*
 * Copyright (c) 2016,2018, zhezhu All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.zhezhu.share.domain.school;

import com.zhezhu.commons.domain.ValueObject;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.common.Period;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class Term extends ValueObject{

    private String name;

    private int seq = 1;

    public Term(String name,Sequence seq) {
        this.name = name;
        this.seq = seq.seq;
    }

    public static Period defaultPeriodOfThisTerm(){
        Date now = Calendar.getInstance().getTime();
        int month = DateUtilWrapper.month(now);
        Date dateStarts = null;
        Date dateEnds = null;
        int year = DateUtilWrapper.year(now);
        if(month > 8 || month < 2){

            dateStarts = DateUtilWrapper.toDate(year + "-09-01", "yyyy-MM-dd");
            dateEnds = DateUtilWrapper.toDate((year+1) + "-01-31", "yyyy-MM-dd");
            if(month <2) {
                dateStarts = DateUtilWrapper.toDate((year-1) + "-09-01", "yyyy-MM-dd");
            }
        }else{
            dateStarts = DateUtilWrapper.toDate(year + "-02-01", "yyyy-MM-dd");
            dateEnds = DateUtilWrapper.toDate((year+1) + "-07-31", "yyyy-MM-dd");
        }
        return new Period(dateStarts,dateEnds);
    }

    public static Term First(){
        return new Term("第一学期",Sequence.One);
    }

    public static Term Second(){
        Term t =  new Term("第二学期",Sequence.Two);
        return t;
    }

    public static Term Last(){
        return new Term("上学期",Sequence.One);
    }

    public static Term Next(){
        Term t =  new Term("下学期",Sequence.Two);
        return t;
    }

    public String name() {
        return name;
    }

    public int seq() {
        return seq;
    }

    //Only 4 persistence
    protected Term(){}

    public static enum Sequence{
        One(1),Two(2);
        private int seq;
        private Sequence(int seq){
            this.seq = seq;
        }
    }
}