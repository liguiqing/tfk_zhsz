package com.zhezhu.assessment.domain.model.assesse;

import com.zhezhu.assessment.domain.model.collaborator.Assessee;
import com.zhezhu.assessment.domain.model.collaborator.Assessor;
import com.zhezhu.assessment.domain.model.index.Index;
import com.zhezhu.assessment.domain.model.index.IndexRepository;
import com.zhezhu.commons.util.DateUtilWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Slf4j
@Component("AssesseService")
public class AssessService {

    @Autowired
    private AssessRepository repository;

    @Autowired
    private IndexRepository indexRepository;

    public Assess newAssess(Index index, Assessor assessor, Assessee assessee, double score){
        return Assess.builder().assessId(repository.nextIdentity())
                .indexId(index.getIndexId())
                .assessorId(assessor.getAssessorId())
                .assesseeId(assessee.getAssesseeId())
                .category(index.getCategory())
                .doneDate(DateUtilWrapper.now())
                .score(score)
                .build();
    }

    public Assess newAssess(Index index, Assessor assessor, Assessee assessee, double score,String word){
        if(index == null)
            return newAssess(assessor, assessee, word);
        if(word == null)
            return newAssess(index,assessor,assessee,score);

        return Assess.builder().assessId(repository.nextIdentity())
                .indexId(index.getIndexId())
                .assessorId(assessor.getAssessorId())
                .assesseeId(assessee.getAssesseeId())
                .category(index.getCategory())
                .doneDate(DateUtilWrapper.now())
                .score(score)
                .word(word)
                .build();
    }

    public Assess newAssess(Assessor assessor, Assessee assessee, String word){
        return Assess.builder().assessId(repository.nextIdentity())
                .assessorId(assessor.getAssessorId())
                .assesseeId(assessee.getAssesseeId())
                .doneDate(DateUtilWrapper.now())
                .word(word)
                .build();
    }

    public List<Assess> newAssesses(Index index, Assessor assessor, Assessee assessee, double score){
        if(!index.isTop()) {
            Assess assess = newAssess(index, assessor, assessee,score);
            return Arrays.asList(assess);
        }

        return toChildrenAssess(index, assessor, assessee, score);
    }

    private List<Assess> toChildrenAssess(Index topIndex, Assessor assessor, Assessee assessee, double score){
        ArrayList<Assess> tops = new ArrayList<>(topIndex.allSize());
        tops.add(newAssess(topIndex, assessor, assessee, score));
        if(topIndex.hasChildren()){
            ArrayList<Assess> assesses = new ArrayList(topIndex.size());
            Set<Index> children = topIndex.getChildren();
            for(Index child:children){
                double childScore = child.calRealScore(score);
                assesses.addAll(toChildrenAssess(child, assessor, assessee, childScore));
            }
        }
        return tops;
    }
}