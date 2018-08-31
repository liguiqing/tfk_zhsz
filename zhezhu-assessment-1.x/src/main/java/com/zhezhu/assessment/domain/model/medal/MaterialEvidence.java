package com.zhezhu.assessment.domain.model.medal;

import com.zhezhu.share.domain.id.medal.AwardId;
import com.zhezhu.share.domain.id.medal.EvidenceId;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 实物证物
 *
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
public class MaterialEvidence extends Evidence {

    @Builder
    MaterialEvidence(EvidenceId evidenceId, AwardId awardId, String storage, Date uploadDate) {
        super(evidenceId, awardId, storage, uploadDate);
    }
}