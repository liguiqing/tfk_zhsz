package com.zhezhu.share.infrastructure.school;

import com.zhezhu.commons.util.CollectionsUtilWrapper;
import com.zhezhu.share.domain.person.Gender;
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
@ToString(exclude = {"clazzes","contacts","credentials"})
public class StudentData {
    private String schoolId;

    private String studentId;

    private String personId;

    private String name;

    private String gender;

    private List<ContactData> contacts;

    private List<ClazzData> clazzes;

    private List<CredentialsData> credentials;

    public boolean hasCredentials(String value){
        if (CollectionsUtilWrapper.isNullOrEmpty(this.credentials))
            return false;
        for(CredentialsData c:credentials){
            if(c.getValue().equals(value))
                return true;
        }
        return false;
    }

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

    public boolean sameGenderAs(Gender gender){
        if(this.gender == null || this.gender.length() == 0)
            return true;

        if(gender == null)
            return false;

        return this.gender.equals(gender.name());
    }

    public boolean hasCredentials(String name,String value){
        if(CollectionsUtilWrapper.isNotNullAndNotEmpty(this.credentials)){
            for(CredentialsData c:this.credentials){
                if(c.sameAs(name,value))
                    return true;
            }
        }
        return false;
    }

    public boolean sameManagedClazzOf(String clazzId){
        String mClazzId = this.getManagedClazzId();
        if(mClazzId.length() < 1)
            return false;

        return mClazzId.equalsIgnoreCase(clazzId);
    }

}