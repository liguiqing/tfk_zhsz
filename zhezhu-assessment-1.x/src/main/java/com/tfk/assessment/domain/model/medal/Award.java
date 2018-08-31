package com.tfk.assessment.domain.model.medal;

import com.google.common.collect.Sets;
import com.tfk.commons.AssertionConcerns;
import com.tfk.commons.domain.Entity;
import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.id.medal.AwardId;
import com.tfk.share.domain.id.medal.MedalId;
import lombok.*;

import java.util.Date;
import java.util.Set;

/**
 * 授勋
 *
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of="awardId",callSuper = false)
@ToString(of={"awardId","medalId","possessorId","medalName","winDate"})
public class Award extends Entity {
    private AwardId awardId;

    private MedalId medalId;

    private PersonId possessorId;

    private Date winDate;

    private String medalName;

    private Set<Evidence> evidences;

    private AwardId riseTo;

    @Builder
    private Award(AwardId awardId, MedalId medalId, PersonId possessorId, Date winDate,String medalName) {
        this.awardId = awardId;
        this.medalId = medalId;
        this.possessorId = possessorId;
        this.winDate = winDate;
        this.medalName = medalName;
    }

    public void riseTo(Award award){
        AssertionConcerns.assertArgumentNull(this.riseTo,"as-03-001");
        this.riseTo = award.awardId;

    }

    public boolean isSameMedal(MedalId medalId){
        return this.medalId.equals(medalId);
    }

    public Award addEvidence(Evidence aEvidence){
        if(this.evidences == null)
            this.evidences = Sets.newHashSet();
        this.evidences.add(aEvidence);
        return this;
    }

}