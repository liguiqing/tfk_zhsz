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
        ('as-03-001','勋章已被使用',3,'zh_cn',"AS");

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
  `schoolId` varchar(36) NOT NULL COMMENT '学校唯一标识',
  `clazzName` varchar(16)  COMMENT '班级名称',
  `gradeName` varchar(16)  COMMENT '年级名称',
  `gradeLevel` TINYINT(2)  COMMENT '年级序列级别,1-12',
  `yearStarts` SMALLINT(4)  COMMENT '学年开始年度',
  `yearEnds` SMALLINT(4)  COMMENT '学年结束年度',
  `removed` TINYINT(1) DEFAULT 0 COMMENT '删除标记',
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
  `birthday` TIMESTAMP  COMMENT '生日',
  `joinDate` TIMESTAMP  COMMENT '入职日期',
  `offDate` TIMESTAMP  COMMENT '离职日期',
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
  `dateStarts` TIMESTAMP  COMMENT '开始日期',
  `dateEnds` TIMESTAMP  COMMENT '结束日期',
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
  `dateStarts` TIMESTAMP  COMMENT '开始日期',
  `dateEnds` TIMESTAMP  COMMENT '结束日期',
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
  `birthday` TIMESTAMP  COMMENT '生日',
  `joinDate` TIMESTAMP  COMMENT '入校日期',
  `offDate` TIMESTAMP  COMMENT '离校日期',
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
  `removed` TINYINT(1) DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `x_sm_student_contact_personId` (`personId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='学生联系信息表';

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
  `dateStarts` TIMESTAMP  COMMENT '开始日期',
  `dateEnds` TIMESTAMP  COMMENT '结束日期',
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
  `dateStarts` TIMESTAMP  COMMENT '开始日期',
  `dateEnds` TIMESTAMP  COMMENT '结束日期',
  PRIMARY KEY (`id`),
  KEY `x_sm_student_managed_personId` (`studentId`),
  KEY `x_sm_student_managed_clazzId` (`clazzId`),
  KEY `x_sm_student_managed_schoolId` (`schoolId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='学生受管班级信息表';