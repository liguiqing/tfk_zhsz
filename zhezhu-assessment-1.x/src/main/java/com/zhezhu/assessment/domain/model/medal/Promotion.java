package com.zhezhu.assessment.domain.model.medal;

import com.zhezhu.commons.domain.IdentifiedValueObject;
import com.zhezhu.share.domain.id.medal.PromotionId;
import com.zhezhu.share.domain.id.school.SchoolId;

/**
 * 勋章晋级
 *
 * @author Liguiqing
 * @since V3.0
 */

public class Promotion extends IdentifiedValueObject {
    private PromotionId promotionId;

    private SchoolId schoolId;

    private int upLeast; //晋级数量

}