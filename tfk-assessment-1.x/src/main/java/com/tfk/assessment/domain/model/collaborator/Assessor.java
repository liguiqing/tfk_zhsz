package com.tfk.assessment.domain.model.collaborator;

import com.tfk.commons.domain.Entity;
import com.tfk.share.domain.id.assessment.AssessorId;
import com.tfk.share.domain.id.school.TeacherId;
import lombok.*;

/**
 * 评估者
 *
 * @author Liguiqing
 * @since V3.0
 */
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@Builder
public class Assessor extends Entity {
    private AssessorId assessorId;

    private Collaborator collaborator;

}