package com.zhezhu.assessment.domain.model.assesse;

import com.zhezhu.commons.domain.IdentifiedValueObject;
import com.zhezhu.share.domain.id.assessment.AssessId;
import com.zhezhu.share.domain.id.assessment.AssesseeId;
import com.zhezhu.share.domain.id.assessment.AssessorId;
import com.zhezhu.share.domain.id.index.IndexId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

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

    private double score; //得分

    private String word;//评语

    @Builder
    private Assess(AssessId assessId, AssessorId assessorId, AssesseeId assesseeId,
                   IndexId indexId, Date doneDate, String category, double score,String word) {
        this.assessId = assessId;
        this.assessorId = assessorId;
        this.assesseeId = assesseeId;
        this.indexId = indexId;
        this.doneDate = doneDate;
        this.category = category;
        this.score = score;
        this.word = word;
    }

    public Assess(){}

}