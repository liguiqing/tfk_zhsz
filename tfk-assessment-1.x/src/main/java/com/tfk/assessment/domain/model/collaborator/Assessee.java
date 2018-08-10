package com.tfk.assessment.domain.model.collaborator;

import com.tfk.share.domain.id.school.StudentId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 被评估计者
 *
 * @author Liguiqing
 * @since V3.0
 */
@ToString
@EqualsAndHashCode
@Builder
public class Assessee extends Collaborator {
    private StudentId studentId;

    protected Assessee(){}
}