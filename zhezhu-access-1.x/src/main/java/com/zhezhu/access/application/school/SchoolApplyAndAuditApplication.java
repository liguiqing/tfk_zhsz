package com.zhezhu.access.application.school;

import com.zhezhu.access.domain.model.school.ClazzFollowApplyRepository;
import com.zhezhu.access.domain.model.school.ClazzFollowAuditRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Liguiqing
 * @since V3.0
 */

@Slf4j
@Service
public class SchoolApplyAndAuditApplication {

    @Autowired
    private ClazzFollowApplyRepository clazzFollowApplyRepository;

    @Autowired
    private ClazzFollowAuditRepository clazzFollowAuditRepository;

    @Transactional(rollbackFor = Exception.class)
    public void applyFollowClazz(ClazzFollowApplyCommand command ){

    }

}