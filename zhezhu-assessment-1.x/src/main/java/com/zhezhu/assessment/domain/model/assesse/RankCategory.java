package com.zhezhu.assessment.domain.model.assesse;

import com.zhezhu.commons.lang.LabelEnum;

/**
 * 排名类型
 *
 * @author Liguiqing
 * @since V3.0
 */

public enum RankCategory implements LabelEnum {
    Year("学年"),Term("学期"),Month("月"),Weekend("周"),Day("天");

    private String label;

    private RankCategory(String label){
        this.label = label;
    }

    @Override
    public String toString(){
        return this.getLabel() + ":" + this.getValue();
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public String getValue() {
        return this.name();
    }

    public RankCategory child(){
        switch (this){
            case Year:return Term;
            case Term:return Month;
            case Month:return Weekend;
            case Weekend:return Day;
        }
        return null;
    }
}