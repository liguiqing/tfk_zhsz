package com.tfk.assessment.domain.model.assesse;

import com.tfk.commons.domain.IdentifiedValueObject;
import com.tfk.share.domain.id.index.IndexId;
import com.tfk.share.domain.id.assessment.AssessId;
import com.tfk.share.domain.id.assessment.AssesseeId;
import com.tfk.share.domain.id.assessment.AssessorId;
import lombok.*;

import java.util.Date;

/**
 * 评价
 *
 * @author Liguiqing
 * @since V3.0
 */
@ToString
@EqualsAndHashCode(of={"assessorId","assesseeId","indexId","doneDate"},callSuper = false)
@Getter
public class Assess extends IdentifiedValueObject {

    private AssessId assessId;

    private AssessorId assessorId;

    private AssesseeId assesseeId;

    private IndexId indexId;

    private Date doneDate;

    private String category;

    private double score;

    @Builder
    private Assess(AssessId assessId, AssessorId assessorId, AssesseeId assesseeId,
                   IndexId indexId, Date doneDate, String category, double score) {
        this.assessId = assessId;
        this.assessorId = assessorId;
        this.assesseeId = assesseeId;
        this.indexId = indexId;
        this.doneDate = doneDate;
        this.category = category;
        this.score = score;
    }

    public Assess(){}

}