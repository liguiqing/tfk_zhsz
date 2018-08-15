package com.tfk.sm.domain.model.school;

import com.tfk.share.domain.id.school.SchoolId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Slf4j
public class CacheMock {

    @Cacheable(value = "smCache", key = "#schoolId.id")
    public String getValue(SchoolId schoolId){
        log.debug("Test Cache");
        return schoolId.getId();
    }

}