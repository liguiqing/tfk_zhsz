DROP TABLE IF EXISTS `sm_school`;
CREATE TABLE `sm_school` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `schoolId` varchar(36) NOT NULL COMMENT '学校唯一标识',
  `tenantId` varchar(36) NOT NULL COMMENT '学校所属租户唯一标识',
  `name` varchar(32)  COMMENT '学校名称',
  `alias` varchar(32)  COMMENT '学校简称',
  `scope` varchar(16) NOT NULL COMMENT '学校类型:Primary-小学;Middle-初中;High-高中;PrimaryToMiddlel-小学到初中;MiddleToHigh-初中到高中；All-小学到高中',
  `removed` TINYINT(1) default 0 COMMENT '删除标记',
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
  `removed` TINYINT(1) default 0 COMMENT '删除标记',
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
  `gradeName` TIMESTAMP  COMMENT '年级名称',
  `gradeLevel` TINYINT(2)  COMMENT '年级序列级别,1-12',
  `yearStarts` SMALLINT(4)  COMMENT '学年开始年度',
  `yearEnds` SMALLINT(4)  COMMENT '学年结束年度',
  `removed` TINYINT(1) default 0 COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `x_sm_clazz_history_clazzId` (`clazzId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='班级历程信息表';

DROP TABLE IF EXISTS `sm_teacher`;
CREATE TABLE `sm_teacher` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `personId` varchar(36) NOT NULL COMMENT '人员唯一标识',
  `schoolId` varchar(36) NOT NULL COMMENT '学校唯一标识',
  `name` varchar(36)  COMMENT '老师姓名',
  `gender` varchar(2)  COMMENT '性别',
  `birthday` TIMESTAMP  COMMENT '生日',
  `joinDate` TIMESTAMP  COMMENT '入职日期',
  `offDate` TIMESTAMP  COMMENT '离职日期',
  `removed` TINYINT(1) default 0 COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `x_sm_teacher_personId` (`personId`),
  KEY `x_sm_teacher_schoolId` (`schoolId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='老师信息表';

DROP TABLE IF EXISTS `sm_teacher_contact`;
CREATE TABLE `sm_teacher_contact` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `personId` varchar(36) NOT NULL COMMENT '老师唯一标识',
  `category` varchar(8)  COMMENT '联系方式类型:Phone-家庭电话;Mobile-移动电话;Email-电子信箱;QQ-qq号码;Weixin-微信',
  `name` varchar(16)  COMMENT '联系方式名称',
  `info` varchar(2)  COMMENT '联系方式',
  PRIMARY KEY (`id`),
  KEY `x_sm_teacher_contact_personId` (`personId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='老师联系信息表';

DROP TABLE IF EXISTS `sm_teacher_course`;
CREATE TABLE `sm_teacher_course` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `personId` varchar(36) NOT NULL COMMENT '老师唯一标识',
  `subjectId` varchar(36)  COMMENT '课程学科唯一标识',
  `gradeName` varchar(8)  COMMENT '课程年级名称',
  `gradeLevel` TINYINT(2)  COMMENT '课程年级序列级别,1-12',
  `courseName` varchar(16)  COMMENT '课程名称',
  `courseAlias` varchar(8)  COMMENT '课程简称',
  PRIMARY KEY (`id`),
  KEY `x_sm_teacher_contact_personId` (`personId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='老师可授课信息表';

DROP TABLE IF EXISTS `sm_teacher_school`;
CREATE TABLE `sm_teacher_school` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `personId` varchar(36) NOT NULL COMMENT '老师唯一标识',
  `schoolId` varchar(36) NOT NULL COMMENT '学校唯一标识',
  `joinDate` TIMESTAMP  COMMENT '入职日期',
  `offDate` TIMESTAMP  COMMENT '离职日期',
  `removed` TINYINT(1) default 0 COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `x_sm_teacher_school_personId` (`personId`),
  KEY `x_sm_teacher_school_schoolId` (`schoolId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='老师入校信息表';

DROP TABLE IF EXISTS `sm_teacher_teaching`;
CREATE TABLE `sm_teacher_teaching` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `personId` varchar(36) NOT NULL COMMENT '老师唯一标识',
  `schoolId` varchar(36) NOT NULL COMMENT '学校唯一标识',
  `clazzId` varchar(36) NOT NULL COMMENT '班级唯一标识',
  `subjectId` varchar(36)  COMMENT '课程学科唯一标识',
  `gradeName` varchar(8)  COMMENT '课程年级名称',
  `gradeLevel` TINYINT(2)  COMMENT '课程年级序列级别,1-12',
  `courseName` varchar(16)  COMMENT '课程名称',
  `courseAlias` varchar(8)  COMMENT '课程简称',
  `dateStarts` TIMESTAMP  COMMENT '开始日期',
  `dateEnds` TIMESTAMP  COMMENT '结束日期',
  `job` varchar(16)  COMMENT '职务,如语文老师',
  PRIMARY KEY (`id`),
  KEY `x_sm_teacher_teaching_personId` (`personId`),
  KEY `x_sm_teacher_teaching_clazzId` (`clazzId`),
  KEY `x_sm_teacher_teaching_schoolId` (`schoolId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='老师班级教学信息表';

DROP TABLE IF EXISTS `sm_teacher_management`;
CREATE TABLE `sm_teacher_management` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `personId` varchar(36) NOT NULL COMMENT '老师唯一标识',
  `schoolId` varchar(36) NOT NULL COMMENT '学校唯一标识',
  `clazzId` varchar(36) NOT NULL COMMENT '班级唯一标识',
  `gradeName` varchar(8)  COMMENT '课程年级名称',
  `gradeLevel` TINYINT(2)  COMMENT '课程年级序列级别,1-12',
  `job` varchar(16)  COMMENT '职务,如班主任',
  `dateStarts` TIMESTAMP  COMMENT '开始日期',
  `dateEnds` TIMESTAMP  COMMENT '结束日期',
  PRIMARY KEY (`id`),
  KEY `x_sm_teacher_management_personId` (`personId`),
  KEY `x_sm_teacher_management_clazzId` (`clazzId`),
  KEY `x_sm_teacher_management_schoolId` (`schoolId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='老师班级教学信息表';

DROP TABLE IF EXISTS `sm_student`;
CREATE TABLE `sm_student` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `personId` varchar(36) NOT NULL COMMENT '学生唯一标识',
  `name` varchar(36) NOT NULL COMMENT '学生姓名',
  `gender` varchar(2)  COMMENT '性别',
  `birthday` TIMESTAMP  COMMENT '生日',
  `removed` TINYINT(1) default 0 COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `x_sm_student_personId` (`personId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='老师信息表';

DROP TABLE IF EXISTS `sm_student_contact`;
CREATE TABLE `sm_student_contact` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `personId` varchar(36) NOT NULL COMMENT '学生唯一标识',
  `category` varchar(8) NOT NULL COMMENT '联系方式类型:Phone-家庭电话;Mobile-移动电话;Email-电子信箱;QQ-qq号码;Weixin-微信',
  `name` varchar(16) NOT NULL COMMENT '联系方式名称',
  `info` varchar(2)  COMMENT '联系方式',
  `removed` TINYINT(1) default 0 COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `x_sm_student_contact_personId` (`personId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='学生联系信息表';

DROP TABLE IF EXISTS `sm_student_study`;
CREATE TABLE `sm_student_study` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `personId` varchar(36) NOT NULL COMMENT '老师唯一标识',
  `schoolId` varchar(36) NOT NULL COMMENT '学校唯一标识',
  `clazzId` varchar(36) NOT NULL COMMENT '班级唯一标识',
  `subjectId` varchar(36)  COMMENT '课程学科唯一标识',
  `gradeName` varchar(8)  COMMENT '课程年级名称',
  `gradeLevel` TINYINT(2)  COMMENT '课程年级序列级别,1-12',
  `courseName` varchar(16)  COMMENT '课程名称',
  `courseAlias` varchar(8)  COMMENT '课程简称',
  `dateStarts` TIMESTAMP  COMMENT '开始日期',
  `dateEnds` TIMESTAMP  COMMENT '结束日期',
  PRIMARY KEY (`id`),
  KEY `x_sm_student_study_personId` (`personId`),
  KEY `x_sm_student_study_clazzId` (`clazzId`),
  KEY `x_sm_student_study_schoolId` (`schoolId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='学生课程学习信息表';

DROP TABLE IF EXISTS `sm_student_managed`;
CREATE TABLE `sm_student_managed` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `personId` varchar(36) NOT NULL COMMENT '老师唯一标识',
  `schoolId` varchar(36) NOT NULL COMMENT '学校唯一标识',
  `clazzId` varchar(36) NOT NULL COMMENT '班级唯一标识',
  `job` varchar(16)  COMMENT '职务,如班长,学习委员',
  `dateStarts` TIMESTAMP  COMMENT '开始日期',
  `dateEnds` TIMESTAMP  COMMENT '结束日期',
  PRIMARY KEY (`id`),
  KEY `x_sm_student_managed_personId` (`personId`),
  KEY `x_sm_student_managed_clazzId` (`clazzId`),
  KEY `x_sm_student_managed_schoolId` (`schoolId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='学生受管班级信息表';