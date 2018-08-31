/*
 * Copyright (c) 2016,2018, zhezhu All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.zhezhu.share.domain.school;

import com.zhezhu.commons.AssertionConcerns;
import com.zhezhu.commons.domain.ValueObject;
import com.zhezhu.commons.util.DateUtilWrapper;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

/**
 * 学年
 *
 * @author Liguiqing
 * @since V3.0
 */
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class StudyYear extends ValueObject {

    private int yearStarts;

    private int yearEnds;

    public StudyYear(int yearStarts, int yearEnds) {
        AssertionConcerns.assertArgumentTrue(yearStarts > 0,"无效的学年起始年度");
        AssertionConcerns.assertArgumentTrue(yearEnds > 0,"无效的学年终止年度");
        AssertionConcerns.assertArgumentTrue(yearStarts < yearEnds,"学年起始年度不能大于终止年度");
        AssertionConcerns.assertArgumentTrue(yearStarts == yearEnds-1,"学年起始年度与终止年度不匹配");
        this.yearStarts = yearStarts;
        this.yearEnds = yearEnds;
    }


    public static StudyYear now(){
        //根据当前时间，自动计算学年
        Date now = DateUtilWrapper.now();
        return newYearsOf(now);
    }

    public static StudyYear newYearsOf(Date date){
        AssertionConcerns.assertArgumentNotNull(date ,"无效的学年起始年度");

        int month = DateUtilWrapper.month(date);
        if(month < 8)
            return new StudyYear(DateUtilWrapper.prevYear(date),DateUtilWrapper.thisYear());
        return new StudyYear(DateUtilWrapper.thisYear(),DateUtilWrapper.nextYear(date));
    }

    public String stringOf(){
        return this.yearStarts + "-" + this.yearEnds;
    }

    //Only 4 persistence
    protected StudyYear(){}
}