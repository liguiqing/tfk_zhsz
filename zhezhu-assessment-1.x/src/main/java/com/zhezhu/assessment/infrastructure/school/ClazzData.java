package com.zhezhu.assessment.infrastructure.school;

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
public class ClazzData {
    private String schoolId;

    private String clazzId;

    private String clazzName;

    private String gradeName;

    private int gradeLevel;

}