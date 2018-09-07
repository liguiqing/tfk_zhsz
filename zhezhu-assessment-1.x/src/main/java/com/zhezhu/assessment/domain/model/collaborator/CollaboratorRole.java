package com.zhezhu.assessment.domain.model.collaborator;

import com.zhezhu.commons.lang.LabelEnum;

/**
 * @author Liguiqing
 * @since V3.0
 */

public enum CollaboratorRole implements LabelEnum {
    Teacher("教师"),Student("学生"),Parent("家长");

    private String label;

    CollaboratorRole(String label) {
        this.label = label;
    }

    @Override
    public String toString(){
        return this.getLabel() + ":" + this.getValue();
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public String getValue() {
        return this.name();
    }
}