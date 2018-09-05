package com.zhezhu.assessment.application.assess;

import com.zhezhu.assessment.domain.model.assesse.Assess;
import com.zhezhu.assessment.domain.model.assesse.AssessRepository;
import com.zhezhu.assessment.domain.model.assesse.AssessService;
import com.zhezhu.assessment.domain.model.collaborator.*;
import com.zhezhu.assessment.domain.model.index.Index;
import com.zhezhu.assessment.domain.model.index.IndexRepository;
import com.zhezhu.assessment.domain.model.medal.AwardRepository;
import com.zhezhu.assessment.infrastructure.message.AssessMessage;
import com.zhezhu.commons.message.MessageEvent;
import com.zhezhu.commons.message.MessageListener;
import com.zhezhu.commons.message.Messagingable;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.assessment.AssessId;
import com.zhezhu.share.domain.id.assessment.AssesseeId;
import com.zhezhu.share.domain.id.assessment.AssessorId;
import com.zhezhu.share.domain.id.index.IndexId;
import com.zhezhu.share.domain.id.school.SchoolId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Liguiqing
 * @since V3.0
 */

@Service
@Slf4j
public class AssessApplicationService {

    @Autowired
    private AssessService assesseService;

    @Autowired
    private IndexRepository indexRepository;

    @Autowired
    private AssessRepository assessRepository;

    @Autowired
    private AssessorRepository assessorRepository;

    @Autowired
    private AssesseeRepository assesseeRepository;

    @Autowired
    private AwardRepository awardRepository;

    @Autowired(required = false)
    private MessageListener messageListener;

    @Transactional(rollbackFor = Exception.class)
    public void assess(NewAssessCommand command){
        log.debug("New assess for {}",command);

        Index index = indexRepository.loadOf(new IndexId(command.getIndexId()));
        Assessor assessor = assessorRepository.loadOf(new AssessorId(command.getAssessorId()));
        Assessee assessee = assesseeRepository.loadOf(new AssesseeId(command.getAssesseeId()));
        List<Assess> assesses = assesseService.newAssesses(index, assessor, assessee, command.getScore());
        assesses.forEach(assess -> assessRepository.save(assess));
        sendMessage(new AssessMessage(index,assessee,command.getScore()));
    }

    /**
     * 老师评价学生
     *
     * @param command
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String teacherAssessStudent(NewTeacherAssessStudentCommand command){
        log.debug("Teacher assess Student {}",command);
        AssessId assessId = assessRepository.nextIdentity();

        PersonId teacherPersonId = new PersonId(command.getTeacherPersonId());
        PersonId studentPersonId = new PersonId(command.getStudentPersonId());
        SchoolId schoolId = new SchoolId(command.getSchoolId());
        Assessor assessor = teacherAsAssessor(teacherPersonId, schoolId);
        Assessee assessee = assesseeRepository.findByPersonIdAndSchoolId(studentPersonId, schoolId);
        Index index = null;
        if(command.getIndexId() != null){
            index = indexRepository.loadOf(new IndexId(command.getIndexId()));
        }
        Assess assess = assesseService.newAssess(index, assessor, assessee, command.getScore(), command.getWord());

        if(index != null){
            sendMessage(new AssessMessage(index,assessee,command.getScore()));
        }
        assessRepository.save(assess);
        return assessId.id();
    }

    private Assessor teacherAsAssessor(PersonId teacherPersonId,SchoolId schoolId){
        Assessor assessor = assessorRepository.findByPersonIdAndSchoolId(teacherPersonId, schoolId);
        if(assessor == null){
            assessor = Assessor.builder()
                    .assessorId(assessorRepository.nextIdentity())
                    .personId(teacherPersonId)
                    .role(CollaboratorRole.Teacher.name())
                    .schoolId(schoolId)
                    .build();
            assessorRepository.save(assessor);
        }
        return assessor;
    }

    private Assessee studentAsAssessee(PersonId stuentPersonId,SchoolId schoolId){
        Assessee assessee = assesseeRepository.findByPersonIdAndSchoolId(stuentPersonId, schoolId);
        if(assessee == null){
            assessee = Assessee.builder()
                    .assesseeId(assesseeRepository.nextIdentity())
                    .personId(stuentPersonId)
                    .role(CollaboratorRole.Student.name())
                    .schoolId(schoolId)
                    .build();
        }
        return assessee;
    }


    private void sendMessage(Messagingable messagingable){
        if(this.messageListener != null){
            this.messageListener.addEvent(new MessageEvent(messagingable));
        }
    }
}