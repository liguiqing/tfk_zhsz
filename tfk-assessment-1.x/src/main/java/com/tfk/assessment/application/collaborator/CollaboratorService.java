package com.tfk.assessment.application.collaborator;

import com.tfk.assessment.domain.model.collaborator.Assessee;
import com.tfk.assessment.domain.model.collaborator.AssesseeRepository;
import com.tfk.assessment.domain.model.collaborator.Assessor;
import com.tfk.assessment.domain.model.collaborator.AssessorRepository;
import com.tfk.assessment.infrastructure.school.SchoolService;
import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.id.assessment.AssesseeId;
import com.tfk.share.domain.id.assessment.AssessorId;
import com.tfk.share.domain.id.school.SchoolId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Slf4j
@Service
public class CollaboratorService {

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private AssesseeRepository assesseeRepository;

    @Autowired
    private AssessorRepository assessorRepository;


    @Transactional(rollbackFor = Exception.class)
    public void toCollaborator(String schoolId){
        log.debug("");

        SchoolId schoolId1 = new SchoolId(schoolId);
        List<PersonId> teacherPersonIds = schoolService.getTeacherPersonIds(schoolId1);
        List<PersonId> studentPersonIds = schoolService.getStudentPersonIds(schoolId1);
        for(PersonId pid:teacherPersonIds){
            AssessorId assessorId = assessorRepository.nextIdentity();
            Assessor assessor = Assessor.builder().assessorId(assessorId).personId(pid).role("Teacher").schoolId(schoolId1).build();
            assessorRepository.save(assessor);
        }

        for(PersonId pid:studentPersonIds){
            AssesseeId assesseeId = assesseeRepository.nextIdentity();
            Assessee assessee = Assessee.builder().assesseeId(assesseeId).personId(pid).role("Student").schoolId(schoolId1).build();
            assesseeRepository.save(assessee);
        }
    }
}