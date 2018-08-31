package com.zhezhu.sm.application.data;

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
public class CourseData {

    private String name;

    private String subjectId;

    private int gradeLevel;

    public CourseData(String name, String subjectId) {
        this.name = name;
        this.subjectId = subjectId;
    }
}