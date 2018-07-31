package com.tfk.sm.application.school;

import com.google.common.base.MoreObjects;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class NewSchoolCommand {

    private String name;//学校名称

    private String alias; //学校简称

    private int scop;

    public NewSchoolCommand() {

    }

    public NewSchoolCommand(String name, String alias, int scop) {

        this.name = name;
        this.alias = alias;
        this.scop = scop;
    }

    public String getName() {
        return name;
    }

    public void name(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void alias(String alias) {
        this.alias = alias;
    }

    public int getScop() {
        return scop;
    }

    public void scop(int scop) {
        this.scop = scop;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("alias", alias)
                .add("scop", scop)
                .toString();
    }
}