package com.zhezhu.assessment.application.assess;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

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

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date doneDate;

    private double indexScore;

    private double score;

    private String word;

    private String assesseeName;

    private String assesseeId;

    private String clazzId;

    private boolean plus;

}