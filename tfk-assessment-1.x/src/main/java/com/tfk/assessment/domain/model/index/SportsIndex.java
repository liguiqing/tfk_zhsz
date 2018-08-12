package com.tfk.assessment.domain.model.index;

import com.tfk.share.domain.id.index.IndexId;
import lombok.Builder;

/**
 * 体育评价指标
 *
 * @author Liguiqing
 * @since V3.0
 */

public class SportsIndex extends Index {

    @Override
    public String getCategory() {
        return "T";
    }

    @Builder
    private SportsIndex(IndexId indexId, String name, double score, double weight,
                        boolean customized) {
        super(indexId, name, score, weight, customized);
    }

    public SportsIndex(){}
}