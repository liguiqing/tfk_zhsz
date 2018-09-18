package com.zhezhu.assessment.application.index;

import com.google.common.collect.Lists;
import com.zhezhu.assessment.domain.model.index.Index;
import com.zhezhu.assessment.domain.model.index.IndexCategory;
import com.zhezhu.commons.util.ClientType;
import com.zhezhu.commons.util.CollectionsUtilWrapper;
import com.zhezhu.share.domain.id.identityaccess.TenantId;
import com.zhezhu.share.domain.id.index.IndexId;
import lombok.*;

import java.util.List;

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

    private List<IndexWebResource> webResources;

    public NewIndexCommand iconToWeChatApp(String icon){
        if(this.webResources == null)
            this.webResources = Lists.newArrayList();
        this.webResources.add(new IndexWebResource("icon", icon,ClientType.WeChatApp.name()));
        return this;
    }

    public Index toStIndex(IndexId indexId){
        Index index =  Index.builder()
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
        return addWebResource(index);
    }

    private Index addWebResource(Index index){
        if(CollectionsUtilWrapper.isNotNullAndNotEmpty(webResources)){
            webResources.forEach(wr->index.addWebResource(toClientType(wr.getCategory()),wr.getName(),wr.getValue()));
        }
        return index;
    }

    private ClientType toClientType(String categoryName){
        return ClientType.valueOf(categoryName);
    }

    public Index toOwnerIndex(IndexId indexId){
        Index index = Index.builder()
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
        return addWebResource(index);
    }

}