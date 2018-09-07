package com.zhezhu.assessment.application.assess;

import com.google.common.collect.Lists;
import lombok.*;

import java.util.List;

/**
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class NewTeacherAssessStudentCommand {
    private String schoolId;

    private String teacherPersonId;

    private String studentPersonId;

    private List<IndexAssess> assesses;

    public NewTeacherAssessStudentCommand addAssess(IndexAssess indexAssess){
        if(this.assesses == null)
            this.assesses = Lists.newArrayList();
        this.assesses.add(indexAssess);
        return this;
    }

    public NewTeacherAssessStudentCommand addAssess(String indexId,double score,String word){
        return this.addAssess(new IndexAssess(indexId, score, word));
    }
}