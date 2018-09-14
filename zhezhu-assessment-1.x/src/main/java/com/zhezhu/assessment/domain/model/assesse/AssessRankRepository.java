package com.zhezhu.assessment.domain.model.assesse;

import com.zhezhu.commons.domain.EntityRepository;
import com.zhezhu.share.domain.id.PersonId;
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

    void deleteByPersonIdAndAssessTeamIdAndRankCategoryAndRankScopeAndRankNodeAndYearStartsAndYearEnds(
            PersonId personId,String assessTeamId,RankCategory category,RankScope scope,String node, int yearStarts,int yearEnds
    );

    AssessRank findByPersonIdAndRankNodeAndRankCategoryAndRankScopeAndYearStartsAndYearEnds(
            PersonId personId,String node,RankCategory category,RankScope scope,int yearStarts,int yearEnds);

    AssessRank findByPersonIdAndRankCategoryAndRankScopeAndRankNode(
            PersonId personId,RankCategory category,RankScope scope,String node);

    AssessRank findByAssessTeamIdAndRankCategoryAndRankScopeAndRankNode(
            String assessTeamId,RankCategory category,RankScope scope,String node);

    List<AssessRank> findAllByAssessTeamIdAndPersonIdAndRankCategoryAndRankScopeAndRankDateBetween(
            String teamId,PersonId personId, RankCategory category,RankScope scope, Date from, Date to);

    List<AssessRank> findAllByPersonIdAndAssessTeamIdAndRankCategoryAndRankScopeAndRankNodeAndYearStartsAndYearEnds(
            PersonId personId,String assessTeamId,RankCategory category,RankScope scope,String node, int yearStarts,int yearEnds);

    List<AssessRank> findAllByAssessTeamIdAndRankCategoryAndRankScopeAndRankNodeAndRankDateBetween(
            String assessTeamId,RankCategory category,RankScope scope,String node, Date from, Date to);
}