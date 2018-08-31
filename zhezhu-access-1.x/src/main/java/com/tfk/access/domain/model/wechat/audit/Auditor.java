package com.tfk.access.domain.model.wechat.audit;

import com.tfk.commons.domain.ValueObject;
import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.SchoolId;
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