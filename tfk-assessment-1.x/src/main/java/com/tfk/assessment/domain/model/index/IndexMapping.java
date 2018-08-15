package com.tfk.assessment.domain.model.index;

import com.tfk.commons.domain.IdentifiedValueObject;
import com.tfk.share.domain.id.index.IndexId;
import lombok.*;

/**
 * 定制指标与标准指标映射
 *
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class IndexMapping extends IdentifiedValueObject {

    private Index mapper;

    private Index mapped;

    private IndexScore score;

}