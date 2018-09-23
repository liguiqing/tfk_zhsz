package com.zhezhu.access.domain.model.user;

import com.zhezhu.commons.domain.IdentifiedValueObject;
import com.zhezhu.share.domain.id.PersonId;
import lombok.*;

/**
 * @author Liguiqing
 * @since V3.0
 */
@AllArgsConstructor
@Builder
@Getter
@EqualsAndHashCode(of = "user",callSuper = false)
@ToString
public class UserDetail extends IdentifiedValueObject {
    private User user;

    private PersonId personId;

    private String realName;

    private String mobile;

    private String email;

    protected void setUser(User user){
        this.user = user;
    }

    protected UserDetail(){}
}