package com.zhezhu.access.domain.model.wechat;

import com.zhezhu.commons.lang.LabelEnum;

/**
 * 微信用户类型
 *
 * @author Liguiqing
 * @since V3.0
 */

public enum WeChatCategory implements LabelEnum {
    Teacher("教师"), Student("学生"),Parent("家长");
    String label ;
    WeChatCategory(String label){
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
}