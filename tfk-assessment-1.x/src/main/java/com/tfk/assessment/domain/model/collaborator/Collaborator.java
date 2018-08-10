package com.tfk.assessment.domain.model.collaborator;

import com.tfk.commons.domain.Entity;
import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.id.assessment.CollaboratorId;
import com.tfk.share.domain.id.school.SchoolId;

/**
 * 参与评价的人物
 *
 * @author Liguiqing
 * @since V3.0
 */

public abstract class Collaborator extends Entity {

    private CollaboratorId collaboratorId;

    private SchoolId schoolId;

    private PersonId personId;

}