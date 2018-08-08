package com.tfk.sm.domain.model.clazz;

import com.tfk.commons.domain.EntityRepository;
import com.tfk.share.domain.id.school.ClazzId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

/**
 * @author Liguiqing
 * @since V3.0
 */
@NoRepositoryBean
public interface ClazzRepository<T extends Clazz> extends EntityRepository<T,ClazzId> {

    default  ClazzId nextIdentity(){
        return new ClazzId();
    }

    boolean supports(Clazz clazz);

    boolean supports(Class<? extends T> clazz);

    @Query(value = "from Clazz where clazzId=?1 and removed=0")
    T loadOf(ClazzId clazzId);

    @Modifying
    @Query(value = "update sm_clazz set removed = 1 where clazzId=:clazzId",nativeQuery = true)
    void delete(@Param("clazzId") String clazzId);

}