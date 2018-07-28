package com.tfk.share.domain.school;

import com.tfk.commons.domain.ValueObject;

/**
 * 课程
 *
 * @author Liguiqing
 * @since V3.0
 */

public class Course extends ValueObject {
    private String name;

    public String name() {
        return name;
    }
}