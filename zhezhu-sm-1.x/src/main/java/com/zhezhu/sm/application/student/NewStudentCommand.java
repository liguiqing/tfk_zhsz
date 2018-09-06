package com.zhezhu.sm.application.student;

import com.zhezhu.share.domain.person.Gender;
import com.zhezhu.sm.application.NewTeadentCommand;
import com.zhezhu.sm.application.data.Contacts;
import com.zhezhu.sm.application.data.CredentialsData;
import lombok.Builder;

import java.util.Date;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class NewStudentCommand extends NewTeadentCommand {

    public NewStudentCommand(){}

    @Builder
    public NewStudentCommand(String schoolId, Date joinDate, Date offDate, String name,
                             Date birthday, Gender gender, Contacts[] contacts, CredentialsData[] credentials) {
        super(schoolId,joinDate,offDate,name,birthday,gender,contacts,credentials);
    }

}