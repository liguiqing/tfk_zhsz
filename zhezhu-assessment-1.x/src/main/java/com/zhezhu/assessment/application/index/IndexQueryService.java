package com.zhezhu.assessment.application.index;

import com.zhezhu.assessment.domain.model.index.Index;
import com.zhezhu.assessment.domain.model.index.IndexRepository;
import com.zhezhu.commons.util.CollectionsUtilWrapper;
import com.zhezhu.share.domain.id.identityaccess.TenantId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Liguiqing
 * @since V3.0
 */

@Slf4j
@Service
@Transactional(readOnly = true)
public class IndexQueryService {

    @Autowired
    private IndexRepository indexRepository;

    public List<IndexData> getOwnerIndexes(String ownerId, String group, boolean withChildren){
        log.debug("Get owner indexes {} {} {}",ownerId,group,withChildren);

        List<Index> indexes = indexRepository.findAllByOwnerAndParentIsNullAndGroup(new TenantId(ownerId),group);
        //如果没有自定义指标,取公共指标
        if(CollectionsUtilWrapper.isNullOrEmpty(indexes)){
            indexes = indexRepository.findAllByOwnerIsNullAndParentIsNull();
        }

        if(withChildren){
            return getIndexChildren(indexes);
        }

        return indexes.stream().map(index ->toIndexData(index)).collect(Collectors.toList());
    }

    private List<IndexData> getIndexChildren(Collection<Index> indexes) {
        return indexes.stream().map(index ->{
            IndexData data = toIndexData(index);
            if(index.hasChildren()){
                data.setChildren(getIndexChildren(index.getChildren()));
            }
            return data;
        }).collect(Collectors.toList());
    }

    private IndexData toIndexData(Index index){
        return IndexData.builder()
                .name(index.getName())
                .alias(index.getAlias())
                .plus(index.isPlus())
                .indexId(index.getIndexId().id())
                .categoryName(index.getCategory())
                .score(index.getScore().getScore())
                .weight(index.getScore().getWeight())
                .description(index.getDescription())
                .group(index.getGroup())
                .build().addWeResources(index.getWebResources());
    }
}