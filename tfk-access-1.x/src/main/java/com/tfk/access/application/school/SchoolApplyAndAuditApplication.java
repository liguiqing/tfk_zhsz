package com.tfk.access.application.school;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Liguiqing
 * @since V3.0
 */

@Slf4j
@Service
public class SchoolApplyAndAuditApplication {

    @Transactional(rollbackFor = Exception.class)
    public void applyFollowClazz(ClazzFollowApplyCommand command ){

    }

}