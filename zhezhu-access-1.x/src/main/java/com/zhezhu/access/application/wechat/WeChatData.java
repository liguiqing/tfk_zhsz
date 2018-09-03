package com.zhezhu.access.application.wechat;

import lombok.*;

/**
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class WeChatData {
    private String openId;

    private String role;

    private String personId;

    private String name;

    private String phone;

}