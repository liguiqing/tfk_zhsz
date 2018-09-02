package com.zhezhu.sm.application.student;

import com.zhezhu.sm.application.data.StudyData;
import lombok.*;

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
public class ArrangeStudentCommand {

    private String studentId;

    private Date dateStarts;

    private Date dateEnds;

    private StudyData[] courses;

    private String managedClazzId;

}