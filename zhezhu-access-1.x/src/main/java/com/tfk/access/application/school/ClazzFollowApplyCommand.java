package com.tfk.access.application.school;

import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.id.access.ClazzFollowApplyId;
import com.tfk.share.domain.id.access.ClazzFollowAuditId;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.SchoolId;
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
public class ClazzFollowApplyCommand {

    private String applyingSchoolId;

    private String applyingClazzId;

    private String applierId;

    private String applierName;

    private String applierPhone;

    private Date applyDate;

    private String cause;

}