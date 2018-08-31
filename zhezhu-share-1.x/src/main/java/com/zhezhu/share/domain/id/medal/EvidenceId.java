package com.zhezhu.share.domain.id.medal;

import com.zhezhu.commons.domain.AbstractId;
import com.zhezhu.commons.domain.Identities;
import com.zhezhu.share.domain.id.IdPrefixes;

/**
 * 授勋证物唯一标识
 *
 * @author Liguiqing
 * @since V3.0
 */

public class EvidenceId extends AbstractId {
    public EvidenceId(String anId) {
        super(anId);
    }

    public EvidenceId() {
        super(Identities.genIdNone(IdPrefixes.EvidenceIdPrefix));
    }
}