package com.tfk.access.domain.model.wechat;

import com.tfk.commons.util.JsonUtillWrapper;
import lombok.*;

/**
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class WeChatAccessToken {

    private String access_token;

    private int expires_in = -1;

    public static WeChatAccessToken from(String json){
        WeChatAccessToken token = JsonUtillWrapper.from(json,WeChatAccessToken.class);
        return token;
    }
}