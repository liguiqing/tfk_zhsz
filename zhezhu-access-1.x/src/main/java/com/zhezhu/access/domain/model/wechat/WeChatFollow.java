package com.zhezhu.access.domain.model.wechat;

import com.zhezhu.access.domain.model.wechat.config.WeChatConfig;
import com.zhezhu.access.domain.model.wechat.config.WeChatHelper;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 微信公众号关注
 *
 * @author Liguiqing
 * @since V3.0
 */
@Getter
@ToString
@EqualsAndHashCode
public class WeChatFollow {
    private String signature;

    private String timestamp;

    private String nonce;

    private String echostr;

    private boolean ok;

    public void create(WeChatConfig config,Map<String,String> params){
        this.signature = params.get("signature");//(request.getParameter("signature"));
        this.timestamp = params.get("timestamp");//(request.getParameter("timestamp"));
        this.nonce = params.get("nonce");//(request.getParameter("nonce"));
        this.echostr = params.get("echostr");//(request.getParameter("echostr"));

        if (StringUtils.isEmpty(signature)
                || StringUtils.isEmpty(timestamp)
                || StringUtils.isEmpty(nonce)) {
            this.ok = false;
        } else {
            this.ok = WeChatHelper.checkSignature(signature, timestamp, nonce,config);
        }
    }

    public String getContent() {
        return this.isOk() ? this.getEchostr() : "";
    }
}