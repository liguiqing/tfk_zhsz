package com.zhezhu.access.application.school;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhezhu.access.domain.model.school.ClazzFollowAudit;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.id.access.ClazzFollowApplyId;
import com.zhezhu.share.domain.id.access.ClazzFollowAuditId;
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
@Setter
@Getter
@ToString
public class ClazzFollowAuditCommand {

    private String applyId;

    private String auditorId;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date auditDate;

    private boolean ok;

    private String description;

    public ClazzFollowAudit toAudit(ClazzFollowAuditId auditId){
        return ClazzFollowAudit.builder()
                .auditId(auditId)
                .auditDate(DateUtilWrapper.now())
                .applyId(new ClazzFollowApplyId(applyId))
                .ok(ok)
                .description(description)
                .build();
    }
}