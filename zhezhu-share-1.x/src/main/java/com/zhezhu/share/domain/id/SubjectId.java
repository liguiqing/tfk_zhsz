package com.zhezhu.share.domain.id;

import com.zhezhu.commons.domain.AbstractId;
import com.zhezhu.commons.domain.Identities;

import static com.zhezhu.share.domain.id.IdPrefixes.SubjectIdPrefix;

/**
 * 学科唯一标识
 *
 * @author Liguiqing
 * @since V3.0
 */

public class SubjectId extends AbstractId {
    public SubjectId(String anId) {
        super(anId);
    }

    public SubjectId() {
        super(Identities.genIdNone(SubjectIdPrefix));
    }
}