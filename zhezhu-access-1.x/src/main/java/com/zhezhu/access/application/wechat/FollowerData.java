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

    private String studentNo;

    private String schoolId;

    private String schoolName;

    private String gradeName;

    private String clazzId;

    private String clazzName;

    private String personId;

    private String gender;

    private String cause;

    private String applierName;

    private String applierPhone;

    private String applierId;

    private String applyId;

    private boolean canBeOk;
}