package com.tfk.share.domain.school;

/**
 * K12学校学段类型:小学,初中,高中等
 *
 * @author Liguiqing
 * @since V3.0
 */

public enum SchoolScope {
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
    PrimaryToMiddlel{//9年一贯制
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
    MiddleToHigh{//完中(初中到高中)
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
    All{ //完校
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

    public static SchoolScope get(int gradeSeq){
        switch (gradeSeq){
            case 1: return Primary;
            case 2: return Middle;
            case 3: return High;
            case 4: return PrimaryToMiddlel;
            case 5: return MiddleToHigh;
            case 9: return All;
            default: return Unkow;
        }
    }
}