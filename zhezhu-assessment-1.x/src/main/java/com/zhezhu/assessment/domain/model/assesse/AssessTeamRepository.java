package com.zhezhu.assessment.domain.model.assesse;

import com.zhezhu.commons.domain.EntityRepository;
import com.zhezhu.share.domain.id.assessment.AssessTeamId;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Repository
public interface AssessTeamRepository extends EntityRepository<AssessTeam, AssessTeamId> {

    @Override
    default AssessTeamId nextIdentity(){return new AssessTeamId();}

    @Override
    @CacheEvict(value = "asCache", key="#p0.assessTeamId.id")
    void save(AssessTeam team);

    @Cacheable(value = "asCache",key = "#p0.id",unless = "#result == null")
    @Query("From AssessTeam where assessTeamId=?1 and removed=0")
    AssessTeam loadOf(AssessTeamId teamId);

    @Query("From AssessTeam where teamId=?1 and removed=0")
    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    AssessTeam findByTeamId(String teamId);

    @Query("From AssessTeam where parentAssessTeamId=?1 and removed=0")
    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    List<AssessTeam> findAllByParentAssessTeamId(AssessTeamId parentTeamId);
}