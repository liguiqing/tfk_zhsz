package com.tfk.assessment.domain.model.assesse;

import com.tfk.assessment.domain.model.collaborator.Assessee;
import com.tfk.assessment.domain.model.collaborator.Assessor;
import com.tfk.assessment.domain.model.index.Index;
import com.tfk.assessment.domain.model.index.IndexRepository;
import com.tfk.commons.util.DateUtilWrapper;
import com.tfk.share.domain.id.index.IndexId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Slf4j
@Component("AssesseService")
public class AssesseService {

    @Autowired
    private AssessRepository repository;

    @Autowired
    private IndexRepository indexRepository;

    public Assess newAssesse(Index index, Assessor assessor, Assessee assessee, double score){
        return Assess.builder().assessId(repository.nextIdentity())
                .indexId(index.getIndexId())
                .assessorId(assessor.getAssessorId())
                .assesseeId(assessee.getAssesseeId())
                .category(index.getCategory())
                .doneDate(DateUtilWrapper.now())
                .score(score)
                .build();
    }

    public List<Assess> newAssesses(IndexId indexId, Assessor assessor, Assessee assessee, double score){
        Index topIndex = indexRepository.loadOf(indexId);
        if(!topIndex.isTop())
            return null;

        return toChildrenAssess(topIndex, assessor, assessee, score);
    }

    private List<Assess> toChildrenAssess(Index topIndex, Assessor assessor, Assessee assessee, double score){
        ArrayList<Assess> tops = new ArrayList<>(topIndex.allSize());
        tops.add(newAssesse(topIndex, assessor, assessee, score));
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