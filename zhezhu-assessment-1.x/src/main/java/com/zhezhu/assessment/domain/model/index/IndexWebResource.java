package com.zhezhu.assessment.domain.model.index;

import com.zhezhu.commons.domain.IdentifiedValueObject;
import com.zhezhu.commons.util.ClientType;
import com.zhezhu.share.domain.id.index.IndexId;
import lombok.*;

/**
 * 评价指标web资源
 *
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@EqualsAndHashCode(of={"indexId","category"},callSuper = false)
@ToString
public class IndexWebResource extends IdentifiedValueObject {

    private Index index;

    private ClientType category;

    private String name;

    private String value;

    public boolean sameAs(ClientType category,String name){
        return this.category.equals(category) && this.name.equalsIgnoreCase(name);
    }
}