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
public class SchoolData {

    private String schoolId;

    private String name;

    private String alias;

    private String scope;
}