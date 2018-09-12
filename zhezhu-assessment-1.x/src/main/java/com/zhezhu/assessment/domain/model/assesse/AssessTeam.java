package com.zhezhu.assessment.domain.model.assesse;

import com.zhezhu.commons.domain.IdentifiedValueObject;
import com.zhezhu.share.domain.id.assessment.AssessTeamId;
import lombok.*;

/**
 * 评价分组
 *
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@EqualsAndHashCode(of="assessTeamId",callSuper = false)
@ToString
public class AssessTeam extends IdentifiedValueObject {

    private AssessTeamId assessTeamId;

    private AssessTeamId parentAssessTeamId;

    private String teamName;

    private String teamId;

    public void updateParent(AssessTeam parent){
        this.parentAssessTeamId = parent.getAssessTeamId();
    }

}