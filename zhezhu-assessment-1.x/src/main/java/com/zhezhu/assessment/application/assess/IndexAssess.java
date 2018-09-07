package com.zhezhu.assessment.application.assess;

import lombok.*;

/**
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class IndexAssess {

    private String indexId;

    private double score;

    private String word;
}