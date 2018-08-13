package com.tfk.assessment.domain.model.index;

import com.tfk.share.domain.id.index.IndexId;
import lombok.Builder;

/**
 * 智育评价指标
 *
 * @author Liguiqing
 * @since V3.0
 */

public class IntelligenceIndex extends Index {

    @Override
    public String getCategory() {
        return "Z";
    }

    @Builder
    private IntelligenceIndex(IndexId indexId, String name, double score, double weight,
                              boolean customized,String description) {
        super(indexId, name, score, weight, customized, description);
    }

    public IntelligenceIndex() {
    }
}