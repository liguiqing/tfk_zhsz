package com.tfk.sm.domain.model.teacher;

import com.tfk.share.domain.id.school.SchoolId;
import com.tfk.share.domain.id.school.TeacherId;
import com.tfk.share.domain.person.Person;
import com.tfk.share.domain.school.Course;
import com.tfk.share.domain.school.Grade;

import java.util.Date;
import java.util.Set;

/**
 * 教师
 *
 * @author Liguiqing
 * @since V3.0
 */

public class Teacher extends Person {
    private SchoolId schoolId;

    private Date joinDate; //入职日期

    private Date offDate; //离职日期

    private Set<TeachedCourse> courses;

    public boolean canTeach(Grade grade, Course course){
        if(this.courses == null)
            return false;
        for(TeachedCourse tc:this.courses){
            if(tc.sameOf(grade,course)){
                return true;
            }
        }

        return false;
    }
}