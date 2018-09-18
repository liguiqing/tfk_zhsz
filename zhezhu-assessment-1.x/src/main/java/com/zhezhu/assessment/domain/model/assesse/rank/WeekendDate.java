package com.zhezhu.assessment.domain.model.assesse.rank;

import com.zhezhu.assessment.domain.model.assesse.RankCategory;
import com.zhezhu.assessment.domain.model.assesse.RankCategoryDate;
import com.zhezhu.commons.util.DateUtilWrapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.Locale;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Component
public class WeekendDate implements RankCategoryDate {


    @Override
    public boolean supports(RankCategory category) {
        return category.equals(RankCategory.Weekend);
    }

    @Override
    public Date from() {
        LocalDate now = LocalDate.now();
        TemporalField fieldISO = WeekFields.of(Locale.CHINA).dayOfWeek();
        return DateUtilWrapper.startOfDay(DateUtilWrapper.fromLocalDate(now.with(fieldISO, 2)));
    }

    @Override
    public Date to() {
        LocalDate now = LocalDate.now();
        TemporalField fieldISO = WeekFields.of(Locale.CHINA).dayOfWeek();
        return DateUtilWrapper.endOfDay(DateUtilWrapper.fromLocalDate(now.with(fieldISO, 6)));
    }

    @Override
    public String node() {
        LocalDate now = LocalDate.now();
        TemporalField fieldISO = WeekFields.of(Locale.CHINA).weekOfYear();
        return now.get(fieldISO)+"";
    }
}