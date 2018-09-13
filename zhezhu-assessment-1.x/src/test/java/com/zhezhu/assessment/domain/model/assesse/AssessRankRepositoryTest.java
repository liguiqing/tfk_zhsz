package com.zhezhu.assessment.domain.model.assesse;

import com.zhezhu.assessment.AssessmentTestConfiguration;
import com.zhezhu.assessment.config.AssessmentApplicationConfiguration;
import com.zhezhu.commons.config.CommonsConfiguration;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.assessment.AssesseeId;
import com.zhezhu.share.domain.id.school.SchoolId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Copyright (c) 2016,$today.year, 深圳市易考试乐学测评有限公司
 **/

@ContextConfiguration(
        classes = {
                AssessmentTestConfiguration.class,
                CommonsConfiguration.class,
                AssessmentApplicationConfiguration.class
        }
)
@Transactional
@Rollback
public class AssessRankRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private AssessRankRepository repository;

    @Test
    public void test() {
        SchoolId schoolId = new SchoolId();
        PersonId personId = new PersonId();
        AssesseeId assesseeId = new AssesseeId();
        String[] nodes = {"2018-09-03","2018-09-04","2018-09-05","2018-09-06","2018-09-07"};
        String[] rankDate = {"2018-09-03","2018-09-04","2018-09-05","2018-09-06","2018-09-07"};
        int[] ranks = {12,10,5,3,2};
        int[] promotes = {-5,2,5,3,1};
        save(RankCategory.Day,rankDate,nodes,ranks,promotes,schoolId,personId,assesseeId);
        nodes = new String[]{"35","36","37","38"};
        rankDate = new String[]{"2018-09-07","2018-09-14","2018-09-21","2018-09-28"};
        ranks = new int[]{8,10,6,3};
        promotes = new int[]{-2,2,4,3};
        save(RankCategory.Weekend,rankDate,nodes,ranks,promotes,schoolId,personId,assesseeId);
        nodes = new String[]{"9","10","11","12","1"};
        rankDate = new String[]{"2018-09-28","2018-10-27","2018-11-230","2018-12-29","2019-01-28"};
        ranks = new int[]{8,10,6,3,1};
        promotes = new int[]{-2,2,4,3,0};
        save(RankCategory.Month,rankDate,nodes,ranks,promotes,schoolId,personId,assesseeId);
        nodes = new String[]{"1"};
        rankDate = new String[]{"2019-01-28"};
        ranks = new int[]{3};
        promotes = new int[]{1};
        save(RankCategory.Term,rankDate,nodes,ranks,promotes,schoolId,personId,assesseeId);

        List<AssessRank> dayRanks = repository.findAllByAssessTeamIdAndPersonIdAndRankCategoryAndRankScopeAndRankDateBetween(schoolId.id(),
                personId,RankCategory.Day,RankScope.Clazz,
                DateUtilWrapper.toDate("2018-09-03","yyyy-MM-dd"),
                DateUtilWrapper.toDate("2018-09-07","yyyy-MM-dd"));
        assertEquals(5,dayRanks.size());

        dayRanks = repository.findAllByAssessTeamIdAndPersonIdAndRankCategoryAndRankScopeAndRankDateBetween(schoolId.id(),
                personId,RankCategory.Day,RankScope.Clazz,
                DateUtilWrapper.toDate("2017-09-03","yyyy-MM-dd"),
                DateUtilWrapper.toDate("2019-09-07","yyyy-MM-dd"));
        assertEquals(5,dayRanks.size());
        assertEquals("2018-09-05",dayRanks.get(2).getRankNode());
        assertEquals(personId,dayRanks.get(2).getPersonId());
        assertEquals(5,dayRanks.get(2).getRank());
        assertEquals(5,dayRanks.get(2).getPromote());


        List<AssessRank> weekendRanks = repository.findAllByAssessTeamIdAndPersonIdAndRankCategoryAndRankScopeAndRankDateBetween(schoolId.id(),
                personId,RankCategory.Weekend,RankScope.Clazz,
                DateUtilWrapper.toDate("2018-09-03","yyyy-MM-dd"),
                DateUtilWrapper.toDate("2019-09-30","yyyy-MM-dd"));
        assertEquals(4,weekendRanks.size());

        List<AssessRank> rs = repository.findAllByPersonIdAndAssessTeamIdAndRankCategoryAndRankScopeAndRankNodeAndYearStartsAndYearEnds(
                personId,schoolId.id(),RankCategory.Weekend,RankScope.Clazz,"37",2018,2019);
        assertEquals(1,rs.size());

        rs = repository.findAllByAssessTeamIdAndRankCategoryAndRankScopeAndRankNodeAndRankDateBetween(
                schoolId.id(),RankCategory.Weekend,RankScope.Clazz,"37",
                DateUtilWrapper.toDate("2017-09-03","yyyy-MM-dd"),
                DateUtilWrapper.toDate("2019-09-07","yyyy-MM-dd")
        );
        assertEquals(1,rs.size());
        repository.deleteByPersonIdAndAssessTeamIdAndRankCategoryAndRankScopeAndRankNodeAndYearStartsAndYearEnds(
                personId,schoolId.id(),RankCategory.Weekend,RankScope.Clazz,"37",2018,2019
        );

        rs = repository.findAllByPersonIdAndAssessTeamIdAndRankCategoryAndRankScopeAndRankNodeAndYearStartsAndYearEnds(
                personId,schoolId.id(),RankCategory.Weekend,RankScope.Clazz,"37",2018,2019);
        assertEquals(0,rs.size());
    }

    private void save(RankCategory category,String[] rankDate,String[] nodes,int[] ranks,int[] promotes,SchoolId schoolId,PersonId personId,AssesseeId assesseeId){
        int i = 0;
        for(String node:nodes){
            AssessRank rank = AssessRank.builder()
                    .assesseeId(assesseeId)
                    .assessTeamId(schoolId.id())
                    .personId(personId)
                    .rankScope(RankScope.Clazz)
                    .rankCategory(category)
                    .yearStarts(2018)
                    .yearEnds(2019)
                    .rankDate(DateUtilWrapper.toDate(rankDate[i],"yyyy-MM-dd"))
                    .rankNode(node)
                    .rank(ranks[i])
                    .promote(promotes[i++])
                    .build();
            repository.save(rank);
        }
    }

}