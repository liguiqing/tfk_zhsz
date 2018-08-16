package com.tfk.assessment.application.assess;

import lombok.*;

/**
 * @author Liguiqing
 * @since V3.0
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class NewAssessCommand {

    private String assesseeId;

    private String assessorId;

    private String indexId;

    private double score;

}