package com.tfk.assessment.domain.model.index;

import com.tfk.share.domain.id.index.IndexId;
import lombok.Builder;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class LaboursIndex extends Index {


    @Override
    public String getCategory() {
        return "L";
    }

    @Builder
    private LaboursIndex(IndexId indexId, String name, double score, double weight,
                         boolean customized,String description) {
        super(indexId, name, score, weight, customized, description);
    }

    public LaboursIndex() {
    }
}