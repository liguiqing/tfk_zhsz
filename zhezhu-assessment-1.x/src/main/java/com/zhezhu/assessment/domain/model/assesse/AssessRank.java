package com.zhezhu.assessment.domain.model.assesse;

import com.zhezhu.commons.domain.IdentifiedValueObject;
import com.zhezhu.share.domain.id.PersonId;
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

    private PersonId personId;

    private int yearStarts;

    private int yearEnds;

    private RankScope rankScope;

    private RankCategory rankCategory; //排名类型:

    private Date rankDate;

    private String rankNode; //排名节点:Year(yearStarts+yearEnds,如2018-2019);Term(1,2);Moth(1-12);Weekend(1-52)

    private int rank;

    private int promote;

}