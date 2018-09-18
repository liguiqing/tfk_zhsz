package com.zhezhu.assessment.application.index;

import com.google.common.collect.Lists;
import com.zhezhu.assessment.domain.model.index.Index;
import com.zhezhu.commons.port.adaptor.http.controller.DataFilter;
import com.zhezhu.commons.util.ClientType;
import com.zhezhu.commons.util.CollectionsUtilWrapper;
import com.zhezhu.commons.util.NumberUtilWrapper;
import lombok.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString(exclude = {"children"})
public class IndexData implements DataFilter<ClientType> {

    private String indexId;

    private String categoryName;

    private String name;

    private String alias;

    private boolean plus;

    private double score;

    private double weight;

    private String description;

    private String group;

    private List<IndexData> children;

    private List<IndexWebResource> webResources;

    private String recommendIcon;

    public double randamScore(){
        double d = this.plus ? 1 : -1;
        return NumberUtilWrapper.randomBetween(0,this.score) * d;
    }

    public IndexData addWebResource(String name, String value, ClientType category){
        if(this.webResources == null) {
            this.webResources = Lists.newArrayList();
        }
        this.webResources.add(new IndexWebResource(name, value, category.name()));
        return this;
    }

    public IndexData addWeResources(Collection<com.zhezhu.assessment.domain.model.index.IndexWebResource> wrs){
        if(CollectionsUtilWrapper.isNullOrEmpty(wrs)){
            return this;
        }

        wrs.forEach(iwr->addResource(iwr));
        return this;
    }

    private void addResource(com.zhezhu.assessment.domain.model.index.IndexWebResource iwr){
        this.addWebResource(iwr.getName(), iwr.getValue(), iwr.getCategory());
    }

    @Override
    public void filter(ClientType clientType) {
        if(CollectionsUtilWrapper.isNullOrEmpty(this.webResources))
            return ;
        for(IndexWebResource iwr:this.webResources){
            if(iwr.getCategory().equalsIgnoreCase(clientType.name())){
                this.recommendIcon = iwr.getValue();
                break;
            }
        }
    }
}