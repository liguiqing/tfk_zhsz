package com.zhezhu.share.infrastructure.school;

import com.zhezhu.share.domain.school.SchoolScope;
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

    private String tenantId;

    private String name;

    private String alias;

    private String scope;

    public boolean scopeOf(SchoolScope scope){
        return scope.name().equalsIgnoreCase(this.scope);
    }
}