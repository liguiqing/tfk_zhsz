package com.zhezhu.share.infrastructure.school;

import com.zhezhu.commons.util.CollectionsUtilWrapper;
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
        if(CollectionsUtilWrapper.isNotNullAndNotEmpty(this.clazzes)){
            return clazzes.get(0).getGradeLevel();
        }
        return 0;
    }

    public String getGradeName(){
        if(CollectionsUtilWrapper.isNotNullAndNotEmpty(this.clazzes)){
            return clazzes.get(0).getGradeName();
        }
        return "";
    }

    public String getManagedClazzId(){
        if(CollectionsUtilWrapper.isNotNullAndNotEmpty(this.clazzes)){
            return clazzes.get(0).getClazzId();
        }
        return "";
    }

}