package com.tfk.sm.application.data;

import lombok.*;

/**
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class ClazzData {

    private String clazzId;

    private String name;

    private String gradeName;

    private int gradeLevel;

}