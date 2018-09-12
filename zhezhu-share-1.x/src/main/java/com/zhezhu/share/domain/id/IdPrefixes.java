package com.zhezhu.share.domain.id;

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
    //微信用户唯一标识
    public static final String WeChatIdPrefix = "WCH";

    //微信用户关关注者唯一标识
    public static final String WeChatFollowerIdPrefix = "WCA";

    //微信用户关关注审核唯一标识
    public static final String FollowAuditIdPrefix = "FAu";

    //微信用户关关注申请唯一标识
    public static final String  FollowApplyIdPrefix = "FAP";

    //班级关注申请唯一标识
    public static final String ClazzFollowApplyIdPrefix = "CFA";

    //班级关注申请唯一标识
    public static final String ClazzFollowAuditIdPrefix = "CFu";

    //个人唯一标识
    public static final String PersonIdPrefix = "PER";

    //学校唯一标识
    public static final String SchoolIdPrefix = "SCH";

    //学科唯一标识
    public static final String SubjectIdPrefix = "SUB";

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

    //评价指标唯一标识
    public static final String IndexIdPrefix = "INX";

    //评价活动唯一标识
    public static final String AssessIdPrefix = "ASS";

    //评价活动分组唯一标识
    public static final String AssessTeamIdPrefix = "ATE";

    //评价主评者唯一标识
    public static final String AssessorIdPrefix = "ASR";

    //评价被评者唯一标识
    public static final String AssesseeIdPrefix = "ASE";

    //评价勋章唯一标识
    public static final String MedalIdPrefix = "MED";

    //授勋唯一标识
    public static final String AwardIdPrefix = "AWA";

    //授勋证物唯一标识
    public static final String EvidenceIdPrefix = "EVI";

    //勋章晋级唯一标识
    public static final String PromotionIdPrefix = "PRO";
}