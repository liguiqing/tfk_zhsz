package com.tfk.access.domain.model.wechat;

import com.tfk.commons.domain.ValueObject;
import com.tfk.share.domain.id.PersonId;
import lombok.*;

import java.util.Date;

/**
 * 关注者审核结果
 *
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class FollowerAudit extends ValueObject {

    private PersonId auditorId;

    private String auditorName;

    private Date auditDate;

    private AuditResult auditResult;

}