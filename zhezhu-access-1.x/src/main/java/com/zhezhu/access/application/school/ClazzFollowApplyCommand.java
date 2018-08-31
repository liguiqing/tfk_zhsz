package com.zhezhu.access.application.school;

import lombok.*;

import java.util.Date;

/**
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ClazzFollowApplyCommand {

    private String applyingSchoolId;

    private String applyingClazzId;

    private String applierId;

    private String applierName;

    private String applierPhone;

    private Date applyDate;

    private String cause;

}