package com.zhezhu.assessment.application.index;

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
@ToString(exclude = {"children"})
public class IndexData {

    private String indexId;

    private String categoryName;

    private String name;

    private double score;

    private double weight;

    private String description;

    private String group;

    private List<IndexData> children;

}