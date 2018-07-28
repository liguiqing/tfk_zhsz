package com.tfk.sm.domain.model.clazz;

import com.tfk.commons.domain.ValueObject;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.school.Grade;
import com.tfk.share.domain.school.StudyYear;

/**
 * 班级史
 *
 * @author Liguiqing
 * @since V3.0
 */

public class ClazzHistory extends ValueObject {
    private ClazzId clazzId;

    private Grade grade;

    private StudyYear studyYear;

    private String clazzName;

}