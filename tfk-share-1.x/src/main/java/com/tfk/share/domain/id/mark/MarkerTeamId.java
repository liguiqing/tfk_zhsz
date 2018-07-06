package com.tfk.share.domain.id.mark;

import com.tfk.commons.domain.AbstractId;
import com.tfk.commons.domain.Identities;
import static com.tfk.share.domain.id.IdPrefixes.MarkerTeamIdPrefix;
/**
 * @author Liguiqing
 * @since V3.0
 */

public class MarkerTeamId extends AbstractId {

    public MarkerTeamId(String anId) {
        super(anId);
    }

    public MarkerTeamId() {
        super(Identities.genIdNone(MarkerTeamIdPrefix));
    }
}