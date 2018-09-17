package com.zhezhu.share.infrastructure.school;

import com.google.common.collect.Lists;
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
@ToString(exclude = {"clazzes","contacts"})
public class TeacherData {

    private String schoolId;

    private String teacherId;

    private String personId;

    private String name;

    private List<ContactData> contacts;

    private List<TeachClazzData> clazzes;

    public void addClazz(TeachClazzData clazz){
        if(this.clazzes == null)
            this.clazzes = Lists.newArrayList();
        this.clazzes.add(clazz);
    }

    public TeacherData asMaster(ClazzData clazz){
        this.addClazz(TeachClazzData.builder().job("Master").clazz(clazz).build());
        return this;
    }

    public TeacherData asTeacher(ClazzData clazz,String course){
        this.addClazz(TeachClazzData.builder().job("Teacher").course(course).clazz(clazz).build());
        return this;
    }

}