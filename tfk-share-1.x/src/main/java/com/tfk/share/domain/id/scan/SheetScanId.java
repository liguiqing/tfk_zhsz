package com.tfk.share.domain.id.scan;

import com.tfk.commons.domain.AbstractId;
import com.tfk.commons.domain.Identities;

import static com.tfk.share.domain.id.IdPrefixes.SheetScanIdPrefix;

/**
 * 答题卡扫描图像唯一标识
 *
 * @author Liguiqing
 * @since V3.0
 */

public class SheetScanId extends AbstractId {

    public SheetScanId(String anId) {
        super(anId);
    }

    public SheetScanId() {
        super(Identities.genIdNone(SheetScanIdPrefix));
    }

}