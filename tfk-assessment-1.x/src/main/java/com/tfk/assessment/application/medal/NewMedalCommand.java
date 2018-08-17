package com.tfk.assessment.application.medal;

import com.tfk.assessment.domain.model.medal.Medal;
import com.tfk.share.domain.id.school.SchoolId;
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