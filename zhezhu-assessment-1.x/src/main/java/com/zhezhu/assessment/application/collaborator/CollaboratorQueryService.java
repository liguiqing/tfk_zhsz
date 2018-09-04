package com.zhezhu.assessment.application.collaborator;

import com.zhezhu.assessment.domain.model.collaborator.Assessee;
import com.zhezhu.assessment.domain.model.collaborator.AssesseeRepository;
import com.zhezhu.assessment.domain.model.collaborator.Assessor;
import com.zhezhu.assessment.domain.model.collaborator.AssessorRepository;
import com.zhezhu.assessment.domain.model.collaborator.CollaboratorRole;
import com.zhezhu.share.infrastructure.school.SchoolService;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.school.SchoolId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 评价查询服务
 *
 * @author Liguiqing
 * @since V3.0
 */
@Slf4j
@Service
public class CollaboratorQueryService {

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private AssesseeRepository assesseeRepository;

    @Autowired
    private AssessorRepository assessorRepository;

    public CollaboratorData getAssessorBy(String schoolId, String personId, CollaboratorRole role){
        log.debug("Get Assessor by personId {}",personId);

        Assessor assessor = assessorRepository.findByPersonIdAndSchoolId(new PersonId(personId), new SchoolId(schoolId));
        return CollaboratorData.builder()
                .assessorId(assessor.getAssessorId().id())
                .schoolId(assessor.getCollaborator().getSchoolId().id())
                .build()
                .setAssessorDetail(role,assessor,schoolService);
    }

    public CollaboratorData getAssesseeBy(String schoolId,String personId, CollaboratorRole role){
        log.debug("Get Assessor by personId {}",personId);

        Assessee assessee = assesseeRepository.findByPersonIdAndSchoolId(new PersonId(personId), new SchoolId(schoolId));
        return CollaboratorData.builder()
                .assessorId(assessee.getAssesseeId().id())
                .schoolId(assessee.getCollaborator().getSchoolId().id())
                .build()
                .setAssesseeDetail(role,assessee,schoolService);
    }
}