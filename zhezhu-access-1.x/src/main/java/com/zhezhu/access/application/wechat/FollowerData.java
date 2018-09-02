package com.zhezhu.access.application.wechat;

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
@Builder
public class FollowerData {

    private String name;

    private String schoolId;

    private String clazzId;

    private String personId;

    private String gender;

    private String cause;
}