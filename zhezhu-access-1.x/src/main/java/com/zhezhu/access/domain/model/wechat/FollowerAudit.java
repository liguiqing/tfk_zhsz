package com.zhezhu.access.domain.model.wechat;

import com.zhezhu.commons.domain.ValueObject;
import com.zhezhu.share.domain.id.PersonId;
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