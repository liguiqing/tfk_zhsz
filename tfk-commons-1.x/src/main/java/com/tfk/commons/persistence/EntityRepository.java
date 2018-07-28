package com.tfk.commons.persistence;

import com.tfk.commons.domain.AbstractId;
import com.tfk.commons.domain.IdentifiedDomainObject;

/**
 * @author Liguiqing
 * @since V3.0
 */

public interface EntityRepository<ID extends AbstractId,E extends IdentifiedDomainObject> {

    E loadOf(ID id);

    void save(E e);

    void remove(E e);
}