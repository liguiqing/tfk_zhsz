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
public class NewMedalCommand {
    private String schoolId;

    private String name;

    private int upLeast; //晋级数量

    private String[] indexIds;

    private int level;

    private String category;

    private String highMedalId;

}