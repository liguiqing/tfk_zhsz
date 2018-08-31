package com.tfk.sm.application.data;

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
public class StudyData {

    private String clazzId;

    private String courseName;

    private String subjectId;

    private int gradeLevel;
}