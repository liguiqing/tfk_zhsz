package com.tfk.assessment.domain.model.medal;

import com.tfk.commons.domain.IdentifiedValueObject;
import com.tfk.share.domain.id.medal.AwardId;
import com.tfk.share.domain.id.medal.EvidenceId;

/**
 * 授勋证物
 *
 * @author Liguiqing
 * @since V3.0
 */

public abstract class Evidence extends IdentifiedValueObject {
    private EvidenceId evidenceId;

    private AwardId awardId;

}