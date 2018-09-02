package com.zhezhu.access.application.school;

import com.zhezhu.access.domain.model.school.ClazzFollowApply;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.access.ClazzFollowApplyId;
import lombok.*;

import java.util.Date;

/**
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ClazzFollowApplyCommand {

    private String applyingSchoolId;

    private String applyingClazzId;

    private String applierId;

    private String applierName;

    private String applierPhone;

    private Date applyDate;

    private String cause;

    public ClazzFollowApply toApply(ClazzFollowApplyId applyId){
        return ClazzFollowApply.builder()
                .applyId(applyId)
                .applierId(new PersonId(applierId))
                .applierName(applierName)
                .applierPhone(applierPhone)
                .applyDate(DateUtilWrapper.now())
                .cause(cause)
                .build();
    }

}