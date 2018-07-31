package com.tfk.share.domain.id;

/**
 *
 * ID前缀
 *
 * 前缀规则：由大写字母组成
 *
 * 1、由一个单词构成的类，取前三个字母，若有冲突，则依次再取，直到不重复；
 * 2、由两个单词构成的类，取首单词两个字母，再取次单词首字母，若有冲突，则取次单词二字母，依此后推，直到不重复
 * 3、由三个以上单词构成的类，取前三个单词首字母，若有冲突，则取三单词二字母，依此后推，直到不重复
 *
 * @author Liguiqing
 * @since V3.0
 */

public class IdPrefixes {
    //个人唯一标识
    public static final String PersonIdPrefix = "PER";

    //学校唯一标识
    public static final String SchoolIdPrefix = "SCH";

    //班级唯一标识
    public static final String ClazzIdPrefix = "CLA";

    //教师唯一标识
    public static final String TeacherIdPrefix = "TEA";

    //教师管理经历唯一标识
    public static final String ClazzManagementIdPrefix = "CMA";

    //教师教学经历唯一标识
    public static final String ClazzTeachingIdPrefix = "CTE";

    //学生唯一标识
    public static final String StudentIdPrefix = "STU";

    //学生学习经历唯一标识
    public static final String StudyIdPrefix = "STD";

    //学生受管经历唯一标识
    public static final String ManagedIdPrefix = "MAN";

    //家长唯一标识
    public static final String ParentIdPrefix = "PAR";

    //客户唯一标识
    public static final String TenantIdPrefix = "TEN";
}