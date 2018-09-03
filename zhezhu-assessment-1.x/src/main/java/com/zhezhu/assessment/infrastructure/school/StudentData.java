package com.zhezhu.assessment.infrastructure.school;

import lombok.*;

import java.util.List;

/**
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString(exclude = {"clazzes"})
public class StudentData {
    private String schoolId;

    private List<ClazzData> clazzes;

    private String studentId;

    private String personId;

    private String name;

    private String gender;

}