package com.zhezhu.commons.message.tencent;

import com.zhezhu.commons.message.MessageEvent;
import com.zhezhu.commons.message.MessageListener;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Slf4j
public class WeixinMessageListener implements MessageListener {

    @Override
    public void addEvent(MessageEvent event) {
        log.debug("Send  {} to {} with Weixin",event.getMessage(),event.getTarget());
    }
}