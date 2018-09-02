package com.zhezhu.sm.application;

import com.zhezhu.share.domain.person.Gender;
import com.zhezhu.sm.application.data.Contacts;
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
public abstract class NewTeadentCommand {

    private String schoolId;

    private Date joinDate;

    private Date offDate;

    private String name;

    private Date birthday;

    private Gender gender;

    private Contacts[] contacts;
}