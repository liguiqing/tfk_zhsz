package com.zhezhu.assessment.domain.model.assesse;

/**
 * 排名策略
 *
 * @author Liguiqing
 * @since V3.0
 */

public interface RankStrategy {

    RankStrategy newStrategy();

    int getRank(double score);

}