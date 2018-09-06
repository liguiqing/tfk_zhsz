package com.zhezhu.assessment.infrastructure.rank;

import com.zhezhu.assessment.domain.model.assesse.RankCategory;

import java.util.Date;

/**
 * @author Liguiqing
 * @since V3.0
 */

public interface RankCategoryDate {
    boolean supports(RankCategory category);

    Date from();

    Date to();
}