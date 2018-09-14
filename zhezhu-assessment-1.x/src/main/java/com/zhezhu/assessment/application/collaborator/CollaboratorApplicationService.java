package com.zhezhu.assessment.application.collaborator;

import com.zhezhu.assessment.domain.model.collaborator.Assessee;
import com.zhezhu.assessment.domain.model.collaborator.AssesseeRepository;
import com.zhezhu.assessment.domain.model.collaborator.Assessor;
import com.zhezhu.assessment.domain.model.collaborator.AssessorRepository;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.assessment.AssesseeId;
import com.zhezhu.share.domain.id.assessment.AssessorId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.infrastructure.school.SchoolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * 评价协作服务
 *
 * @author Liguiqing
 * @since V3.0
 */
@Slf4j
@Service
public class CollaboratorApplicationService {

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private AssesseeRepository assesseeRepository;

    @Autowired
    private AssessorRepository assessorRepository;


    @Transactional(rollbackFor = Exception.class)
    public void toCollaborator(String schoolId){
        log.debug("School {} Teacher to Assessor,Student to Assessee",schoolId);
        studentToAssessee(schoolId);
        teacherToAssessor(schoolId);
   }

    @Transactional(rollbackFor = Exception.class)
    public void studentToAssessee(String schoolId){
        log.debug("School {} students to assessees");
        SchoolId schoolId_ = new SchoolId(schoolId);
        List<PersonId> studentPersonIds = schoolService.getAllStudentPersonIds(schoolId_);
        toAssessee(studentPersonIds,schoolId_,"Student");
    }

    @Transactional(rollbackFor = Exception.class)
    public void teacherToAssessor(String schoolId){
        log.debug("School {} teacher to assessors");
        SchoolId schoolId_ = new SchoolId(schoolId);
        List<PersonId> teacherPersonIds = schoolService.getTeacherPersonIds(schoolId_);
        toAssessor(teacherPersonIds, schoolId_, "Teacher");
    }

    private void toAssessee(Collection<PersonId> personIds,SchoolId schoolId,String role){
        for(PersonId pid:personIds){
            AssesseeId assesseeId = assesseeRepository.nextIdentity();
            Assessee assessee = Assessee.builder().assesseeId(assesseeId).personId(pid).role(role).schoolId(schoolId).build();
            assesseeRepository.save(assessee);
        }
    }

    private void toAssessor(Collection<PersonId> personIds,SchoolId schoolId,String role){
        for(PersonId pid:personIds){
            AssessorId assessorId = assessorRepository.nextIdentity();
            Assessor assessor = Assessor.builder().assessorId(assessorId).personId(pid).role(role).schoolId(schoolId).build();
            assessorRepository.save(assessor);
        }
    }
}