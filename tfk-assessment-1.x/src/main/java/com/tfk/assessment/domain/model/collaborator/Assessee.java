package com.tfk.assessment.domain.model.collaborator;

import com.tfk.commons.domain.IdentifiedValueObject;
import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.id.assessment.AssesseeId;
import com.tfk.share.domain.id.school.SchoolId;
import lombok.*;

/**
 * 被评估计者
 *
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@ToString(of={"assesseeId","collaborator"})
@EqualsAndHashCode(of={"assesseeId"},callSuper = false)
@Getter
public class Assessee extends IdentifiedValueObject {
    private AssesseeId assesseeId;

    private Collaborator collaborator;

    @Builder
    private Assessee(AssesseeId assesseeId, PersonId personId, SchoolId schoolId, String role){
        this.assesseeId = assesseeId;
        this.collaborator = new Collaborator(schoolId,personId, role);
    }
}