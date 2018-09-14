package com.zhezhu.assessment.domain.model.assesse.rank;

import com.zhezhu.assessment.domain.model.assesse.RankCategory;
import com.zhezhu.assessment.domain.model.assesse.RankCategoryDate;
import com.zhezhu.commons.util.DateUtilWrapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Component
public class DayDate implements RankCategoryDate {


    @Override
    public boolean supports(RankCategory category) {
        return category.equals(RankCategory.Day);
    }

    @Override
    public Date from() {
        Date now = DateUtilWrapper.now();
        return DateUtilWrapper.getStartDayOfWeek(now);
    }

    @Override
    public Date to() {
        Date now = DateUtilWrapper.now();
        return DateUtilWrapper.getEndDayOfWeek(now);
    }

    @Override
    public String node() {
        return LocalDate.now().toString();
    }
}