package com.zhezhu.access.domain.model.wechat;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@Data
public class WeChatAccount {
    private String openid;

    private String nickname;

    private String gender;

    private String province;

    private String city;

    private String country;

    private String headImgUrl;

    private String[] privilege;

    private String unionId;

    private boolean error = false;

    private int errCode;

    private String errMsg;

}