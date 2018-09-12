package com.zhezhu.assessment.domain.model.assesse;

import com.zhezhu.commons.domain.IdentifiedValueObject;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.assessment.AssessTeamId;
import com.zhezhu.share.domain.id.assessment.AssesseeId;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import lombok.*;

import java.util.Date;

/**
 * 评价排名
 *
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@EqualsAndHashCode(exclude ={"rankDate","rank","promote"},callSuper = false)
@ToString
public class AssessRank extends IdentifiedValueObject {
    private SchoolId schoolId;

    private ClazzId clazzId;

    private AssessTeamId assessTeamId;

    private AssesseeId assesseeId;

    private PersonId personId;

    private int yearStarts;

    private int yearEnds;

    private RankScope rankScope;  //排名范围

    private RankCategory rankCategory; //排名类型

    private Date rankDate;

    private String rankNode; //排名节点:Year(yearStarts+yearEnds,如2018-2019);Term(1,2);Moth(1-12);Weekend(1-52)

    private double score;

    private double promoteScore; //进退步分数

    private int rank;

    private int promote;  //进退步

}