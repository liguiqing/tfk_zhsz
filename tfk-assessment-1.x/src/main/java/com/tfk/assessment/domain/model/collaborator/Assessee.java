package com.tfk.assessment.domain.model.collaborator;

import com.tfk.commons.domain.Entity;
import com.tfk.share.domain.id.assessment.AssesseeId;
import com.tfk.share.domain.id.school.StudentId;
import lombok.*;

/**
 * 被评估计者
 *
 * @author Liguiqing
 * @since V3.0
 */
@ToString
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@Getter
public class Assessee extends Entity {
    private AssesseeId assesseeId;

    private Collaborator collaborator;

    public Assessee(){}
}