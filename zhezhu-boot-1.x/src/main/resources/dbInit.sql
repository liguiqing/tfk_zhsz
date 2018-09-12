DROP TABLE IF EXISTS `cm_message`;
CREATE TABLE `cm_message` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `code` varchar(16)  COMMENT '消息代码,编码格式:##-##-### 应用系统编码-模块编码-消息编码',
  `msg` varchar(256)  COMMENT '消息内容',
  `level` TINYINT(1) DEFAULT 1 COMMENT '消息级别:1-debug;2-info;3-error',
  `local` varchar(8) DEFAULT 'zh_cn' COMMENT '本地化标识',
  `app` varchar(16)  COMMENT '消息归属应用系统',
  PRIMARY KEY (`id`),
  INDEX `x_cm_message_level` (`level`),
  INDEX `x_cm_message_local` (`local`),
  INDEX `x_cm_message_app` (`app`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='系统输出的提示信息';
INSERT INTO `cm_message` (`code`,`msg`,`level`,`local`,`app`)
VALUES  ('00-00-000','系统未定义消息',1,'zh_cn',NULL),
        ('cm-01-001','联系方法格式错误',3,'zh_cn',"CM"),
        ('sm-02-001','学校查无此班级',3,'zh_cn',"SM"),
        ('sm-03-001','学校查无此老师',3,'zh_cn',"SM"),
        ('sm-04-001','学校查无此学生',3,'zh_cn',"SM"),
        ('as-01-001','指标得分超过允许范围',3,'zh_cn',"AS"),
        ('as-03-001','勋章已被使用',3,'zh_cn',"AS"),
        ('as-03-002','上级勋章级别不能低于本级',3,'zh_cn',"AS"),
        ('as-03-003','勋章级别不能小1',3,'zh_cn',"AS"),
        ('as-03-004','无效的评价角色',3,'zh_cn',"AS"),
        ('ac-01-000','微信账号没有关联',3,'zh_cn',"AC"),
        ('ac-01-001','班级关注申请已经完成审核,不能取消',3,'zh_cn',"AC"),
        ('ac-01-002','班级关注申请已经完成审核,不能再审核',3,'zh_cn',"AC"),
        ('ac-01-003','班级关注申请审核不能空',3,'zh_cn',"AC"),
        ('ac-01-004','关注审核取消失败',3,'zh_cn',"AC"),
        ('ac-01-005','无效的关注申请',3,'zh_cn',"AC"),
        ('ac-01-006','关注申请已经完成审核',3,'zh_cn',"AC"),
        ('ac-01-007','无效的微信用户类型',3,'zh_cn',"AC"),
        ('ac-01-008','相同类型的微信用户不能复制数据',3,'zh_cn',"AC");

DROP TABLE IF EXISTS `cm_course`;
CREATE TABLE `cm_course` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `subjectId` varchar(36) NOT NULL COMMENT '消息代码,编码格式:##-##-### 应用系统编码-模块编码-消息编码',
  `fullName` varchar(64)  COMMENT '学科全名称',
  `alias` VARCHAR(16)  COMMENT '学科简名称',
  `gbCode` VARCHAR(16) COMMENT '国标代码',
  `studyStarts` TINYINT(2)  COMMENT '学科开始学习的年级,1-12',
  `studyEnds` TINYINT(2)  COMMENT '学科完成学习的年级,1-12',
  PRIMARY KEY (`id`),
  INDEX `x_cm_course_subjectId` (`subjectId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='学科信息表';
INSERT INTO `cm_course` (`subjectId`,`fullName`,`alias`,`gbCode`,`studyStarts`,`studyEnds`)
VALUES  ('SUBe563abfe42c545dbb3d600f6d512c707','语文','语文',NULL,1,12), ('SUB30c275bab0d0468f86dc13e68ddb032d','数学','数学',NULL,1,12),
        ('SUBdd1954be392743938eefac1b5ed2e04d','英语','英语',NULL,1,12), ('SUB82cc43e0688441959a91c57d1ecdab10','物理','物理',NULL,8,12),
        ('SUBb0e1a124fd6a4df8b6b48e3302ecbbe1','化学','化学',NULL,9,12), ('SUBaa7e27e57bb9416f96ed0e1b5ef14b1d','政治','政治',NULL,7,12),
        ('SUB0254a9ad779d4dc4a2f87ae123527c4a','历史','历史',NULL,7,12), ('SUB4b680df0137b4564bc438fcb647373f4','地理','地理',NULL,7,12),
        ('SUB818056b50c8446b2827d9ba76c164db2','生物','生物',NULL,7,12), ('SUBf2d8fe15a40b4f72bdaa21492a8ffb77','信息技术','信技',NULL,10,12),
        ('SUBc1d12ca4cf9b4672b58c0cb4ce5e1629','备用课程1','备用1',NULL,1,12), ('SUB4bfab137366a42938b36670d62fa330a','备用课程2','备用2',NULL,1,12),
        ('SUB44f727fa633b4681b958ea48b1a0c908','备用课程3','备用3',NULL,1,12), ('SUB1276abca27174ab9aae78eb2b34f7768','备用课程4','备用4',NULL,1,12),
        ('SUBcb619a1578464e5f93459c52ccbc338e','备用课程5','备用5',NULL,1,12), ('SUB431acfd2196549ada3792823e0c0ad25','备用课程6','备用6',NULL,1,12),
        ('SUB7919e3fb543e4566bedb801187362c59','备用课程7','备用7',NULL,1,12), ('SUBac4aed22199f410abb4d6bfe83888bc2','备用课程8','备用8',NULL,1,12),
        ('SUBd1803b6f25a64d4d953e716b56bc5d9b','备用课程9','备用9',NULL,1,12), ('SUBb363304861234307968306cf11e84f29','备用课程10','备用10',NULL,1,12);

DROP TABLE IF EXISTS `sm_school`;
CREATE TABLE `sm_school` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `schoolId` varchar(36) NOT NULL COMMENT '学校唯一标识',
  `tenantId` varchar(36) NOT NULL COMMENT '学校所属租户唯一标识',
  `name` varchar(32)  COMMENT '学校名称',
  `alias` varchar(32)  COMMENT '学校简称',
  `scope` varchar(16) NOT NULL COMMENT '学校类型:Primary-小学;Middle-初中;High-高中;PrimaryToMiddlel-小学到初中;MiddleToHigh-初中到高中；All-小学到高中',
  `removed` TINYINT(1) DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `x_sm_school_schoolId` (`schoolId`),
  KEY `x_sm_school_tenantId` (`tenantId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='学校信息表';

DROP TABLE IF EXISTS `sm_clazz`;
CREATE TABLE `sm_clazz` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `clazzId` varchar(36) NOT NULL COMMENT '班级唯一标识',
  `schoolId` varchar(36) NOT NULL COMMENT '学校唯一标识',
  `openedTime` TIMESTAMP  COMMENT '开班时间',
  `closedTime` TIMESTAMP  COMMENT '结束时间',
  `clazzType` varchar(16) NOT NULL COMMENT '班级类型:Supervise-行政政;Learning-教学班;United-不分班级',
  `removed` TINYINT(1) DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `x_sm_clazz_schoolId` (`schoolId`),
  KEY `x_sm_clazz_clazzId` (`clazzId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='班级信息表';

DROP TABLE IF EXISTS `sm_clazz_history`;
CREATE TABLE `sm_clazz_history` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `clazzId` varchar(36) NOT NULL COMMENT '班级唯一标识',
  `clazzName` varchar(16)  COMMENT '班级名称',
  `gradeName` varchar(16)  COMMENT '年级名称',
  `gradeLevel` TINYINT(2)  COMMENT '年级序列级别,1-12',
  `yearStarts` SMALLINT(4)  COMMENT '学年开始年度',
  `yearEnds` SMALLINT(4)  COMMENT '学年结束年度',
  PRIMARY KEY (`id`),
  KEY `x_sm_clazz_history_clazzId` (`clazzId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='班级历程信息表';

DROP TABLE IF EXISTS `sm_teacher`;
CREATE TABLE `sm_teacher` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `teacherId` varchar(36) NOT NULL COMMENT '学校老师唯一标识',
  `personId` varchar(36) NOT NULL COMMENT '人员唯一标识',
  `schoolId` varchar(36) NOT NULL COMMENT '学校唯一标识',
  `name` varchar(36)  COMMENT '老师姓名',
  `gender` varchar(8)  COMMENT '性别',
  `birthday` DATE  COMMENT '生日',
  `joinDate` DATE  COMMENT '入职日期',
  `offDate` DATE  COMMENT '离职日期',
  `removed` TINYINT(1) DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `x_sm_teacher_teacherId` (`teacherId`),
  KEY `x_sm_teacher_personId` (`personId`),
  KEY `x_sm_teacher_schoolId` (`schoolId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='老师信息表';

DROP TABLE IF EXISTS `sm_teacher_contact`;
CREATE TABLE `sm_teacher_contact` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `personId` varchar(36) NOT NULL COMMENT '老师唯一标识',
  `category` varchar(8)  COMMENT '联系方式类型:Phone-家庭电话;Mobile-移动电话;Email-电子信箱;QQ-qq号码;Weixin-微信',
  `name` varchar(16)  COMMENT '联系方式名称',
  `info` varchar(64)  COMMENT '联系方式',
  PRIMARY KEY (`id`),
  KEY `x_sm_teacher_contact_personId` (`personId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='老师联系信息表';

DROP TABLE IF EXISTS `sm_teacher_course`;
CREATE TABLE `sm_teacher_course` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `teacherId` varchar(36) NOT NULL COMMENT '老师唯一标识',
  `subjectId` varchar(36)  COMMENT '课程学科唯一标识',
  `gradeName` varchar(8)  COMMENT '课程年级名称',
  `gradeLevel` TINYINT(2)  COMMENT '课程年级序列级别,1-12',
  `courseName` varchar(16)  COMMENT '课程名称',
  `courseAlias` varchar(8)  COMMENT '课程简称',
  PRIMARY KEY (`id`),
  KEY `x_sm_teacher_contact_teacherId` (`teacherId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='老师可授课信息表';

DROP TABLE IF EXISTS `sm_teacher_teaching`;
CREATE TABLE `sm_teacher_teaching` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `teacherId` varchar(36) NOT NULL COMMENT '老师唯一标识',
  `schoolId` varchar(36) NOT NULL COMMENT '学校唯一标识',
  `clazzId` varchar(36) NOT NULL COMMENT '班级唯一标识',
  `subjectId` varchar(36)  COMMENT '课程学科唯一标识',
  `gradeName` varchar(8)  COMMENT '课程年级名称',
  `gradeLevel` TINYINT(2)  COMMENT '课程年级序列级别,1-12',
  `courseName` varchar(16)  COMMENT '课程名称',
  `courseAlias` varchar(8)  COMMENT '课程简称',
  `yearStarts` SMALLINT(4)  COMMENT '学年开始年度',
  `yearEnds` SMALLINT(4)  COMMENT '学年结束年度',
  `dateStarts` DATE  COMMENT '开始日期',
  `dateEnds` DATE  COMMENT '结束日期',
  `job` varchar(16)  COMMENT '职务,如语文老师',
  PRIMARY KEY (`id`),
  KEY `x_sm_teacher_teaching_teacherId` (`teacherId`),
  KEY `x_sm_teacher_teaching_clazzId` (`clazzId`),
  KEY `x_sm_teacher_teaching_schoolId` (`schoolId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='老师班级教学信息表';

DROP TABLE IF EXISTS `sm_teacher_management`;
CREATE TABLE `sm_teacher_management` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `teacherId` varchar(36) NOT NULL COMMENT '老师唯一标识',
  `schoolId` varchar(36) NOT NULL COMMENT '学校唯一标识',
  `clazzId` varchar(36) NOT NULL COMMENT '班级唯一标识',
  `gradeName` varchar(8)  COMMENT '课程年级名称',
  `gradeLevel` TINYINT(2)  COMMENT '课程年级序列级别,1-12',
  `job` varchar(16)  COMMENT '职务,如班主任',
  `yearStarts` SMALLINT(4)  COMMENT '学年开始年度',
  `yearEnds` SMALLINT(4)  COMMENT '学年结束年度',
  `dateStarts` DATE  COMMENT '开始日期',
  `dateEnds` DATE  COMMENT '结束日期',
  PRIMARY KEY (`id`),
  KEY `x_sm_teacher_management_teacherId` (`teacherId`),
  KEY `x_sm_teacher_management_clazzId` (`clazzId`),
  KEY `x_sm_teacher_management_schoolId` (`schoolId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='老师班级教学信息表';

DROP TABLE IF EXISTS `sm_student`;
CREATE TABLE `sm_student` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `studentId` varchar(36) NOT NULL COMMENT '学生唯一标识',
  `schoolId` varchar(36) NOT NULL COMMENT '学校唯一标识',
  `personId` varchar(36) NOT NULL COMMENT '人员唯一标识',
  `name` varchar(36) NOT NULL COMMENT '学生姓名',
  `gender` varchar(8)  COMMENT '性别',
  `birthday` DATE  COMMENT '生日',
  `joinDate` DATE  COMMENT '入校日期',
  `offDate` DATE  COMMENT '离校日期',
  `removed` TINYINT(1) DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `x_sm_student_studentId` (`studentId`),
  KEY `x_sm_student_schoolId` (`schoolId`),
  INDEX `x_sm_student_personId` (`personId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='学校学生信息表';

DROP TABLE IF EXISTS `sm_student_contact`;
CREATE TABLE `sm_student_contact` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `personId` varchar(36) NOT NULL COMMENT '人员唯一标识',
  `category` varchar(8) NOT NULL COMMENT '联系方式类型:Phone-家庭电话;Mobile-移动电话;Email-电子信箱;QQ-qq号码;Weixin-微信',
  `name` varchar(16) NOT NULL COMMENT '联系方式名称',
  `info` varchar(64)  COMMENT '联系方式',
  PRIMARY KEY (`id`),
  KEY `x_sm_student_contact_personId` (`personId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='学生联系信息表';

DROP TABLE IF EXISTS `sm_student_credentials`;
CREATE TABLE `sm_student_credentials` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `personId` varchar(36) NOT NULL COMMENT '人员唯一标识',
  `names` varchar(16) NOT NULL COMMENT '证件名称',
  `info` varchar(64)  COMMENT '联系方式',
  `releaseDate` DATE  COMMENT '发放日期',
  `expireDate` DATE  COMMENT '有效日期',
  PRIMARY KEY (`id`),
  KEY `x_sm_student_credentials_personId` (`personId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='学生证件表';

DROP TABLE IF EXISTS `sm_student_study`;
CREATE TABLE `sm_student_study` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `studentId` varchar(36) NOT NULL COMMENT '学生唯一标识',
  `schoolId` varchar(36) NOT NULL COMMENT '学校唯一标识',
  `clazzId` varchar(36) NOT NULL COMMENT '班级唯一标识',
  `subjectId` varchar(36)  COMMENT '课程学科唯一标识',
  `gradeName` varchar(8)  COMMENT '课程年级名称',
  `gradeLevel` TINYINT(2)  COMMENT '课程年级序列级别,1-12',
  `courseName` varchar(16)  COMMENT '课程名称',
  `courseAlias` varchar(8)  COMMENT '课程简称',
  `yearStarts` SMALLINT(4)  COMMENT '学年开始年度',
  `yearEnds` SMALLINT(4)  COMMENT '学年结束年度',
  `dateStarts` DATE  COMMENT '开始日期',
  `dateEnds` DATE  COMMENT '结束日期',
  PRIMARY KEY (`id`),
  KEY `x_sm_student_study_studentId` (`studentId`),
  KEY `x_sm_student_study_clazzId` (`clazzId`),
  KEY `x_sm_student_study_schoolId` (`schoolId`),
  INDEX `x_sm_student_study_subjectId` (`subjectId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='学生课程学习信息表';

DROP TABLE IF EXISTS `sm_student_managed`;
CREATE TABLE `sm_student_managed` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `studentId` varchar(36) NOT NULL COMMENT '学生唯一标识',
  `schoolId` varchar(36) NOT NULL COMMENT '学校唯一标识',
  `clazzId` varchar(36) NOT NULL COMMENT '班级唯一标识',
  `job` varchar(16)  COMMENT '职务,如班长,学习委员',
  `gradeName` varchar(8)  COMMENT '年级名称',
  `gradeLevel` TINYINT(2)  COMMENT '年级序列级别,1-12',
  `yearStarts` SMALLINT(4)  COMMENT '学年开始年度',
  `yearEnds` SMALLINT(4)  COMMENT '学年结束年度',
  `dateStarts` DATE  COMMENT '开始日期',
  `dateEnds` DATE  COMMENT '结束日期',
  PRIMARY KEY (`id`),
  KEY `x_sm_student_managed_personId` (`studentId`),
  KEY `x_sm_student_managed_clazzId` (`clazzId`),
  KEY `x_sm_student_managed_schoolId` (`schoolId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='学生受管班级信息表';

DROP TABLE IF EXISTS `ac_WeChat`;
CREATE TABLE `ac_WeChat` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `weChatId` varchar(36) NOT NULL COMMENT '绑定唯一标识',
  `personId` varchar(36) NOT NULL COMMENT '绑定者人员唯一标识',
  `wechatOpenId` varchar(36) NOT NULL  COMMENT '微信唯一标识',
  `category` varchar(16)  COMMENT '绑定者类型:Teacher;Student;Parent',
  `name` varchar(16) NOT NULL COMMENT '绑定者姓名',
  `phone` varchar(16)  COMMENT '绑定者联系电话',
  `bindDate` TIMESTAMP DEFAULT now() COMMENT '绑定时间',
  `removed` TINYINT(1) DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `x_ac_WeChat_weChatId` (`weChatId`),
  INDEX `x_ac_WeChat_personId` (`personId`),
  INDEX `x_ac_WeChat_wechatOpenId` (`wechatOpenId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='微信绑定信息';

DROP TABLE IF EXISTS `ac_WeChatFollower`;
CREATE TABLE `ac_WeChatFollower` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `followerId` varchar(36) NOT NULL COMMENT '被关注者唯一标识',
  `weChatId` varchar(36) NOT NULL COMMENT '绑定唯一标识',
  `personId` varchar(36) NOT NULL COMMENT '绑定者人员唯一标识',
  `followDate` TIMESTAMP DEFAULT now() COMMENT '关注时间',
  `auditorId` varchar(36)  COMMENT '审核人员唯一标识,PersonId',
  `auditorName` varchar(16)  COMMENT '审核人员姓名',
  `auditDate` TIMESTAMP DEFAULT now() COMMENT '审核时间',
  `auditResult` varchar(16) DEFAULT 'Undo' COMMENT '审核结果:Undo;Yes,No',
  PRIMARY KEY (`id`),
  KEY `x_ac_WeChatFollower_weChatId` (`weChatId`),
  INDEX `x_ac_WeChatFollower_followerId` (`followerId`),
  INDEX `x_ac_WeChatFollower_personId` (`personId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='被关注者信息';

DROP TABLE IF EXISTS `ac_FollowApply`;
CREATE TABLE `ac_FollowApply` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `applyId` varchar(36) NOT NULL COMMENT '关注申请唯一标识',
  `auditId` varchar(36)  COMMENT '审核唯一标识',
  `applierWeChatId` varchar(36) NOT NULL COMMENT '审核申请人员唯一标识,WeChatId',
  `applierWeChatOpenId` varchar(36) NOT NULL COMMENT '审核申请人员微信号',
  `applierName` varchar(16)  COMMENT '审核申请人员姓名',
  `followerId` varchar(36) NOT NULL COMMENT '关注者唯一标识,PersonId',
  `followerSchoolId` varchar(36) NOT NULL COMMENT '关注者所在学校唯一标识',
  `followerClazzId` varchar(36) NOT NULL COMMENT '关注者所在班级唯一标识',
  `applyDate` TIMESTAMP DEFAULT now() COMMENT '申请时间',
  `cause` varchar(128)  COMMENT '申请原因',
  PRIMARY KEY (`id`),
  KEY `x_ac_FollowApply_applyId` (`applyId`),
  KEY `x_ac_FollowApply_auditId` (`auditId`),
  INDEX `x_ac_FollowApply_followerSchoolId` (`followerSchoolId`),
  INDEX `x_ac_FollowApply_followerClazzId` (`followerClazzId`),
  INDEX `x_ac_FollowApply_followerId` (`followerId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='关注申请';

DROP TABLE IF EXISTS `ac_FollowAudit`;
CREATE TABLE `ac_FollowAudit` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `auditId` varchar(36) NOT NULL COMMENT '审核唯一标识',
  `applyId` varchar(36)  COMMENT '关注申请唯一标识',
  `auditorId` varchar(36) NOT NULL COMMENT '审核人员唯一标识,PersonId',
  `auditorSchoolId` varchar(36) NOT NULL COMMENT '审核人员所在学校唯一标识',
  `auditorClazzId` varchar(36) NOT NULL COMMENT '审核人员所在班级唯一标识',
  `auditorRole` varchar(36) NOT NULL COMMENT '审核人员角色:Teacher;Student;Parent',
  `auditorName` varchar(16)  COMMENT '审核人员姓名',
  `applierWeChatId` varchar(36) NOT NULL COMMENT '审核申请人员唯一标识,WeChatId',
  `applierWeChatOpenId` varchar(36) NOT NULL COMMENT '审核申请人员微信号',
  `applierName` varchar(16)  COMMENT '审核申请人员姓名',
  `defendantId` varchar(36) NOT NULL COMMENT '被核人员唯一标识,PersonId',
  `defendantSchoolId` varchar(36) NOT NULL COMMENT '被核人员所在学校唯一标识',
  `defendantClazzId` varchar(36) NOT NULL COMMENT '被核人员所在班级唯一标识',
  `defendantRole` varchar(16) NOT NULL COMMENT '被核人员角色:Teacher;Student;Parent',
  `defendantName` varchar(16)  COMMENT '被核人员唯一标识',
  `auditDate` TIMESTAMP DEFAULT now() COMMENT '审核时间',
  `ok`TINYINT(1)  COMMENT '审核结果',
  `description` varchar(128)  COMMENT '审核说明',
  PRIMARY KEY (`id`),
  KEY `x_ac_FollowAudit_auditId` (`auditId`),
  INDEX `x_ac_FollowAudit_auditorSchoolId` (`auditorSchoolId`),
  INDEX `x_ac_FollowAudit_auditorClazzId` (`auditorClazzId`),
  INDEX `x_ac_FollowAudit_applyId` (`applyId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='关注申请审核';

DROP TABLE IF EXISTS `ac_ClazzFollowApply`;
CREATE TABLE `ac_ClazzFollowApply` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `applyId` varchar(36) NOT NULL COMMENT '关注申请唯一标识',
  `auditId` varchar(36)  COMMENT '审核唯一标识',
  `applyingSchoolId` varchar(36) NOT NULL COMMENT '关注者所在学校唯一标识',
  `applyingClazzId` varchar(36) NOT NULL COMMENT '关注者所在班级唯一标识',
  `applierId` varchar(36)  COMMENT '关注者唯一标识,PersonId',
  `applierName` varchar(16)  COMMENT '申请人员姓名',
  `applierPhone` varchar(16)  COMMENT '申请人员电话',
  `applyDate` TIMESTAMP DEFAULT now() COMMENT '申请时间',
  `cause` varchar(128)  COMMENT '申请原因',
  `passed`TINYINT(1)  COMMENT '审核结果',
  PRIMARY KEY (`id`),
  KEY `x_ac_ClazzFollowApply_applyId` (`applyId`),
  KEY `x_ac_ClazzFollowApply_auditId` (`auditId`),
  INDEX `x_ac_ClazzFollowApply_applyingSchoolId` (`applyingSchoolId`),
  INDEX `x_ac_ClazzFollowApply_applyingClazzId` (`applyingClazzId`),
  INDEX `x_ac_ClazzFollowApply_applierId` (`applierId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='班级关注申请';

DROP TABLE IF EXISTS `ac_ClazzFollowAudit`;
CREATE TABLE `ac_ClazzFollowAudit` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `auditId` varchar(36)  COMMENT '审核唯一标识',
  `applyId` varchar(36) NOT NULL COMMENT '关注申请唯一标识',
  `auditorId` varchar(36) NOT NULL COMMENT '审核者唯一标识,PersonId',
  `auditDate` TIMESTAMP DEFAULT now() COMMENT '审核时间',
  `ok`TINYINT(1)  COMMENT '审核结果',
  `description` varchar(128)  COMMENT '审核说明',
  PRIMARY KEY (`id`),
  KEY `x_ac_ClazzFollowAudit_auditId` (`auditId`),
  KEY `x_ac_ClazzFollowAudit_applyId` (`applyId`),
  INDEX `x_ac_ClazzFollowAudit_auditorId` (`auditorId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='班级关注审核';

DROP TABLE IF EXISTS `as_Index`;
CREATE TABLE `as_Index` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `indexId` VARCHAR(36) NOT NULL  COMMENT '评价指标唯一标识',
  `parentId` VARCHAR(36)  COMMENT '评价指标上级唯一标识',
  `ownerId` VARCHAR(36)  COMMENT '所属者,学校级客户就是schoolId,为空时表示通用指标',
  `category` VARCHAR(16) NOT NULL  COMMENT '评价指标类别',
  `name` VARCHAR(16) NOT NULL  COMMENT '评价指标名称',
  `alias` VARCHAR(8)  COMMENT '评价指标简称',
  `plus` TINYINT(1) DEFAULT 1 COMMENT '大于0正面指标,计算时分值相加;等于0负责指标,计算时分值相减',
  `score` DOUBLE(5,2)  COMMENT '评价指标分值',
  `weight` DOUBLE(3,2) COMMENT '评价指标得分计算权重,取值范围0-1',
  `description` VARCHAR(128)   COMMENT '评价指标说明',
  `groups` VARCHAR(16)   COMMENT '指标组',
  `removed` TINYINT(1) DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `x_as_Index_indexId` (`indexId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='评价指标';

DROP TABLE IF EXISTS `as_Index_Mapping`;
CREATE TABLE `as_Index_Mapping` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `indexId` VARCHAR(36) NOT NULL  COMMENT '评价指标唯一标识',
  `mappedId` VARCHAR(36)  COMMENT '评价指标上级唯一标识',
  `score` DOUBLE(5,2)  COMMENT '评价指标分值',
  `weight` DOUBLE(3,2) COMMENT '评价指标得分计算权重,取值范围0-1',
  `removed` TINYINT(1) DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `x_as_Index_Mapping_indexId` (`indexId`),
  KEY `x_as_Index_Mapping_mappedIndexId` (`mappedId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='客户评价指标与标准指标映射';

DROP TABLE IF EXISTS `as_Assessee`;
CREATE TABLE `as_Assessee` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `assesseeId` varchar(36) NOT NULL COMMENT '被评者唯一标识',
  `schoolId` varchar(36) NOT NULL COMMENT '学校唯一标识',
  `personId` varchar(36) NOT NULL COMMENT '人员唯一标识',
  `role` varchar(16)  COMMENT '人员角色:老师(Teacher);学生(Student);家长(Parents)',
  PRIMARY KEY (`id`),
  INDEX `x_as_Assessee_assesseeId` (`assesseeId`),
  INDEX `x_as_Assessee_schoolId` (`schoolId`),
  INDEX `x_as_Assessee_personId` (`personId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='被评者';

DROP TABLE IF EXISTS `as_Assessor`;
CREATE TABLE `as_Assessor` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `assessorId` varchar(36) NOT NULL COMMENT '主评者唯一标识',
  `schoolId` varchar(36) NOT NULL COMMENT '学校唯一标识',
  `personId` varchar(36) NOT NULL COMMENT '人员唯一标识',
  `role` varchar(16)  COMMENT '人员角色:老师(Teacher);学生(Student);家长(Parents)',
  PRIMARY KEY (`id`),
  INDEX `x_as_Assessor_assessorId` (`assessorId`),
  INDEX `x_as_Assessor_schoolId` (`schoolId`),
  INDEX `x_as_Assessor_personId` (`personId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='主评者';

DROP TABLE IF EXISTS `as_Assess`;
CREATE TABLE `as_Assess` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `assessId` varchar(36) NOT NULL COMMENT '评价唯一标识',
  `assessorId` varchar(36) NOT NULL COMMENT '主评唯一标识',
  `assesseeId` varchar(36) NOT NULL COMMENT '被评唯一标识',
  `assessTeamId` varchar(36) COMMENT '评价所属组唯一标识',
  `indexId` varchar(36)  COMMENT '评价指标唯一标识',
  `doneDate` TIMESTAMP DEFAULT NOW() COMMENT '评价时间',
  `category` VARCHAR(16) COMMENT '评价指标类别',
  `score` DOUBLE(5,2)  COMMENT '评价得分',
  `word` VARCHAR(64)  COMMENT '评语',
  `removed` TINYINT(1) DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `x_as_Assess_assessId` (`assessId`),
  INDEX `x_as_Assess_assessorId` (`assessorId`),
  INDEX `x_as_Assess_assesseeId` (`assesseeId`),
  INDEX `x_as_Assess_assessTeamId` (`assessTeamId`),
  INDEX `x_as_Assess_indexId` (`indexId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='评价信息表';

DROP TABLE IF EXISTS `as_AssessRank`;
CREATE TABLE `as_AssessRank` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `schoolId` varchar(36) NOT NULL COMMENT '学校唯一标识,源于as_Assessee.schoolId',
  `personId` varchar(36) NOT NULL COMMENT '人员唯一标识,源于as_Assessee.personId',
  `yearStarts` SMALLINT(4)  COMMENT '学年开始年度',
  `yearEnds` SMALLINT(4)  COMMENT '学年结束年度',
  `rankScope` varchar(16)  COMMENT '排名范围:Clazz-班级;Grade-年级;School-学校',
  `rankCategory` varchar(16) NOT NULL COMMENT '排名类型:Year-学年;Term-学期;Month-月;Weekend-周',
  `rankDate` DATE COMMENT '排名日期',
  `rankNode` varchar(16)  COMMENT '排名节点:Year(yearStarts+yearEnds,如2018-2019);Term(1,2);Moth(1-12);Weekend(1-52);',
  `rank` SMALLINT(4)  COMMENT '排名',
  `promote` SMALLINT(4)  COMMENT '与前期排名进退步名次',
  PRIMARY KEY (`id`),
  INDEX `x_as_AssessRank_schoolId` (`schoolId`),
  INDEX `x_as_AssessRank_personId` (`personId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='评价排名';

DROP TABLE IF EXISTS `as_Medal`;
CREATE TABLE `as_Medal` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `medalId` varchar(36) NOT NULL COMMENT '勋章唯一标识',
  `highId` varchar(36)  COMMENT '上级勋章唯一标识',
  `schoolId` varchar(36) NOT NULL COMMENT '勋章所属学校唯一标识',
  `level` SMALLINT  COMMENT '级别',
  `category` varchar(16)  COMMENT '勋章类别',
  `name` varchar(16)  COMMENT '勋章名称',
  `upLeast` SMALLINT  COMMENT '晋级数量',
  `indexIds` varchar(1204)  COMMENT '勋章指标组成',
  PRIMARY KEY (`id`),
  KEY `x_as_Medal_medalId` (`medalId`),
  KEY `x_as_Medal_schoolId` (`schoolId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='评价勋章';

DROP TABLE IF EXISTS `as_Award`;
CREATE TABLE `as_Award` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `awardId` varchar(36) NOT NULL COMMENT '授勋唯一标识',
  `riseTo` varchar(36)  COMMENT '被用于高级授勋唯一标识',
  `medalId` varchar(36) NOT NULL COMMENT '勋章唯一标识',
  `possessorId` varchar(36) NOT NULL COMMENT '勋章获得者唯一标识,PersonId',
  `winDate` TIMESTAMP DEFAULT NOW() COMMENT '勋章类别',
  `medalName` varchar(16)  COMMENT '所得勋章名称',
  `removed` TINYINT(1) DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`),
  INDEX `x_as_Award_assessorId` (`awardId`),
  INDEX `x_as_Award_riseTo` (`riseTo`),
  INDEX `x_as_Award_medalId` (`medalId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='授勋';

DROP TABLE IF EXISTS `as_Evidence`;
CREATE TABLE `as_Evidence` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `evidenceId` varchar(36) NOT NULL COMMENT '授勋证物唯一标识',
  `awardId` varchar(36) NOT NULL COMMENT '授勋唯一标识',
  `evidenceType` varchar(16) NOT NULL COMMENT '证物类型',
  `uploadDate` DATE COMMENT '上传日期',
  `storage` varchar(512)  COMMENT '证物存储点',
  `removed` TINYINT(1) DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`),
  INDEX `x_as_Evidence_assessorId` (`awardId`),
  INDEX `x_as_Evidence_evidenceId` (`evidenceId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='授勋证物';