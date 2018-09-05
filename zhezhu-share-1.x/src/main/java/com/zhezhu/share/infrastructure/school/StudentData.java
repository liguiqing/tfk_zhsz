package com.zhezhu.share.infrastructure.school;

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
public class StudentData {
    private String schoolId;

    private String studentId;

    private String personId;

    private String name;

    private String gender;

    private List<ContactData> contacts;

    private List<ClazzData> clazzes;

    public int getGradeLevel(){
        if(clazzes != null){
            return clazzes.get(0).getGradeLevel();
        }
        return 0;
    }

    public String getGradeName(){
        if(clazzes != null){
            return clazzes.get(0).getGradeName();
        }
        return "";
    }

}