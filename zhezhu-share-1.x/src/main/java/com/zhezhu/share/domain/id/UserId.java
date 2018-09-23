package com.zhezhu.share.domain.id;

import com.zhezhu.commons.domain.AbstractId;
import com.zhezhu.commons.domain.Identities;

import static com.zhezhu.share.domain.id.IdPrefixes.UserIdPrefix;

/**
 * 系统用户唯一标识
 *
 * @author Liguiqing
 * @since V3.0
 */

public class UserId extends AbstractId {
    public UserId(String anId) {
        super(anId);
    }

    public UserId() {
        this(Identities.genIdNone(UserIdPrefix));
    }
}