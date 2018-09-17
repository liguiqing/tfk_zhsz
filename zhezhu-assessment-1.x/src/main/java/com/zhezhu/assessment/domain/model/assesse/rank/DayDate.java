package com.zhezhu.assessment.domain.model.assesse.rank;

import com.zhezhu.assessment.domain.model.assesse.RankCategory;
import com.zhezhu.assessment.domain.model.assesse.RankCategoryDate;
import com.zhezhu.commons.util.DateUtilWrapper;
import org.springframework.stereotype.Component;

import java.time.*;
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
        return Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    @Override
    public Date to() {
        return Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofTotalSeconds(0)));
    }

    @Override
    public String node() {
        return LocalDate.now().toString();
    }
}