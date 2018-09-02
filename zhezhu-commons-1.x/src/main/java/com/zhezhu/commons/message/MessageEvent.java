package com.zhezhu.commons.message;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class MessageEvent<T extends Messagingable> {

    private T t;

    public MessageEvent(T t){
        this.t = t;
    }

    public String getMessage(){
        return t.toMessge();
    }

    public String getTarget(){
        return this.t.target();
    }
}