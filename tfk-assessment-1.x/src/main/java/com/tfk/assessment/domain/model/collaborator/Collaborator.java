package com.tfk.assessment.domain.model.collaborator;

import com.tfk.commons.domain.Entity;
import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.id.assessment.CollaboratorId;
import com.tfk.share.domain.id.school.SchoolId;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 参与评价的人员
 *
 * @author Liguiqing
 * @since V3.0
 */

@ToString
@EqualsAndHashCode
public abstract class Collaborator extends Entity {

    private CollaboratorId collaboratorId;

    private SchoolId schoolId;

    private PersonId personId;

    protected Collaborator(){}

}