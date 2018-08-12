package com.tfk.assessment.domain.model.index;

import com.tfk.share.domain.id.index.IndexId;
import lombok.Builder;

/**
 * 美育指标
 *
 * @author Liguiqing
 * @since V3.0
 */

public class EstheticsIndex extends Index {

    @Builder
    private EstheticsIndex(IndexId indexId, String name, double score, double weight, boolean customized, String description) {
        super(indexId, name, score, weight, customized, description);
    }

    public EstheticsIndex() {
    }

    @Override
    public String getCategory() {
        return "M";
    }
}