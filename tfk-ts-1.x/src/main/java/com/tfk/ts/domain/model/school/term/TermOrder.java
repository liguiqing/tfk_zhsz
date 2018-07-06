/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.term;

import com.tfk.commons.util.DateUtilWrapper;

import java.util.Date;

/**
 * 学期中上学期，下学期
 *
 * @author Liguiqing
 * @since V3.0
 */

public enum TermOrder {
    First{
        public int getOrder() {
            return 1;
        }

        public String getName(){
            return "上学期";
        }

        public String getAlias(){
            return "第一学期";
        }

    },
    Second{
        public int getOrder(){
            return 2;
        }

        public String getName(){
            return "下学期";
        }

        public String getAlias(){
            return "第二学期";
        }
    };

    public int getOrder(){
        return 0;
    }

    public String getName(){
        return null;
    }

    public String getAlias(){
        return null;
    }

    public static TermOrder orderOf(Date date) {
        int month = DateUtilWrapper.month(date);
        if(month >1 && month <8){
            return Second;
        }
        return First;
    }
}