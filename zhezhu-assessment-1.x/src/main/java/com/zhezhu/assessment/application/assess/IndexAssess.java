package com.zhezhu.assessment.application.assess;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class IndexAssess {

    private String indexId;

    private double score = -1d;

    private String word;

    public IndexAssess(String word){
        this.word =  word;
    }

    public IndexAssess(String indexId,double score){
        this.indexId = indexId;
        this.score = score;
    }
}