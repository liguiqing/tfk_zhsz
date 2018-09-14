package com.zhezhu.assessment.domain.model.assesse.rank;

import com.zhezhu.assessment.domain.model.assesse.RankCategory;
import com.zhezhu.assessment.domain.model.assesse.RankCategoryDate;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.common.Period;
import com.zhezhu.share.domain.school.Term;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Component
public class TermDate implements RankCategoryDate {


    @Override
    public boolean supports(RankCategory category) {
        return category.equals(RankCategory.Term);
    }

    @Override
    public Date from() {
        Period period = Term.defaultPeriodOfThisTerm();
        return period.starts();
    }

    @Override
    public Date to() {
        Period period = Term.defaultPeriodOfThisTerm();
        return period.ends();
    }

    @Override
    public String node() {
        Period period = Term.defaultPeriodOfThisTerm();
        LocalDate date = DateUtilWrapper.toLocalDate(period.starts());
        if(date.getMonthValue() == 9)
            return "1";
        return "2";
    }
}