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
@ToString(exclude = "children")
public class SchoolAssessRankData {

    private String schoolId;

    private String clazzId;

    private String personId;//

    private String assesseeName;

    private String rankScope; // RankScope

    private String rankCategory; //RankCategory

    private String rankNode;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date rankDate;

    private double score;

    private double promoteScore; //进退步分数

    private int rank;

    private int promote;  //进退步

}