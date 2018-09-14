package com.zhezhu.assessment.domain.model.assesse.rank;

import com.zhezhu.assessment.domain.model.assesse.RankCategory;
import com.zhezhu.assessment.domain.model.assesse.RankCategoryDate;
import com.zhezhu.commons.util.DateUtilWrapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Component
public class MonthDate implements RankCategoryDate {


    @Override
    public boolean supports(RankCategory category) {
        return category.equals(RankCategory.Month);
    }

    @Override
    public Date from() {
        LocalDate now = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        return DateUtilWrapper.fromLocalDate(now);
    }

    @Override
    public Date to() {
        LocalDate now = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        return DateUtilWrapper.fromLocalDate(now);
    }

    @Override
    public String node() {
        LocalDate now = LocalDate.now();
        return now.getMonthValue() + "";
    }
}