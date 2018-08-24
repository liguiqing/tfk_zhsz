package com.tfk.access.domain.model.wechat.audit;

import com.tfk.commons.domain.ValueObject;
import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.SchoolId;
import lombok.*;

/**
 * 关注被审核人
 *
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode(exclude = {"name","role"})
@ToString
public class Defendant extends ValueObject {
    private PersonId defendantId;

    private SchoolId schoolId;

    private ClazzId clazzId;

    private AuditRole role;

    private String name;

}