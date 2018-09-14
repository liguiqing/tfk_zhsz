package com.zhezhu.sm.application.clazz;

import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.domain.school.Grade;
import com.zhezhu.sm.domain.model.clazz.Clazz;
import com.zhezhu.sm.domain.model.clazz.UnitedClazz;
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
public class NewClazzCommand {
    private String schoolId;

    private Date openedTime; //开班时间

    private String clazzName;

    private int yearStarts;

    private int yearEnds;

    private int gradeLevel;


    public Clazz toClazz() {
        //TODO other Clazz
        return toUnitedClazz();
    }

    private  UnitedClazz toUnitedClazz(){
        ClazzId clazzId = new ClazzId();
        UnitedClazz clazz = new UnitedClazz(clazzId,new SchoolId(this.schoolId),this.openedTime);
        clazz.addHistory(grade(),this.clazzName);
        return clazz;
    }

    public Grade grade(){
        return Grade.newWithLevel(this.gradeLevel);
    }

}