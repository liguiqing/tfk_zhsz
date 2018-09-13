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
@EqualsAndHashCode(of ={"personId","yearStarts","yearEnds","rankNode"},callSuper = false)
@ToString
public class AssessRank extends IdentifiedValueObject {

    private AssesseeId assesseeId;

    //Value from AssessTeam.teamId;
    private String assessTeamId;

    //Value from Assessee.collaborator.personId
    private PersonId personId;

    private int yearStarts;

    private int yearEnds;

    //排名范围
    private RankScope rankScope;

    //排名类型
    private RankCategory rankCategory;

    private Date rankDate;

    // If rankCategory'value of:
    // Year(yearStarts+yearEnds,ex:2018-2019);
    // Term(1,2);
    // Moth(1-12);
    // Weekend(1-52)
    // Day(date.toString("yyyy-MM-dd"))
    private String rankNode;

    private double score;

    private double promoteScore; //进退步分数

    private int rank;

    private int promote;  //进退步

}