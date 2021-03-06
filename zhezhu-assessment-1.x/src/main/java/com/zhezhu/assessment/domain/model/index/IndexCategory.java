package com.zhezhu.assessment.domain.model.index;

/**
 * @author Liguiqing
 * @since V3.0
 */

public enum IndexCategory {
    Morals("德",1),Intelligence("智",2),Sports("体",3),Esthetics("美",4),Labours("劳",5);

    private String name;

    private int type;

    private IndexCategory(String name,int type){
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString(){
        return this.name;
    }
}