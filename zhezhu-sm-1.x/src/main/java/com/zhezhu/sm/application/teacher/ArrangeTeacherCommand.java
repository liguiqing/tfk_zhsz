package com.zhezhu.sm.application.teacher;

import com.zhezhu.sm.application.data.CourseData;
import lombok.*;

import java.util.Date;
/**
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@ToString(exclude = {"courses","managementClazzIds","teachingClazzIds"})
public class ArrangeTeacherCommand {
    private String teacherId;

    private Date dateStarts;

    private Date dateEnds;

    private CourseData[] courses;

    private String[] managementClazzIds;

    private String[] teachingClazzIds;

    public ArrangeTeacherCommand(String teacherId, CourseData[] courses,
                                 String[] managementClazzIds, String[] teachingClazzIds) {
        this.teacherId = teacherId;
        this.courses = courses;
        this.managementClazzIds = managementClazzIds;
        this.teachingClazzIds = teachingClazzIds;
    }

}