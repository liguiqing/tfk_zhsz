package com.zhezhu.share.domain.id.assessment;

import com.zhezhu.commons.domain.AbstractId;
import com.zhezhu.commons.domain.Identities;
import com.zhezhu.share.domain.id.IdPrefixes;


/**
 * @author Liguiqing
 * @since V3.0
 */

public class AssessTeamId extends AbstractId {
    public AssessTeamId(String anId) {
        super(anId);
    }

    public AssessTeamId() {
        super(Identities.genIdNone(IdPrefixes.AssessTeamIdPrefix));
    }
}