/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.infrastructure.strategy;

import com.tfk.ts.domain.model.school.GradeLevel;
import com.tfk.ts.domain.model.school.GradeNameGenerateStrategy;
import com.tfk.ts.domain.model.school.common.Configable;
import com.tfk.ts.domain.model.school.common.Configuation;
import com.tfk.ts.domain.model.school.common.ConfiguationFactory;

/**
 * 一到六年级，初一，初二，初三，高一，高二高三名称生成器
 *
 * @author Liguiqing
 * @since V3.0
 */

public class GradeNameOneToSixAndC1ToC3AndG1ToG3GenerateStrategy implements GradeNameGenerateStrategy,Configable {

    private  Configuation configuation = new Configuation(GradeNameGenerateStrategy.configName,
            getClass().getName(),"#link#c1toG3Generate.html");

    public GradeNameOneToSixAndC1ToC3AndG1ToG3GenerateStrategy(){
        ConfiguationFactory.register(this);
    }

    @Override
    public Configuation config() {
        return this.configuation;
    }

    @Override
    public String genGradeName(GradeLevel seq) {
        switch (seq){
            case Two:
                return "二年级";
            case Three:
                return "三年级";
            case Four:
                return "四年级";
            case Five:
                return "五年级";
            case Six:
                return "六年级";
            case Seven:
                return "初一";
            case Eight:
                return "初二";
            case Nine:
                return "初三";
            case Ten:
                return "高一";
            case Eleven:
                return "高二";
            case Twelve:
                return "高三";
        }
        return "一年级";
    }

}