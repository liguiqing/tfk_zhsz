package com.tfk.assessment.domain.model.collaborator;

import com.tfk.commons.domain.EntityRepository;
import com.tfk.share.domain.id.assessment.CollaboratorId;

/**
 * @author Liguiqing
 * @since V3.0
 */

public interface CollaboratorRepository<T extends Collaborator> extends EntityRepository<T,CollaboratorId> {

    Collaborator loadOf(CollaboratorId collaboratorId);
}