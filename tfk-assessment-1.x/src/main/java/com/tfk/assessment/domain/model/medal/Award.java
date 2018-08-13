package com.tfk.assessment.domain.model.medal;

import com.google.common.collect.Sets;
import com.tfk.commons.domain.Entity;
import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.id.medal.AwardId;
import com.tfk.share.domain.id.medal.MedalId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
import java.util.Set;

/**
 * 授勋
 *
 * @author Liguiqing
 * @since V3.0
 */

@Getter
@EqualsAndHashCode(of="awardId",callSuper = false)
@ToString
public class Award extends Entity {
    private AwardId awardId;

    private MedalId medalId;

    private PersonId possessorId;

    private Date winDate;

    private Set<Evidence> evidences;

    private AwardId riseTo;

    public Award(){}

    @Builder
    private Award(AwardId awardId, MedalId medalId, PersonId possessorId, Date winDate) {
        this.awardId = awardId;
        this.medalId = medalId;
        this.possessorId = possessorId;
        this.winDate = winDate;
    }

    public void riseTo(Award award){
        this.riseTo = award.awardId;
    }

    public boolean isSameMedal(MedalId medalId){
        return this.medalId.equals(medalId);
    }

    public void addEvidence(Evidence aEvidence){
        if(this.evidences == null)
            this.evidences = Sets.newHashSet();
        this.evidences.add(aEvidence);
    }
}