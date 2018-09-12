package com.zhezhu.assessment.domain.model.assesse.rank;

import com.zhezhu.assessment.domain.model.assesse.RankStrategy;

/**
 * @author Liguiqing
 * @since V3.0
 */
public class SameScoreSamRankStrategy implements RankStrategy {

    private double score = 0;

    private int rank = 0;

    public SameScoreSamRankStrategy(){}

    @Override
    public RankStrategy newStrategy() {
        return this;
    }

    @Override
    public int getRank(double score) {
        if(this.score != score){
            this.score = score;
            this.rank++;
        }
        return this.rank;
    }
}