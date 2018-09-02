package com.zhezhu.share.domain.id.index;

import com.zhezhu.commons.domain.AbstractId;
import com.zhezhu.commons.domain.Identities;
import com.zhezhu.share.domain.id.IdPrefixes;

/**
 * 评价指标唯一标识
 *
 * @author Liguiqing
 * @since V3.0
 */

public class IndexId extends AbstractId {
    public IndexId(String anId) {
        super(anId);
    }

    public IndexId() {
        super(Identities.genIdNone(IdPrefixes.IndexIdPrefix));
    }
}