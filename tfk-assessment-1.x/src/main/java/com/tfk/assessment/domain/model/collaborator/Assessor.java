package com.tfk.assessment.domain.model.collaborator;

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
@EqualsAndHashCode
@Builder
public class Assessor extends Collaborator {
    private TeacherId teacherId;

    protected Assessor(){}
}