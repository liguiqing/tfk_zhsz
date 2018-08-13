package com.tfk.assessment.domain.model.index;

import com.tfk.commons.domain.IdentifiedValueObject;
import com.tfk.share.domain.id.identityaccess.TenantId;
import com.tfk.share.domain.id.index.IndexId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 租户定制指标
 *
 * @author Liguiqing
 * @since V3.0
 */
@Getter
@ToString(of={"tenantId","indexId"})
@EqualsAndHashCode(of={"tenantId","indexId"},callSuper = false)
public class TenantIndex extends IdentifiedValueObject {

    private TenantId tenantId;

    private IndexId indexId;

    public TenantIndex(TenantId tenantId, IndexId indexId) {
        this.tenantId = tenantId;
        this.indexId = indexId;
    }
}