package com.zhezhu.access.application.school;

import com.zhezhu.access.domain.model.school.ClazzFollowApply;
import com.zhezhu.access.domain.model.school.ClazzFollowApplyRepository;
import com.zhezhu.access.domain.model.school.ClazzFollowAudit;
import com.zhezhu.access.domain.model.school.ClazzFollowAuditRepository;
import com.zhezhu.commons.util.CollectionsUtilWrapper;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.access.ClazzFollowAuditId;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.infrastructure.school.ClazzData;
import com.zhezhu.share.infrastructure.school.SchoolData;
import com.zhezhu.share.infrastructure.school.SchoolService;
import com.zhezhu.share.infrastructure.school.TeacherData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    private ClazzFollowApplyRepository clazzFollowApplyRepository;

    private ClazzFollowAuditRepository clazzFollowAuditRepository;

    private SchoolService schoolService;

    @Autowired
    public SchoolApplyAndAuditQueryService(ClazzFollowApplyRepository clazzFollowApplyRepository,
                                           ClazzFollowAuditRepository clazzFollowAuditRepository,
                                           SchoolService schoolService) {
        this.clazzFollowApplyRepository = clazzFollowApplyRepository;
        this.clazzFollowAuditRepository = clazzFollowAuditRepository;
        this.schoolService = schoolService;
    }

    /**
     * 查询申请人的所有已经通过审核的班级关注申请
     *
     * @param applierId {@link PersonId#id()}
     * @return list of {@link ClazzFollowApplyAndAuditData}
     */
    public List<ClazzFollowApplyAndAuditData> getAuditedClazzs(String applierId){
        log.debug("Get Audited Clazzs of {}",applierId);

        List<ClazzFollowApply> applies = clazzFollowApplyRepository.findAllByApplierIdAndAuditIdIsNotNull(new PersonId(applierId));
        return toData(applies);
    }

    /**
     * 查询申请人的所有待审核的班级关注申请
     *
     * @param applierId applierId {@link PersonId#id()}
     * @return list of {@link ClazzFollowApplyAndAuditData}
     */
    public List<ClazzFollowApplyAndAuditData> getAuditingClazzs(String applierId){
        log.debug("Get auditing clazzs of {}",applierId);

        List<ClazzFollowApply> applies = clazzFollowApplyRepository.findAllByApplierIdAndAuditIdIsNull(new PersonId(applierId));
        return toData(applies);
    }

    /**
     * 查询申请人的所有待审核的班级关注申请
     *
     * @param page 页码
     * @param size 页容
     * @return list of {@link ClazzFollowApplyAndAuditData}
     */
    public List<ClazzFollowApplyAndAuditData> getAllAuditingClazzApply(int page,int size){
        log.debug("Get all auditing clazzs of page {} size {}",page,size);

        List<ClazzFollowApply> applies = clazzFollowApplyRepository.findAuditingByLimit(page,size);
        return toData(applies);
    }

    private List<ClazzFollowApplyAndAuditData> toData(List<ClazzFollowApply> applies){
        if(CollectionsUtilWrapper.isNullOrEmpty(applies))
            return new ArrayList<>();

        return applies.stream().map(apply -> {
            String clazzId = apply.getApplyingClazzId().id();
            ClazzData clazz = schoolService.getClazz(new ClazzId(clazzId));
            String schoolId = clazz.getSchoolId();
            SchoolData school = schoolService.getSchool(new SchoolId(schoolId));
            String schoolName = school.getName();
            boolean canBeOk = applierIsInClazz(apply,apply.getApplyingClazzId());
            ClazzFollowApplyAndAuditData data =  ClazzFollowApplyAndAuditData.builder()
                    .applyId(apply.getApplyId().id())
                    .schoolId(schoolId)
                    .schoolName(schoolName)
                    .gradeName(clazz.getGradeName())
                    .clazzId(clazzId)
                    .clazzName(clazz.getClazzName())
                    .applierName(apply.getApplierName())
                    .applierPhone(apply.getApplierPhone())
                    .applierId(apply.getApplierId().id())
                    .applyDesc(apply.getCause())
                    .applyDate(apply.getApplyDate())
                    .canBeOk(canBeOk)
                    .build();
            if(apply.isAudited()){
                ClazzFollowAuditId auditId = apply.getAuditId();
                ClazzFollowAudit audit = clazzFollowAuditRepository.loadOf(auditId);
                data.setAuditDate(audit.getAuditDate());
                data.setAuditId(audit.getAuditId().id());
                data.setAuditCause(audit.getDescription());
                data.setOk(audit.isOk());
            }
            return data;
        }).collect(Collectors.toList());
    }

    private boolean applierIsInClazz(ClazzFollowApply apply,ClazzId clazzId) {
        List<TeacherData> teachers = schoolService.getClazzTeachers(apply.getApplyingClazzId());
        if (CollectionsUtilWrapper.isNullOrEmpty(teachers))
            return false;

        for (TeacherData teacher : teachers) {
            boolean b1 = teacher.sameNameAs(apply.getApplierName());
            boolean b2 = teacher.samePhoneAs(apply.getApplierPhone());
            if(b1 && b2)
                return true;
        }

        return false;
    }
}