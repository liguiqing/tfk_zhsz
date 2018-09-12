package com.zhezhu.assessment.domain.model.assesse;

import com.zhezhu.commons.util.DateUtilWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 排名类型时间计算服务
 *
 * @author Liguiqing
 * @since V3.0
 */
@Component
public class RankCategoryService {

    @Autowired
    private List<RankCategoryDate> categoryDates;

    public Date from(RankCategory category ){
        for(RankCategoryDate date:categoryDates){
            if(date.supports(category)){
                return date.from();
            }
        }
        return DateUtilWrapper.now();
    }

    public Date to(RankCategory category ){
        for(RankCategoryDate date:categoryDates){
            if(date.supports(category)){
               return date.to();
            }
        }
        return DateUtilWrapper.now();
    }
}