package com.zhezhu.access.application.school;

import lombok.*;

import java.util.Date;

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
public class ClazzFollowApplyAndAuditData {
    private String schoolId ;

    private String schoolName;

    private String clazzId;

    private String clazzName;

    private String applyId;

    private String auditId;

    private Date applyDate;

    private Date auditDate;

    private String auditCause;

    private String applyDesc;

    private String applierId;

    private String applierName;

    private String auditorId;

    private String auditorName;

    private boolean ok;
}