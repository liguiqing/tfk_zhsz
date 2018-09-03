package com.zhezhu.assessment.application.assess;

import lombok.*;

/**
 * @author Liguiqing
 * @since V3.0
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class AssessData {
    private String indexName;

    private double indexScore;

    private double score;

    private String word;

    private String assesseeName;

    private String assesseeId;

    private String clazzId;

}