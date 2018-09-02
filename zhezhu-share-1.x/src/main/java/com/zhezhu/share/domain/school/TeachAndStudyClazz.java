package com.zhezhu.share.domain.school;

import com.zhezhu.share.domain.common.Period;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 教学班级
 *
 * @author Liguiqing
 * @since V3.0
 */
@EqualsAndHashCode(of={"course","period"},callSuper = false)
@ToString(of={"course","period"})
public class TeachAndStudyClazz extends GradClazz {

    private Course course;

    private Period period;

    public TeachAndStudyClazz(SchoolId schoolId, ClazzId clazzId,
                              Grade grade, Course course, Period period) {
        super(schoolId, clazzId, grade);
        this.course = course;
        this.period = period;
    }

    TeachAndStudyClazz(){}
}