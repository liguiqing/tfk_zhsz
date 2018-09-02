package com.zhezhu.sm.application.student;

import com.zhezhu.share.domain.person.Gender;
import com.zhezhu.sm.application.NewTeadentCommand;
import com.zhezhu.sm.application.data.Contacts;

import java.util.Date;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class NewStudentCommand extends NewTeadentCommand {

    public NewStudentCommand(){}

    public NewStudentCommand(String schoolId, Date joinDate, Date offDate, String name,
                             Date birthday, Gender gender, Contacts[] contacts) {
        super(schoolId,joinDate,offDate,name,birthday,gender,contacts);
    }

}