package com.zhezhu.assessment.application.medal;

import lombok.*;

/**
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class UpdateMedalCommand {

    private String medalId;

    private String name;

    private int upLeast; //晋级数量

    private String[] indexIds;

}