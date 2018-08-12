package com.tfk.assessment.domain.model.assesse;

import com.tfk.commons.domain.EntityRepository;
import com.tfk.share.domain.id.assessment.AssessId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Repository("AssesseRepository")
public interface AssesseRepository extends EntityRepository<Assesse,AssessId> {

    @Override
    default  AssessId nextIdentity() {
        return new AssessId();
    }

    @Query("From Assesse where assessId=?1")
    Assesse loadOf(AssessId assessId);

}