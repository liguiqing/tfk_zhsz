package com.zhezhu.assessment.domain.model.collaborator;

import com.zhezhu.commons.domain.ValueObject;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.school.SchoolId;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 参与评价的人员
 *
 * @author Liguiqing
 * @since V3.0
 */
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of={"schoolId","personId","role"},callSuper = false)
@Getter
public class Collaborator extends ValueObject {

    private SchoolId schoolId;

    private PersonId personId;

    private String role;

    protected Collaborator(){}

}