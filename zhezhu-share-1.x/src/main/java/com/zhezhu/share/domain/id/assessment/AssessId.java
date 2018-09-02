package com.zhezhu.share.domain.id.assessment;

import com.zhezhu.commons.domain.AbstractId;
import com.zhezhu.commons.domain.Identities;
import com.zhezhu.share.domain.id.IdPrefixes;

/**
 * 评价参与者ID
 *
 * @author Liguiqing
 * @since V3.0
 */

public class AssessId extends AbstractId {

    public AssessId(String anId) {
        super(anId);
    }

    public AssessId() {
        super(Identities.genIdNone(IdPrefixes.AssessIdPrefix));
    }
}