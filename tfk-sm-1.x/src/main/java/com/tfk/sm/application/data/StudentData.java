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
public class StudentData {

    private String schoolId;

    private String clazzId;

    private String studentId;

    private String personId;

    private String name;

    private int gender;

    private String gradeName;

    private int gradeLevel;

    private String clazzName;

}