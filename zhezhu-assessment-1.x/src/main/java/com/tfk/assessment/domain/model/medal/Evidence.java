package com.tfk.assessment.domain.model.medal;

import com.tfk.commons.domain.IdentifiedValueObject;
import com.tfk.share.domain.id.medal.AwardId;
import com.tfk.share.domain.id.medal.EvidenceId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 授勋证物
 *
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = {"evidenceId"},callSuper = false)
@ToString(of = {"evidenceId","awardId","storage","uploadDate"})
public abstract class Evidence extends IdentifiedValueObject {
    private EvidenceId evidenceId;

    private AwardId awardId;

    private String storage;

    private Date uploadDate;

    protected Evidence(EvidenceId evidenceId, AwardId awardId, String storage, Date uploadDate) {
        this.evidenceId = evidenceId;
        this.awardId = awardId;
        this.storage = storage;
        this.uploadDate = uploadDate;
    }
}