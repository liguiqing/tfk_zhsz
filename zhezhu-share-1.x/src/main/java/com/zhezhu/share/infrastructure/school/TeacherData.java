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
public class TeacherData {

    private String schoolId;

    private String teacherId;

    private String personId;

    private String name;

    private List<ContactData> contacts;

    private List<ClazzData> clazzes;

}