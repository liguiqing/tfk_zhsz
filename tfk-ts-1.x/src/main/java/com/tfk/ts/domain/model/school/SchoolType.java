/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school;

/**
 * 学校类型：小学，初中，高中
 *
 * @author Liguiqing
 * @since V3.0
 */

public enum SchoolType {
    Primary{//小学
        public int getValue(){
            return 1;
        }

        public int gradeFrom(){
            return 1;
        }

        public int gradeTo(){
            return 6;
        }
    },
    Middle{//初中
        public int getValue(){
            return 2;
        }

        public int gradeFrom(){
            return 7;
        }

        public int gradeTo(){
            return 9;
        }
    },
    High{//高中
        public int getValue(){
            return 3;
        }

        public int gradeFrom(){
            return 10;
        }

        public int gradeTo(){
            return 12;
        }
    },
    PrimaryToMiddlel{
        public int getValue(){
            return 4;
        }

        public int gradeFrom(){
            return 1;
        }

        public int gradeTo(){
            return 9;
        }
    },
    MiddleToHigh{
        public int getValue(){
            return 5;
        }

        public int gradeFrom(){
            return 9;
        }

        public int gradeTo(){
            return 12;
        }
    },
    All{
        public int getValue(){
            return 9;
        }

    },
    Unkow{
        public int getValue(){
            return 0;
        }
    };

    public int getValue(){
        return -1;
    }

    public int gradeFrom(){
        return 1;
    }

    public int gradeTo(){
        return 12;
    }

    public boolean isIn(int gradeSeq){
        return this.gradeFrom() <= gradeSeq && gradeSeq <= this.gradeTo();
    }

}