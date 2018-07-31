package com.tfk.sm.port.adapter.persistence;

import com.tfk.share.domain.id.school.SchoolId;
import com.tfk.sm.domain.model.school.School;
import com.tfk.sm.domain.model.school.SchoolRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Repository
public class SchoolRepositoryHibernateImpl {


    @PersistenceContext
    private EntityManager em;

    public SchoolId nextIdentity() {
        return new SchoolId();
    }


    public School loadOfId(SchoolId id) {
        return null;
    }

    public void save(School school) {
        em.persist(school);
    }
}