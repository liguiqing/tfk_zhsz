package com.zhezhu.assessment.application.collaborator;

import com.zhezhu.assessment.domain.model.collaborator.*;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.infrastructure.school.SchoolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 评价查询服务
 *
 * @author Liguiqing
 * @since V3.0
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class CollaboratorQueryService {

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private AssesseeRepository assesseeRepository;

    @Autowired
    private AssessorRepository assessorRepository;

    public CollaboratorData getAssessorBy(String schoolId, String personId, CollaboratorRole role){
        return this.getAssessorBy(schoolId, personId, role, true);
    }

    public CollaboratorData getAssessorBy(String schoolId, String personId, CollaboratorRole role,boolean withDetail){
        log.debug("Get Assessor by personId {}",personId);

        Assessor assessor = assessorRepository.findByPersonIdAndSchoolId(new PersonId(personId), new SchoolId(schoolId));
        CollaboratorData data =  CollaboratorData.builder()
                .assessorId(assessor.getAssessorId().id())
                .schoolId(assessor.getCollaborator().getSchoolId().id())
                .build();
        if(withDetail)
                data.setAssessorDetail(role,assessor,schoolService);
        return data;
    }

    public CollaboratorData getAssesseeBy(String schoolId,String personId, CollaboratorRole role){
        return getAssesseeBy(schoolId, personId, role, true);
    }

    public CollaboratorData getAssesseeBy(String schoolId,String personId, CollaboratorRole role,boolean withDetail){
        log.debug("Get Assessor by personId {}",personId);

        Assessee assessee = assesseeRepository.findByPersonIdAndSchoolId(new PersonId(personId), new SchoolId(schoolId));
        CollaboratorData data =  CollaboratorData.builder()
                .assesseeId(assessee.getAssesseeId().id())
                .schoolId(assessee.getCollaborator().getSchoolId().id())
                .build();

        if(withDetail)
            data.setAssesseeDetail(role,assessee,schoolService);
        return data;
    }
}