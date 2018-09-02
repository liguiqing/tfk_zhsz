package com.zhezhu.access.application.school;

import com.zhezhu.access.domain.model.school.ClazzFollowApply;
import com.zhezhu.access.domain.model.school.ClazzFollowApplyRepository;
import com.zhezhu.access.domain.model.school.ClazzFollowAuditRepository;
import com.zhezhu.access.infrastructure.SchoolService;
import com.zhezhu.commons.util.CollectionsUtilWrapper;
import com.zhezhu.share.domain.id.PersonId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Liguiqing
 * @since V3.0
 */

@Slf4j
@Service
public class SchoolApplyAndAuditQueryService {

    @Autowired
    private ClazzFollowApplyRepository clazzFollowApplyRepository;

    @Autowired
    private ClazzFollowAuditRepository clazzFollowAuditRepository;

    @Autowired
    @Qualifier("localSchoolService")
    private SchoolService schoolService;

    /**
     * 查询申请人的所有已经通过审核的班级关注申请
     *
     * @param applierId
     * @return
     */
    public List<ClazzFollowApplyAndAuditData> getAuditedClazzs(String applierId){
        log.debug("Get Audited Clazzs of {}",applierId);

        List<ClazzFollowApply> applies = clazzFollowApplyRepository.findAllByApplierIdAndAuditIdIsNotNull(new PersonId(applierId));
        if(CollectionsUtilWrapper.isNullOrEmpty(applies))
            return new ArrayList<>();

        return applies.stream().map(apply -> {
            String clazzId = apply.getApplyingClazzId().id();
            String clazzName = schoolService.getClazzName(clazzId);
            String schoolId = schoolService.getSchoolIdBy(clazzId);
            String schoolName = schoolService.getSchoolName(schoolId);
            return ClazzFollowApplyAndAuditData.builder()
                    .schoolId(schoolId)
                    .schoolName(schoolName)
                    .clazzId(clazzId)
                    .clazzName(clazzName)
                    .build();
        }).collect(Collectors.toList());
    }
}