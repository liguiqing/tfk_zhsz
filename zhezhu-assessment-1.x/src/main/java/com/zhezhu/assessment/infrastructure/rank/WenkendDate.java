package com.zhezhu.assessment.infrastructure.rank;

import com.zhezhu.assessment.domain.model.assesse.RankCategory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Component
public class WenkendDate implements RankCategoryDate {


    @Override
    public boolean supports(RankCategory category) {
        return category.equals(RankCategory.Weekend);
    }

    @Override
    public Date from() {
        return null;
    }

    @Override
    public Date to() {
        return null;
    }
}