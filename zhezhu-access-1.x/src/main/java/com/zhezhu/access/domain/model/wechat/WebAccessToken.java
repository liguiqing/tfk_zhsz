package com.zhezhu.access.domain.model.wechat;

import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@Data
public class WebAccessToken {
    private String accessToken;

    private int expiresIn;

    private String refreshToken;

    private String openId;

    private String scope;

    private boolean error = false;

    private int errCode;

    private String errMsg;

    public void setError(){
        if(this.errCode >0){
            this.error = true;
        }
    }

    public boolean isSuccess(){
        return this.openId != null;
    }

}