package com.zhezhu.assessment.domain.model.assesse;

import com.zhezhu.commons.domain.EntityRepository;
import com.zhezhu.share.domain.id.assessment.AssessId;
import com.zhezhu.share.domain.id.assessment.AssessTeamId;
import com.zhezhu.share.domain.id.assessment.AssesseeId;
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
@Repository
public interface AssessRepository extends EntityRepository<Assess,AssessId> {

    @Override
    default  AssessId nextIdentity() {
        return new AssessId();
    }

    @Override
    @CacheEvict(value = "asCache", key="#p0.assessId.id")
    void save(Assess assess);

    @Cacheable(value = "asCache",key = "#p0.id",unless = "#result == null")
    @Query("From Assess where assessId=?1 and removed=0")
    Assess loadOf(AssessId assessId);

    @CacheEvict(value = "asCache",key="#p0.id")
    @Modifying
    @Query(value = "update Assess set removed = 1 where assessId=?1")
    void delete(AssessId assessId);

    @Query("From Assess where assesseeId=?1 and doneDate between ?2 and ?3 and removed=0")
    List<Assess> findByAssesseeIdAndDoneDateBetween(AssesseeId assesseeId, Date doneDateFrom, Date doneDateTo);

    @Query("From Assess where assessTeamId=?1 and doneDate between ?2 and ?3 and removed=0")
    List<Assess> findByAssessTeamIdAndDoneDateBetween(AssessTeamId teamId, Date doneDateFrom, Date doneDateTo);
}