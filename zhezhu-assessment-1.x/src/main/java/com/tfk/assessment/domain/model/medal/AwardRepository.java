package com.tfk.assessment.domain.model.medal;

import com.tfk.commons.domain.EntityRepository;
import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.id.medal.AwardId;
import com.tfk.share.domain.id.medal.MedalId;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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

    @Override
    @CacheEvict(value = "asCache", key="#p0.awardId.id")
    void save(Award award);

    @Cacheable(value = "asCache",key = "#p0.id",unless = "#result == null")
    @Query("From Award where removed=0 and awardId=?1")
    Award loadOf(AwardId awardId);

    @CacheEvict(value = "asCache",key="#p0.id")
    @Modifying
    @Query(value = "update Award set removed = 1 where awardId=?1")
    void delete(AwardId awardId);

    List<Award> findAwardsByPossessorIdAndMedalIdEqualsAndWinDateBetweenAndRiseToIsNull(
            PersonId possessorId, MedalId medalId, Date dateStarts, Date dateEnds);
}