package com.zhezhu.assessment.domain.model.index;

import com.google.common.primitives.Doubles;
import com.zhezhu.commons.domain.ValueObject;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 指标分值
 *
 * @author Liguiqing
 * @since V3.0
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class IndexScore extends ValueObject {

    private double score;

    private double weight;

    public boolean isOutOfBounds(double score){
        return false;
        //return Doubles.compare(this.score,score) > 0;
    }

    public double convert(double score){
        if(isOutOfBounds(score))
            return -1d;
        return this.weight * score;
    }

    protected IndexScore() {

    }
}