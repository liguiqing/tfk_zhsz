package com.zhezhu.assessment.domain.model.index;

import com.zhezhu.commons.domain.IdentifiedValueObject;
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