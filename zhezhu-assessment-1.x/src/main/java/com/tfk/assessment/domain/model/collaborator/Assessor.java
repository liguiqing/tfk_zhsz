package com.tfk.assessment.domain.model.collaborator;

import com.tfk.commons.domain.IdentifiedValueObject;
import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.id.assessment.AssessorId;
import com.tfk.share.domain.id.school.SchoolId;
import lombok.*;

/**
 * 评估者
 *
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@ToString(of={"assessorId","collaborator"})
@EqualsAndHashCode(of={"assessorId","collaborator"},callSuper = false)
@Getter
public class Assessor extends IdentifiedValueObject {
    private AssessorId assessorId;

    private Collaborator collaborator;

    @Builder
    private Assessor(AssessorId assessorId, PersonId personId, SchoolId schoolId,String role){
        this.assessorId = assessorId;
        this.collaborator = new Collaborator(schoolId,personId, role);
    }
}