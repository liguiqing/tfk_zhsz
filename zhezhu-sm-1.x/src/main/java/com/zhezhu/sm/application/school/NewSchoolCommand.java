package com.zhezhu.sm.application.school;

import lombok.*;

/**
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class NewSchoolCommand {

    private String name;//学校名称

    private String alias; //学校简称

    private int scope;

}