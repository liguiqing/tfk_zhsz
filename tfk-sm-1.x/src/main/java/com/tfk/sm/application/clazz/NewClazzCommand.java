package com.tfk.sm.application.clazz;

import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.SchoolId;
import com.tfk.share.domain.school.Grade;
import com.tfk.sm.domain.model.clazz.Clazz;
import com.tfk.sm.domain.model.clazz.ClazzHistory;
import com.tfk.sm.domain.model.clazz.UnitedClazz;
import lombok.*;

import java.util.Date;

/**
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@AllArgsConstructor
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
        ClazzHistory history = new ClazzHistory(clazzId,grade(),this.clazzName);
        clazz.addHistory(history);
        return clazz;
    }

    public Grade grade(){
        return Grade.newWithLevel(this.gradeLevel);
    }

}