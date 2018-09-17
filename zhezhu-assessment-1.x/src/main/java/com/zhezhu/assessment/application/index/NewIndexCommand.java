package com.zhezhu.assessment.application.index;

import com.zhezhu.assessment.domain.model.index.Index;
import com.zhezhu.assessment.domain.model.index.IndexCategory;
import com.zhezhu.share.domain.id.identityaccess.TenantId;
import com.zhezhu.share.domain.id.index.IndexId;
import lombok.*;

/**
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class NewIndexCommand {

    private String parentIndexId; //上级指标

    private String categoryName;

    private String  ownerId;

    private String name;

    private String alias;

    private boolean plus;

    private double score;

    private double weight;

    private String description;

    private String group;

    private String icon;

    public Index toStIndex(IndexId indexId){
        return Index.builder()
                .indexId(indexId)
                .name(name)
                .plus(plus)
                .alias(alias)
                .category(IndexCategory.valueOf(this.categoryName))
                .score(score)
                .weight(weight)
                .description(description)
                .group(this.group)
                .build();
    }

    public Index toOwnerIndex(IndexId indexId){
        return Index.builder()
                .indexId(indexId)
                .owner(new TenantId(ownerId))
                .name(name)
                .plus(plus)
                .alias(alias)
                .category(IndexCategory.valueOf(this.categoryName))
                .score(score)
                .weight(weight)
                .description(description)
                .group(this.group)
                .build();
    }

}