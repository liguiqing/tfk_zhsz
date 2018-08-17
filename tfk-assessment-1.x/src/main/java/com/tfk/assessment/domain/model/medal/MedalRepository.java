package com.tfk.assessment.domain.model.medal;

import com.tfk.commons.domain.EntityRepository;
import com.tfk.share.domain.id.medal.MedalId;
import com.tfk.share.domain.id.school.SchoolId;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Repository("MedalRepository")
public interface MedalRepository extends EntityRepository<Medal,MedalId> {

    @Override
    default MedalId nextIdentity() {
        return new MedalId();
    }

    @Override
    @CacheEvict(value = "asCache", key="#p0.medalId.id")
    void save(Medal medal);

    @Cacheable(value = "asCache",key = "#p0.id",unless = "#result == null")
    @Query("From Medal where  medalId=?1")
    Medal loadOf(MedalId medalId);

    @CacheEvict(value = "asCache",key="#p0.id")
    @Modifying
    @Query(value = "DELETE from Medal where medalId=?1")
    void delete(MedalId medalId);

    List<Medal> findMedalsBySchoolId(SchoolId schoolId);

    List<Medal> findMedalsBySchoolIdAndHigh(SchoolId schoolId,Medal hight);
}