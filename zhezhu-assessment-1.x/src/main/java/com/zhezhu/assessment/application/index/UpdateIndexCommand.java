package com.zhezhu.assessment.application.index;

import com.zhezhu.assessment.domain.model.index.Index;
import com.zhezhu.assessment.domain.model.index.IndexCategory;
import com.zhezhu.assessment.domain.model.index.IndexScore;
import lombok.*;

/**
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString(callSuper = true)
public class UpdateIndexCommand extends NewIndexCommand {

    private String indexId;

    public UpdateIndexCommand(String parentIndexId, String categoryName, String ownerId,
                              String name,String alias,boolean plus, double score, double weight, String description, String indexId,String group) {
        super(parentIndexId, categoryName, ownerId, name,alias,plus, score, weight, description,group);
        this.indexId = indexId;
    }

    public UpdateIndexCommand build(NewIndexCommand command){
        return new UpdateIndexCommand(command.getParentIndexId(), command.getCategoryName(),
                command.getOwnerId(), command.getName(),command.getName(),true, command.getScore(), command.getWeight(),
                command.getDescription(),this.indexId,command.getGroup());

    }

    public void updateIndex(Index index){
        index.setCategory(IndexCategory.valueOf(super.getCategoryName()));
        index.setDescription(super.getDescription());
        index.setName(super.getName());
        index.setScore(new IndexScore(super.getScore(),super.getWeight()));
    }
}