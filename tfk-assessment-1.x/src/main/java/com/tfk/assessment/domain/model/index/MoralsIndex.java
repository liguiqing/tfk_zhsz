package com.tfk.assessment.domain.model.index;

import com.tfk.share.domain.id.index.IndexId;
import lombok.Builder;

/**
 * 德育评价指标
 *
 * @author Liguiqing
 * @since V3.0
 */
public class MoralsIndex extends Index {


    @Override
    public String getCategory() {
        return "D";
    }

    @Builder
    private MoralsIndex(IndexId indexId, String name, double score, double weight,
                        boolean customized) {
        super(indexId, name, score, weight, customized);
    }

    public MoralsIndex(){}
}