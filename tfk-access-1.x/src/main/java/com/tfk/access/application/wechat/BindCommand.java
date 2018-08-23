package com.tfk.access.application.wechat;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BindCommand {
    private String wechatOpenId;

    private String name;

    private String schoolId;

    private String clazzId;

    private String phone;

    private String gender;
}