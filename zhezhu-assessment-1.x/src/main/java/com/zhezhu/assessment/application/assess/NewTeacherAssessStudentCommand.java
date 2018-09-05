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
public class NewTeacherAssessStudentCommand {
    private String schoolId;

    private String teacherPersonId;

    private String studentPersonId;

    private String indexId;

    private double score;

    private String word;

}