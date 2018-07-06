
DROP TABLE IF EXISTS `ts_school`;
CREATE TABLE `ts_school` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `schoolId` varchar(36) NOT NULL COMMENT '学校唯一标识',
  `tenantId` varchar(36) NOT NULL COMMENT '学校所属租户唯一标识',
  `name` varchar(32) NOT NULL COMMENT '学校名称',
  `alias` varchar(32) NOT NULL COMMENT '学校简称',
  `type` varchar(16) NOT NULL COMMENT '学校类型:Primary-小学;Middle-初中;High-高中;PrimaryToMiddlel-小学到初中;MiddleToHigh-初中到高中；All-小学到高中',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `x_ts_school_schoolId` (`schoolId`),
  KEY `x_ts_school_tenantId` (`tenantId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='学校信息表';

DROP TABLE IF EXISTS `ts_term`;
CREATE TABLE `ts_term` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `termId` varchar(36) NOT NULL COMMENT '学期唯一标识',
  `schoolId` varchar(36) NOT NULL COMMENT '学校所属租户唯一标识',
  `name` varchar(32) NOT NULL COMMENT '学期名称',
  `yearName` varchar(32) NOT NULL COMMENT '学年名称',
  `startsYear` SMALLINT (4) NOT NULL COMMENT '学年起始年度',
  `endsYear` SMALLINT(4) NOT NULL COMMENT '学年结束年度',
  `seq` varchar(8) NOT NULL COMMENT '学期序号：First-上学期Second-下学期',
  `starts` DATE  COMMENT '学期开始时间',
  `ends` DATE  COMMENT '学期结束时间',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `x_ts_term_schoolId` (`schoolId`),
  KEY `x_ts_term_termId` (`termId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='学期信息表';

DROP TABLE IF EXISTS `ts_staff`;
CREATE TABLE `ts_staff` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `schoolId` varchar(36) NOT NULL COMMENT '所属学校唯一标识,与表ts_school.schoolId关联',
  `staffId` varchar(36) NOT NULL COMMENT '教职工唯一标识，系统中所有人员是唯一的',
  `name` varchar(36) NOT NULL COMMENT '姓名',
  `gender` varchar(4) DEFAULT '无' COMMENT '教职工性别',
  `periodStarts` DATE  COMMENT '教职工入职时间',
  `periodEnds` DATE  COMMENT '教职工离职时间,为空时表示一直在职',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `x_ts_staff_schoolId` (`schoolId`),
  KEY `x_ts_staff_staffId` (`staffId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='教职工信息表';

DROP TABLE IF EXISTS `ts_staff_identity`;
CREATE TABLE `ts_staff_identity` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `staffId` varchar(36) NOT NULL COMMENT '教职工唯一标识，与表ts_staff.staffId关联',
  `identity` varchar(36) NOT NULL COMMENT '教职工身份证件号',
  `identityTypeName` varchar (16) DEFAULT '身份证'  COMMENT '教职工身份证件号类型：身份证, 教育云标识,QQ,微信,港澳台证件号, 工号,其他',
  `validityStarts` DATE  COMMENT '证件有效开始时间',
  `validityEnds` DATE  COMMENT '证件有效结束时间,为空时一直有效',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `x_ts_staff_staffId` (`staffId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='教职工身份标识表';

DROP TABLE IF EXISTS `ts_teacher`;
CREATE TABLE `ts_teacher` (
  `id` varchar(32)  NOT NULL,
  `schoolId` varchar(36) NOT NULL COMMENT '所属学校唯一标识,与表ts_school.schoolId关联',
  `identity` varchar(36) NOT NULL COMMENT '人员唯一标识，与表ts_staff.staffId关联',
  `name` varchar(36) NOT NULL COMMENT '姓名',
  `periodStarts` DATE  COMMENT '任教时间',
  `periodEnds` DATE  COMMENT '离任时间,为空时表示一直在职',
  `courseName` varchar(8) NOT NULL COMMENT '教学课程名称',
  `subjectId` varchar(36) NOT NULL COMMENT '教学课科目唯一标识，与外部系统保持一致',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `x_ts_teacher_schoolId` (`schoolId`),
  KEY `x_ts_teacher_identity` (`identity`),
  KEY `x_ts_teacher_subjectId` (`subjectId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='教师信息表';


DROP TABLE IF EXISTS `ts_leader`;
CREATE TABLE `ts_leader` (
  `id` varchar(32)  NOT NULL,
  `schoolId` varchar(36) NOT NULL COMMENT '所属学校唯一标识,与表ts_school.schoolId关联',
  `identity` varchar(36) NOT NULL COMMENT '人员唯一标识，与表ts_staff.staffId关联',
  `name` varchar(36) NOT NULL COMMENT '姓名',
  `periodStarts` DATE  COMMENT '入职时间',
  `periodEnds` DATE  COMMENT '离职时间,为空时表示一直在职',
  `post` varchar (8)   COMMENT '职位，如教导主任，教务主任等',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `x_ts_leader_schoolId` (`schoolId`),
  KEY `x_ts_leader_identity` (`identity`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='校领导信息表，如校长，教务主任等';

DROP TABLE IF EXISTS `ts_grade_master`;
CREATE TABLE `ts_grade_master` (
  `id` varchar(32)  NOT NULL,
  `schoolId` varchar(36) NOT NULL COMMENT '所属学校唯一标识,与表ts_school.schoolId关联',
  `identity` varchar(36) NOT NULL COMMENT '人员唯一标识，与表ts_staff.staffId关联',
  `name` varchar(36) NOT NULL COMMENT '姓名',
  `periodStarts` DATE  COMMENT '入职时间职',
  `periodEnds` DATE  COMMENT '离职时间,为空时表示一直在职',
  `gradeName` varchar (8)   COMMENT '年级名称',
  `gradeSeq` SMALLINT (2)   COMMENT '年级序列，数字1到12',
  `yearName` varchar(32) NOT NULL COMMENT '任职时学年',
  `yearStarts` SMALLINT (4) NOT NULL COMMENT '任职时学年起始年度',
  `yearEnds` SMALLINT(4) NOT NULL COMMENT '任职时学年结束年度',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `x_ts_grade_master_schoolId` (`schoolId`),
  KEY `x_ts_grade_master_identity` (`identity`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='年级主任信息表';

DROP TABLE IF EXISTS `ts_subject_master`;
CREATE TABLE `ts_subject_master` (
  `id` varchar(32)  NOT NULL,
  `schoolId` varchar(36) NOT NULL COMMENT '所属学校唯一标识,与表ts_school.schoolId关联',
  `identity` varchar(36) NOT NULL COMMENT '人员唯一标识，与表ts_staff.staffId关联',
  `name` varchar(36) NOT NULL COMMENT '姓名',
  `periodStarts` DATE  COMMENT '入职时间',
  `periodEnds` DATE  COMMENT '离职时间,为空时表示一直在职',
  `subjectId` varchar(36) NOT NULL COMMENT '负责学科唯一标识',
  `subjectName` varchar(16) NOT NULL COMMENT '负责学科名称',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `x_ts_subject_master_schoolId` (`schoolId`),
  KEY `x_ts_subject_master_staffId` (`identity`),
  KEY `x_ts_subject_master_subjectId` (`subjectId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='学科负责人信息表';

DROP TABLE IF EXISTS `ts_clazz_master`;
CREATE TABLE `ts_clazz_master` (
  `id` varchar(32)  NOT NULL,
  `schoolId` varchar(36) NOT NULL COMMENT '所属学校唯一标识,与表ts_school.schoolId关联',
  `clazzId` varchar(36) NOT NULL COMMENT '负责班级唯一标识',
  `identity` varchar(36) NOT NULL COMMENT '人员唯一标识，与表ts_staff.staffId关联',
  `name` varchar(36) NOT NULL COMMENT '姓名',
  `periodStarts` DATE  COMMENT '入职时间',
  `periodEnds` DATE  COMMENT '离职时间,为空时表示一直在职',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `x_ts_clazz_master_schoolId` (`schoolId`),
  KEY `x_ts_clazz_master_identity` (`identity`),
  KEY `x_ts_clazz_master_clazzId` (`clazzId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='班主任信息表';

DROP TABLE IF EXISTS `ts_clazz_teacher`;
CREATE TABLE `ts_clazz_teacher` (
  `id` varchar(32)  NOT NULL,
  `schoolId` varchar(36) NOT NULL COMMENT '所属学校唯一标识,与表ts_school.schoolId关联',
  `clazzId` varchar(36) NOT NULL COMMENT '负责班级唯一标识',
  `identity` varchar(36) NOT NULL COMMENT '人员唯一标识，与表ts_staff.staffId关联',
  `name` varchar(36) NOT NULL COMMENT '姓名',
  `periodStarts` DATE  COMMENT '入职时间',
  `periodEnds` DATE  COMMENT '离职时间,为空时表示一直在职',
  `subjectId` varchar(36) NOT NULL COMMENT '负责学科唯一标识',
  `subjectName` varchar(16) NOT NULL COMMENT '负责学科名称',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `x_ts_clazz_master_schoolId` (`schoolId`),
  KEY `x_ts_clazz_master_identity` (`identity`),
  KEY `x_ts_clazz_master_clazzId` (`clazzId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='班级学科教师信息表';

DROP TABLE IF EXISTS `ts_course`;
CREATE TABLE `ts_course` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `gradeName` varchar (8)   COMMENT '年级名称,为空表示全年级通用，如语文，英语，数学',
  `gradeLevel` SMALLINT (2)  COMMENT '年级序列，数字1到12',
  `name` varchar (8) NOT NULL  COMMENT '课程名称，如语文，英语，数学',
  `subjectId` varchar (36) NOT NULL  COMMENT '课程科目唯一标识',
  `schoolId` varchar(36)  COMMENT '开设学校唯一标识，如果为空，表示通用',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `x_ts_course_subjectId` (`subjectId`),
  KEY `x_ts_course_schoolId` (`schoolId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='课程信息表';
insert into `ts_course`(`name`,`subjectId`) values('语文','Easytnt_SBID_001'),('数学','Easytnt_SBID_002'),('英语','Easytnt_SBID_003');
insert into `ts_course`(`gradeName`,`gradeLevel`,`name`,`subjectId`) values('七年级',7,'政治','Easytnt_SBID_004'),('七年级',7,'历史','Easytnt_SBID_005'),('七年级',7,'地理','Easytnt_SBID_006'),('七年级',7,'生物','Easytnt_SBID_009');
insert into `ts_course`(`gradeName`,`gradeLevel`,`name`,`subjectId`) values('八年级',8,'政治','Easytnt_SBID_004'),('八年级',8,'历史','Easytnt_SBID_005'),('八年级',8,'地理','Easytnt_SBID_006'),('八年级',8,'物理','Easytnt_SBID_007'),('八年级',8,'生物','Easytnt_SBID_009');
insert into `ts_course`(`gradeName`,`gradeLevel`,`name`,`subjectId`) values('九年级',9,'政治','Easytnt_SBID_004'),('九年级',9,'物理','Easytnt_SBID_007'),('九年级',9,'化学','Easytnt_SBID_008');
insert into `ts_course`(`gradeName`,`gradeLevel`,`name`,`subjectId`) values('高一',10,'政治','Easytnt_SBID_004'),('高一',10,'历史','Easytnt_SBID_005'),('高一',10,'地理','Easytnt_SBID_006'),('高一',10,'物理','Easytnt_SBID_007'),('高一',10,'化学','Easytnt_SBID_008'),('高一',10,'生物','Easytnt_SBID_009');
insert into `ts_course`(`gradeName`,`gradeLevel`,`name`,`subjectId`) values('高二',11,'政治','Easytnt_SBID_004'),('高二',11,'历史','Easytnt_SBID_005'),('高二',11,'物理','Easytnt_SBID_007'),('高二',11,'化学','Easytnt_SBID_008'),('高二',11,'生物','Easytnt_SBID_009');
insert into `ts_course`(`gradeName`,`gradeLevel`,`name`,`subjectId`) values('高三',12,'政治','Easytnt_SBID_004'),('高三',12,'历史','Easytnt_SBID_005'),('高三',12,'地理','Easytnt_SBID_006'),('高三',12,'物理','Easytnt_SBID_007'),('高三',12,'化学','Easytnt_SBID_008'),('高三',12,'生物','Easytnt_SBID_009');


DROP TABLE IF EXISTS `ts_clazz`;
CREATE TABLE `ts_clazz` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `schoolId` varchar(36) NOT NULL COMMENT '所属学校唯一标识',
  `clazzId` varchar(36) NOT NULL COMMENT '班级唯一标识',
  `clazzNo` varchar(16) NOT NULL COMMENT '班级编号',
  `name` varchar (8)  NOT NULL COMMENT '班级名称',
  `createDate` varchar (8)  NOT NULL COMMENT '班级创建日期 YYYY-mm',
  `adminType` varchar (8) NOT NULL  COMMENT '班级管理类型：Admin-行政班,Teach-教学班,Mixture-不分',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `x_ts_clazz_schoolId` (`schoolId`),
  KEY `x_ts_clazz_clazzId` (`clazzId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='班级信息表';

DROP TABLE IF EXISTS `ts_clazz_catagory`;
CREATE TABLE `ts_clazz_catagory` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `clazzId` varchar(36) NOT NULL COMMENT '班级唯一标识,与表ts_clazz.classId关联',
  `code` varchar (8)  NOT NULL COMMENT '班级分类代码',
  `name` varchar (8) NOT NULL  COMMENT '班级分类名称，如理科，文科，重点，普通，实验等',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `x_ts_clazz_clazzId_catagory` (`clazzId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='班级分类信息表，如理科班，文科班，重点班，普通班，实验班等';

DROP TABLE IF EXISTS `ts_clazz_term_history`;
CREATE TABLE `ts_clazz_term_history` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `clazzId` varchar(36) NOT NULL COMMENT '班级唯一标识,与表ts_clazz.classId关联',
  `termId` varchar(36) NOT NULL COMMENT '学期唯一标识,与表ts_term.termId关联',
  `termName` varchar(8) NOT NULL COMMENT '学期名称：First-上学期，Second-下学期',
  `gradeName` varchar (8)   COMMENT '年级名称',
  `gradeSeq` SMALLINT (2)   COMMENT '年级序列，数字1到12',
  `yearName` varchar(32) NOT NULL COMMENT '学年',
  `yearStarts` SMALLINT (4) NOT NULL COMMENT '学年起始年度',
  `yearEnds` SMALLINT(4) NOT NULL COMMENT '学年结束年度',
  `wl` SMALLINT DEFAULT 0  COMMENT '文理分科：0－不分；2-理科；1-文科',
  `starts` DATE  COMMENT '开始时间，与学期开学时间一致',
  `ends` DATE  COMMENT '结束时间，与学期结束时间一致',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `x_ts_clazz_history_clazzId` (`clazzId`),
  KEY `x_ts_clazz_history_termId` (`termId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='班级学期记录表';

DROP TABLE IF EXISTS `ts_student`;
CREATE TABLE `ts_student` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `schoolId` varchar(36) NOT NULL COMMENT '所属学校唯一标识',
  `studentId` varchar(36) NOT NULL COMMENT '学生唯一标识，系统中所有学生是唯一的',
  `name` varchar(36) NOT NULL COMMENT '姓名',
  `gender` varchar(4) DEFAULT '无' COMMENT '学生性别',
  `joinDate` DATE  COMMENT '入校时间',
  `leafDate` DATE  COMMENT '离校时间,为空时表示一直在校',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `x_ts_student_schoolId` (`schoolId`),
  KEY `x_ts_student_studentId` (`studentId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='学生信息表';

DROP TABLE IF EXISTS `ts_student_belong`;
CREATE TABLE `ts_student_belong` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `schoolId` varchar(36) NOT NULL COMMENT '所属学校唯一标识',
  `clazzId` varchar(36) NOT NULL COMMENT '所属班级唯一标识',
  `studentId` varchar(36) NOT NULL COMMENT '学生唯一标识，系统中所有学生是唯一的',
  `joinDate` DATE  COMMENT '入校时间',
  `leafDate` DATE  COMMENT '离校时间,为空时表示一直在校',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `x_ts_student_clazzId` (`clazzId`),
  KEY `x_ts_student_studentId` (`studentId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='学生管理班级信息表';

DROP TABLE IF EXISTS `ts_student_study`;
CREATE TABLE `ts_student_study` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `schoolId` varchar(36) NOT NULL COMMENT '所属学校唯一标识',
  `clazzId` varchar(36) NOT NULL COMMENT '所属班级唯一标识',
  `studentId` varchar(36) NOT NULL COMMENT '学生唯一标识，系统中所有学生是唯一的',
  `gradeName` varchar (8)   COMMENT '年级名称',
  `gradeSeq` SMALLINT (2)   COMMENT '年级序列，数字1到12',
  `yearName` varchar(32) NOT NULL COMMENT '学年',
  `yearStarts` SMALLINT (4) NOT NULL COMMENT '学年起始年度',
  `yearEnds` SMALLINT(4) NOT NULL COMMENT '学年结束年度',
  `subjectId` varchar(36) NOT NULL COMMENT '学习课程的学科唯一标识',
  `courseName` varchar(16) NOT NULL COMMENT '学习课程名称',
  `starts` DATE  COMMENT '学习开始时间',
  `ends` DATE  COMMENT '学习完成时间,为空时表示一直在校',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `x_ts_student_clazzId` (`clazzId`),
  KEY `x_ts_student_studentId` (`studentId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='学生学习班级信息表';

DROP TABLE IF EXISTS `ts_student_identity`;
CREATE TABLE `ts_student_identity` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `studentId` varchar(36) NOT NULL COMMENT '学生唯一标识，关联表ts_student_study.studentId',
  `identity` varchar(36) NOT NULL COMMENT '学生身份证件号',
  `identityTypeName` varchar (16) DEFAULT '身份证'  COMMENT '学生身份证件名，身份证, 学籍号, 学号,教育云标识,QQ,微信,港澳台证件号, 考号,其他',
  `validityStarts` DATE  COMMENT '证件有效开始时间',
  `validityEnds` DATE  COMMENT '证件有效结束时间,为空时一直有效',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `x_ts_student_identity_studentId` (`studentId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='学生身份标识表';


DROP TABLE IF EXISTS `ts_configuation`;
CREATE TABLE `ts_configuation` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `schoolId` varchar(36) NOT NULL COMMENT '所属学校唯一标识',
  `name` varchar(36) NOT NULL COMMENT '系统配置名称',
  `value` varchar(255) NOT NULL COMMENT '系统配置值',
  `description` varchar (1000) COMMENT '系统配置说明',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `x_ts_school_configuation` (`schoolId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='学校系统配置信息表';
