package com.zhezhu.commons.util;

import com.zhezhu.commons.lang.LabelEnum;

/**
 * 客户端类型
 *
 * @author Liguiqing
 * @since V3.0
 */

public enum ClientType implements LabelEnum {
    Common("通用"),PC("PC web端"),MobileApp("手机移动端APP"),PadApp("平板移动端"),WeChatApp("微信小程序"),WeChatPublic("微信公众号");

    private String label;

    private String value;

    private ClientType(String label){
        this.label = label;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.label +":" + this.value;
    }
}