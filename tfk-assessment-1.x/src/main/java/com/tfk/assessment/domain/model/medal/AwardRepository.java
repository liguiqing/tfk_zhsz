package com.tfk.assessment.domain.model.medal;

import com.tfk.commons.domain.EntityRepository;
import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.id.medal.AwardId;
import com.tfk.share.domain.id.medal.MedalId;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Repository("AwardRepository")
public interface AwardRepository extends EntityRepository<Award,AwardId> {

    @Override
    default AwardId nextIdentity() {
        return new AwardId();
    }

    List<Award> findAwardsByPossessorIdAndMedalIdEqualsAndWinDateBetweenAndRiseToIsNull(
            PersonId possessorId, MedalId medalId, Date dateStarts, Date dateEnds);
}