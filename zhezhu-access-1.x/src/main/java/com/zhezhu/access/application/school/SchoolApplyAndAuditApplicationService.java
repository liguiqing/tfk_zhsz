package com.zhezhu.access.application.school;

import com.zhezhu.access.domain.model.school.ClazzFollowApply;
import com.zhezhu.access.domain.model.school.ClazzFollowApplyRepository;
import com.zhezhu.access.domain.model.school.ClazzFollowAudit;
import com.zhezhu.access.domain.model.school.ClazzFollowAuditRepository;
import com.zhezhu.access.domain.model.wechat.WeChat;
import com.zhezhu.access.domain.model.wechat.WeChatCategory;
import com.zhezhu.access.domain.model.wechat.WeChatRepository;
import com.zhezhu.commons.AssertionConcerns;
import com.zhezhu.commons.util.CollectionsUtilWrapper;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.access.ClazzFollowApplyId;
import com.zhezhu.share.domain.id.access.ClazzFollowAuditId;
import com.zhezhu.share.infrastructure.school.SchoolService;
import com.zhezhu.share.infrastructure.school.TeacherData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * 学校申请及审核服务
 *
 * @author Liguiqing
 * @since V3.0
 */

@Slf4j
@Service
public class SchoolApplyAndAuditApplicationService {

    private ClazzFollowApplyRepository clazzFollowApplyRepository;

    private ClazzFollowAuditRepository clazzFollowAuditRepository;

    private WeChatRepository weChatRepository;

    private SchoolService schoolService;

    @Autowired
    public SchoolApplyAndAuditApplicationService(ClazzFollowApplyRepository clazzFollowApplyRepository,
                                                 ClazzFollowAuditRepository clazzFollowAuditRepository,
                                                 WeChatRepository weChatRepository,
                                                 SchoolService schoolService) {
        this.clazzFollowApplyRepository = clazzFollowApplyRepository;
        this.clazzFollowAuditRepository = clazzFollowAuditRepository;
        this.weChatRepository = weChatRepository;
        this.schoolService = schoolService;
    }

    /**
     * 班级关注申请
     *
     * @param command {@link ClazzFollowApplyCommand}
     * @return value of {@link ClazzFollowApplyId}
     */
    @Transactional(rollbackFor = Exception.class)
    public String followClazzApply(ClazzFollowApplyCommand command){
        log.debug("Clazz follow applay {}",command);

        ClazzFollowApplyId applyId = clazzFollowApplyRepository.nextIdentity();
        ClazzFollowApply apply = command.toApply(applyId);

        clazzFollowApplyRepository.save(apply);
        return applyId.id();
    }

    /**
     * 取消班级关注申请
     *
     * @param applyId {@link ClazzFollowApplyId}
     */
    @Transactional(rollbackFor = Exception.class)
    public void followClazzApplyCancel(String applyId){
        log.debug("Cancel clazz follow applay {}",applyId);

        ClazzFollowApplyId applyId1 = new ClazzFollowApplyId(applyId);
        ClazzFollowApply apply = clazzFollowApplyRepository.loadOf(applyId1);
        AssertionConcerns.assertArgumentTrue((apply != null && apply.isAudited()),"ac-01-001");
        clazzFollowApplyRepository.delete(applyId1);
    }

    /**
     * 审核班级关注申请
     *
     * @param command {@link ClazzFollowAuditCommand}
     * @return value of {@link ClazzFollowAuditId}
     */
    @Transactional(rollbackFor = Exception.class)
    public String followClazzAudit(ClazzFollowAuditCommand command){
        log.debug(" Clazz apply audit {}",command);

        ClazzFollowAuditId auditId = clazzFollowAuditRepository.nextIdentity();
        ClazzFollowAudit audit = command.toAudit(auditId);

        ClazzFollowApply apply = clazzFollowApplyRepository.loadOf(audit.getApplyId());

        AssertionConcerns.assertArgumentNotNull(apply,"ac-01-002");
        AssertionConcerns.assertArgumentFalse(apply.isAudited(),"ac-01-002");

        clazzFollowAuditRepository.save(audit);
        apply.audit(audit);
        syncWechatPersonId(apply);
        clazzFollowApplyRepository.save(apply);

        return auditId.id();
    }

    private void syncWechatPersonId(ClazzFollowApply apply ){
        log.debug(" Sync wechat personId {}",apply.getApplierId().id());

        WeChat weChat = weChatRepository.findByPersonIdAndCategoryEquals(apply.getApplierId(), WeChatCategory.Teacher);
        List<TeacherData> teachers = schoolService.getClazzTeachers(apply.getApplyingClazzId());
        if(CollectionsUtilWrapper.isNotNullAndNotEmpty(teachers)){
            for(TeacherData teacher:teachers){
                boolean pb = teacher.samePhoneAs(weChat.getPhone());
                boolean nb = teacher.sameNameAs(weChat.getName());
                if(pb && nb){
                    weChat.upatePersonId(new PersonId(teacher.getPersonId()));
                    weChatRepository.save(weChat);
                    apply.updateApplierId(weChat.getPersonId());
                    break;
                }
            }
        }

    }

    /**
     * 取消班级关注申请审核
     *
     * @param auditId {@link ClazzFollowAuditId}
     */
    @Transactional(rollbackFor = Exception.class)
    public void followClazzAuditCancel(String auditId){
        log.debug(" Clazz apply audit cancel {}",auditId);
        ClazzFollowAuditId auditId1 = new ClazzFollowAuditId(auditId);
        ClazzFollowAudit audit = clazzFollowAuditRepository.loadOf(auditId1);
        AssertionConcerns.assertArgumentTrue((audit != null),"ac-01-003");
        ClazzFollowApply apply = clazzFollowApplyRepository.loadOf(audit.getApplyId());

        apply.cancelAudit(audit);
        clazzFollowAuditRepository.delete(auditId1);
        clazzFollowApplyRepository.save(apply);
    }

}