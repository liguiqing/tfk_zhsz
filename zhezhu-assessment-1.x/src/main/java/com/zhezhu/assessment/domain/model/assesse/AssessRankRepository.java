package com.zhezhu.assessment.domain.model.assesse;

import com.zhezhu.commons.domain.EntityRepository;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.school.SchoolId;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Liguiqing
 * @since V3.0
 */

@Repository
public interface AssessRankRepository extends EntityRepository<AssessRank, PersonId> {

    default PersonId nextIdentity(){return new PersonId();}

    void save(AssessRank rank);

    AssessRank findByPersonIdAndRankNodeAndRankCategoryAndRankScopeAndYearStartsAndYearEnds(PersonId personId,String node,
                                                                                            RankCategory category,RankScope scope,
                                                                                            int yearStarts,int yearEnds);

    List<AssessRank> findAllBySchoolIdAndPersonIdAndRankCategoryAndRankScopeAndRankDateBetween(
            SchoolId schoolId,PersonId personId, RankCategory category,RankScope scope, Date from, Date to);
}