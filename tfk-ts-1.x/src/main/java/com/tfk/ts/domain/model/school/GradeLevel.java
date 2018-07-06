/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school;

/**
 * 年级序列,一到十二年级
 *
 * @author Liguiqing
 * @since V3.0
 */

public enum GradeLevel {
    One(1),Two(2), Three(3),
    Four(4),Five(5),Six(6),
    Seven(7),Eight(8),Nine(9),
    Ten(10),Eleven(11),Twelve(12);

    private int seq = 1;

    GradeLevel(int seq){
        this.seq = seq;
    }

    public int getSeq(){
        return this.seq;
    }

    public GradeLevel next(){
        int o = this.ordinal();
        for(GradeLevel level:GradeLevel.values()){
            if(level.ordinal() == o+1)
                return level;
        }
        return null;
    }

    public static GradeLevel fromLevel(int level){
        for(GradeLevel aLevel:GradeLevel.values()){
            if(aLevel.getSeq() == level)
                return aLevel;
        }

        throw new GradeNotFoundException(level + "");

    }

    public static GradeLevel fromName(String name){
        for(GradeLevel aLevel:GradeLevel.values()){
            if(aLevel.name().equals(name))
                return aLevel;
        }

        throw new GradeNotFoundException(name + "");

    }

}