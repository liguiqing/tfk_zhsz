package com.tfk.commons.lang.reflect;

import com.google.common.base.MoreObjects;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class FieldMapper {
    private String nameOfClass;

    private String nameOfDb;

    public FieldMapper(String nameOfClass, String nameOfDb) {
        this.nameOfClass = nameOfClass;
        this.nameOfDb = nameOfDb;
    }

    public String nameOfClass() {
        return nameOfClass;
    }

    public String nameOfDb() {
        return nameOfDb;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("nameOfClass", nameOfClass)
                .add("nameOfDb", nameOfDb)
                .toString();
    }
}