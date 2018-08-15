package com.tfk.assessment.domain.model.medal;

import com.tfk.share.domain.id.medal.AwardId;
import com.tfk.share.domain.id.medal.EvidenceId;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 图片证物
 *
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
public class PhotoEvidence extends Evidence {

    @Builder
    PhotoEvidence(EvidenceId evidenceId, AwardId awardId, String storage, Date uploadDate) {
        super(evidenceId, awardId, storage, uploadDate);
    }
}