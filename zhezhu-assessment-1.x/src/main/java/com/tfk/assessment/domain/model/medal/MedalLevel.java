package com.tfk.assessment.domain.model.medal;

import com.tfk.commons.AssertionConcerns;
import com.tfk.commons.domain.ValueObject;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 勋章等级
 *
 * @author Liguiqing
 * @since V3.0
 */

@Getter
@ToString(of={"level","category"})
@EqualsAndHashCode(of={"level","category"},callSuper = false)
public class MedalLevel extends ValueObject {

    private int level;

    private String category;

    protected MedalLevel(){}

    public MedalLevel(int level, String category) {
        AssertionConcerns.assertArgumentTrue(level>0,"as-03-003");
        this.level = level;
        this.category = category;
    }

    public boolean gt(MedalLevel otherLevel){
        return this.level > otherLevel.level;
    }

}