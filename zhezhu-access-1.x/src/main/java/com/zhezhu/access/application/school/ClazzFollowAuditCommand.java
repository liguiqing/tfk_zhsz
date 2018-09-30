package com.zhezhu.access.application.school;

import com.zhezhu.access.domain.model.school.ClazzFollowAudit;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.access.ClazzFollowApplyId;
import com.zhezhu.share.domain.id.access.ClazzFollowAuditId;
import lombok.*;

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

    private boolean ok;

    private String description;

    public ClazzFollowAudit toAudit(ClazzFollowAuditId auditId){
        return ClazzFollowAudit.builder()
                .auditId(auditId)
                .auditorId(new PersonId(this.auditorId))
                .auditDate(DateUtilWrapper.now())
                .applyId(new ClazzFollowApplyId(applyId))
                .ok(ok)
                .description(description)
                .build();
    }
}