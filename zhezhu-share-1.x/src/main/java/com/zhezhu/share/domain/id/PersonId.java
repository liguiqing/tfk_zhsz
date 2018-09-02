package com.zhezhu.share.domain.id;

import com.zhezhu.commons.domain.AbstractId;
import com.zhezhu.commons.domain.Identities;

import static com.zhezhu.share.domain.id.IdPrefixes.PersonIdPrefix;

/**
 * 个人唯一标识
 *
 * @author Liguiqing
 * @since V3.0
 */

public class PersonId extends AbstractId {


    public PersonId(String anId) {
        super(anId);
    }

    public PersonId() {
        super(Identities.genIdNone(PersonIdPrefix));
    }

}