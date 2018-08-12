package com.tfk.assessment.domain.model.assesse;

import com.tfk.assessment.domain.model.collaborator.Assessee;
import com.tfk.assessment.domain.model.collaborator.Assessor;
import com.tfk.assessment.domain.model.index.Index;
import com.tfk.assessment.domain.model.index.IndexRepository;
import com.tfk.commons.util.DateUtilWrapper;
import com.tfk.share.domain.id.index.IndexId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Component("AssesseService")
public class AssesseService {

    @Autowired
    private AssesseRepository repository;

    @Autowired
    private IndexRepository indexRepository;

    public Assesse newAssesse(Index index, Assessor assessor, Assessee assessee, double score){
        return Assesse.builder().assessId(repository.nextIdentity())
                .indexId(index.getIndexId())
                .assessorId(assessor.getAssessorId())
                .assesseeId(assessee.getAssesseeId())
                .category(index.getCategory())
                .doneDate(DateUtilWrapper.now())
                .score(score)
                .build();
    }

    public List<Assesse> newAssesses(IndexId indexId, Assessor assessor, Assessee assessee, double score){
        Index topIndex = indexRepository.loadOf(indexId);
        if(!topIndex.isTop())
            return null;

        return toChildrenAssess(topIndex, assessor, assessee, score);
    }

    private List<Assesse> toChildrenAssess(Index topIndex, Assessor assessor, Assessee assessee,double score){
        ArrayList<Assesse> tops = new ArrayList<>(topIndex.allSize());
        tops.add(newAssesse(topIndex, assessor, assessee, score));
        if(topIndex.hasChildren()){
            ArrayList<Assesse> assesses = new ArrayList(topIndex.size());
            Set<Index> children = topIndex.getChildren();
            for(Index child:children){
                double childScore = child.calRealScore(score);
                assesses.addAll(toChildrenAssess(child, assessor, assessee, childScore));
            }
        }
        return tops;
    }
}