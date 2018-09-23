package com.zhezhu.access.domain.model.user;

import com.zhezhu.commons.domain.Entity;
import com.zhezhu.share.domain.id.UserId;
import lombok.*;

import java.util.Date;

/**
 * @author Liguiqing
 * @since V3.0
 */
@AllArgsConstructor
@Builder
@Getter
@ToString(exclude = {"password","detail"})
@EqualsAndHashCode(of = {"userName"},callSuper = false)
public class User extends Entity {

    private UserId userId;

    private String userName;

    private String password;

    private boolean ok;

    private Date createTime;

    private UserDetail detail;

    public void addDetail(UserDetail detail){
        this.detail = detail;
        detail.setUser(this);
    }

    public void updatePassword(String password){
        this.password = password;
    }

    protected User(){}
}