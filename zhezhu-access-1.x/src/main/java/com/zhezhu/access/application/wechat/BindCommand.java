package com.zhezhu.access.application.wechat;


import lombok.*;

import java.util.List;

/**
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"followers"})
@Builder
public class BindCommand {
    private String wechatOpenId;

    private String name;

    private String phone;

    private List<FollowerData> followers;
}