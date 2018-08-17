package com.tfk.assessment.application.index;

import com.tfk.assessment.domain.model.index.Index;
import com.tfk.assessment.domain.model.index.IndexCategory;
import com.tfk.assessment.domain.model.index.IndexScore;
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
                              String name, double score, double weight, String description, String indexId) {
        super(parentIndexId, categoryName, ownerId, name, score, weight, description);
        this.indexId = indexId;
    }

    public UpdateIndexCommand build(NewIndexCommand command){
        return new UpdateIndexCommand(command.getParentIndexId(), command.getCategoryName(),
                command.getOwnerId(), command.getName(), command.getScore(), command.getWeight(),
                command.getDescription(),this.indexId);

    }

    public void updateIndex(Index index){
        index.setCategory(IndexCategory.getByName(super.getCategoryName()));
        index.setDescription(super.getDescription());
        index.setName(super.getName());
        index.setScore(new IndexScore(super.getScore(),super.getWeight()));
    }
}