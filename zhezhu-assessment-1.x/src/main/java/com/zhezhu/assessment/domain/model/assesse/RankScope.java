package com.zhezhu.assessment.domain.model.assesse;

import com.zhezhu.commons.lang.LabelEnum;

/**
 * 排名范围
 *
 * @author Liguiqing
 * @since V3.0
 */

public enum RankScope implements LabelEnum {
    Clazz("班级"),Grade("年级"),School("学校");

    private String label;

    RankScope(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public String getValue() {
        return this.name();
    }

    @Override
    public String toString(){
        return this.label + "【" + this.name() + "】" ;
    }

    public RankScope child(){
        switch (this){
            case School:return Grade;
            case Grade:return Clazz;
        }
        return null;
    }
}