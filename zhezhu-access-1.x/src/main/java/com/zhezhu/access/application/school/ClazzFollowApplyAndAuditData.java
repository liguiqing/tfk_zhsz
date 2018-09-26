package com.zhezhu.access.application.school;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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

    private String gradeName;

    private String clazzId;

    private String clazzName;

    private String applyId;

    private String auditId;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date applyDate;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date auditDate;

    private String auditCause;

    private String applyDesc;

    private String applierId;

    private String applierName;

    private String applierPhone;

    private String auditorId;

    private String auditorName;

    private boolean ok;
}