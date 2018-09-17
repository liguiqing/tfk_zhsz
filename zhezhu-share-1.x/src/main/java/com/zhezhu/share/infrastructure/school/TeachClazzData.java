package com.zhezhu.share.infrastructure.school;

import lombok.*;

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
public class TeachClazzData {

    private String job;

    private String course;

    private ClazzData clazz;

}