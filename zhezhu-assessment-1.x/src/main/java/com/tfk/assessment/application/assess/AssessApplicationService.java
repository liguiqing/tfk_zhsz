package com.tfk.assessment.application.assess;

import com.tfk.assessment.domain.model.assesse.Assess;
import com.tfk.assessment.domain.model.assesse.AssessRepository;
import com.tfk.assessment.domain.model.assesse.AssessService;
import com.tfk.assessment.domain.model.collaborator.Assessee;
import com.tfk.assessment.domain.model.collaborator.AssesseeRepository;
import com.tfk.assessment.domain.model.collaborator.Assessor;
import com.tfk.assessment.domain.model.collaborator.AssessorRepository;
import com.tfk.assessment.domain.model.index.Index;
import com.tfk.assessment.domain.model.index.IndexRepository;
import com.tfk.assessment.domain.model.medal.AwardRepository;
import com.tfk.assessment.infrastructure.message.AssessMessage;
import com.tfk.commons.message.MessageEvent;
import com.tfk.commons.message.MessageListener;
import com.tfk.commons.message.Messagingable;
import com.tfk.share.domain.id.assessment.AssesseeId;
import com.tfk.share.domain.id.assessment.AssessorId;
import com.tfk.share.domain.id.index.IndexId;
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
    public void assess(NewAssessCommand command)throws Exception{
        log.debug("New assess for {}",command);

        Index index = indexRepository.loadOf(new IndexId(command.getIndexId()));
        Assessor assessor = assessorRepository.loadOf(new AssessorId(command.getAssessorId()));
        Assessee assessee = assesseeRepository.loadOf(new AssesseeId(command.getAssesseeId()));
        List<Assess> assesses = assesseService.newAssesses(index, assessor, assessee, command.getScore());
        assesses.forEach(assess -> assessRepository.save(assess));
        sendMessage(new AssessMessage(index,assessee,command.getScore()));
    }

    private void sendMessage(Messagingable messagingable){
        if(this.messageListener != null){
            this.messageListener.addEvent(new MessageEvent(messagingable));
        }
    }
}