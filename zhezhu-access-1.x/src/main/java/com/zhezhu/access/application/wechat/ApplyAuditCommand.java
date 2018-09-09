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
public class ApplyAuditCommand {
    private String auditorId; //审核者的PersonId

    private String applyId;

    private String auditId;

    private boolean ok;

    private String description;

}