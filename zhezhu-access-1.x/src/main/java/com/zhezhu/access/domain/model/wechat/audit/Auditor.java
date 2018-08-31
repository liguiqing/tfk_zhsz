package com.zhezhu.access.domain.model.wechat.audit;

import com.zhezhu.commons.domain.ValueObject;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import lombok.*;

/**
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode(exclude = {"name"})
@ToString
public class Auditor extends ValueObject {

    private PersonId auditorId;

    private SchoolId schoolId;

    private AuditRole role;

    private ClazzId clazzId;

    private String name;

}