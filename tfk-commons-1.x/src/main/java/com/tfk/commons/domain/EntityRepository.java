/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.commons.domain;


import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

/**
 * 实体仓储
 *
 * @author Liguiqing
 * @since V3.0
 */

@NoRepositoryBean
public interface EntityRepository<T extends IdentifiedDomainObject,ID extends AbstractId> extends Repository<T,ID> {
    ID nextIdentity();

    void save(T t);

}