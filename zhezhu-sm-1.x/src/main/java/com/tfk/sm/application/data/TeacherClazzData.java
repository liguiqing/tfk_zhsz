package com.tfk.sm.application.data;

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
public class TeacherClazzData {

    private String clazzType;

    private List<ClazzData> clazzDatas;

}