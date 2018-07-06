package com.tfk.commons.persistence;

import com.tfk.commons.domain.AbstractId;
import org.springframework.jdbc.core.JdbcOperations;

/**
 * @author Liguiqing
 * @since V3.0
 */

public abstract  class JdbcRepository<E> {
    protected EntityMapper<E> mapper;

    protected JdbcOperations jdbc;

    public JdbcRepository(EntityMapper mapper, JdbcOperations jdbc) {
        this.mapper = mapper;
        this.jdbc = jdbc;
    }

    protected E find(String sql , AbstractId id){
        return jdbc.query(sql,resultSet -> {
            return mapper.mapper(resultSet);
        },new Object[]{id.id()});
    }

    protected void insert(E e){
        String sql = mapper.insertSql();
        Object[] args = mapper.insertOrgs(e);
        jdbc.update(sql,args);
    }

}