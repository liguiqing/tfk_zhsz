package com.tfk.assessment.domain.model.index;

import com.tfk.commons.domain.EntityRepository;
import com.tfk.share.domain.id.index.IndexId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Repository("IndexRepository")
public interface IndexRepository extends EntityRepository<Index,IndexId> {

    @Override
    default IndexId nextIdentity(){
        return new IndexId();
    }

    @Query("From Index where indexId=?1")
    Index loadOf(IndexId indexId);

}