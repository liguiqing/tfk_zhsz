DROP DATABASE IF EXISTS `tfk_paper_marking`;
CREATE DATABASE `tfk_paper_marking` CHARACTER SET utf8 ;
USE `tfk_paper_marking`;

DROP TABLE IF EXISTS `t_ps_Dict`;
CREATE TABLE `t_ps_Dict` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `dictId` VARCHAR(36) NOT NULL COMMENT '字典唯一标识',
  `tableName` VARCHAR(36) NOT NULL COMMENT '表名',  
  `culumnName` VARCHAR(36) NOT NULL COMMENT '字段名',
  `dataType` VARCHAR(36) NOT NULL COMMENT '数据类型，与使用的数据数据库系统有关，默认是Mysql',
  `maxLength` SMALLINT(4)  COMMENT '数据大长度,-1表示未知',  
  `readonly` TINYINT(1) DEFAULT '0' COMMENT '是否只读，0-非；1-是',   
  PRIMARY KEY (`id`), 
  KEY `x_ps_Dict_dictId` (`dictId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－考试业务字典表';

DROP TABLE IF EXISTS `t_ps_DictValue`;
CREATE TABLE `t_ps_DictValue` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `dictId` VARCHAR(36) NOT NULL COMMENT '字典唯一标识',  
  `fieldName` VARCHAR(36) NOT NULL COMMENT '字典名',
  `fieldValue` VARCHAR(36) NOT NULL COMMENT '字典值',
  PRIMARY KEY (`id`), 
  KEY `x_ps_DictValue_dictId` (`dictId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－考试业务字典表';

DROP TABLE IF EXISTS `t_ps_OutInterface`;
CREATE TABLE `t_ps_OutInterface` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `interfaceId` VARCHAR(128) NOT NULL COMMENT '外部服务唯一标识',
  `server` VARCHAR(256) NOT NULL COMMENT '接口服务域名或者IP及端口，如http://api.exsample.com:9988',  
  `url` VARCHAR(256) NOT NULL COMMENT '接口服务地址，如/api/foo/bar/#?p1=#&p2=#,参数用#表示', 
  `httpMethod` VARCHAR(8)  COMMENT 'Http Method，为空时支持所有方法：GET,POST,PUT,DELTE,OPTIONAL,TRACE',
  `requestBody` TEXT  COMMENT '使用POST/PUT时request body json串',   
  `version` VARCHAR(8)  COMMENT '接口版本',  
  `spec` TEXT  COMMENT '接口说明',  
  `status` TINYINT(1) DEFAULT '1' COMMENT '使用状态，0-不可用;1-可用',   
  PRIMARY KEY (`id`), 
  KEY `x_ps_OutInterface_interfaceId` (`interfaceId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷-外部接口';

/***项目、考试、科目、参考机构（学校|班级）**/
DROP TABLE IF EXISTS `t_ps_MarkingProject`;
CREATE TABLE `t_ps_MarkingProject` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `projectId` VARCHAR(36) NOT NULL COMMENT '阅卷项目唯一标识',
  `creatorId` VARCHAR(36) NOT NULL COMMENT '考试项目创建者标识', 
  `managerIds` TEXT COMMENT '考试项目管理者标识', 
  `name` VARCHAR(128) NOT NULL COMMENT '项目名称', 
  `status` TINYINT(1) DEFAULT '0' COMMENT '状态，1-可用；0-作废；9-完成',  
  PRIMARY KEY (`id`), 
  KEY `x_ps_MarkingProject_projectId` (`projectId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－阅卷项目';
SET @dict_uuid_index = 1;
SET @dict_uuid = CONCAT('DICT-0000000',@dict_uuid_index);
INSERT INTO `t_ps_Dict` (dictId,tableName,culumnName,dataType,maxLength,readonly) VALUES (@dict_uuid,'t_ps_MarkingProject','status','TINYINT',1,1);
INSERT INTO `t_ps_DictValue` (dictId,fieldName,fieldValue) VALUES (@dict_uuid,'可用','1'),(@dict_uuid,'作废','0'),(@dict_uuid,'完成','9');

DROP TABLE IF EXISTS `t_ps_Exam`;
CREATE TABLE `t_ps_Exam` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `examId` VARCHAR(36) NOT NULL COMMENT '考试唯一标识',
  `projectId` VARCHAR(36) NOT NULL COMMENT '阅卷项目唯一标识', 
  `targetOrgId` VARCHAR(36) NOT NULL COMMENT '考试使用机构标识，值对应表t_ps_TestedOrg.orgId',  
  `creatorOrgId` VARCHAR(36) NOT NULL COMMENT '考试创建机构标识',
  `creatorOrgType` TINYINT(1) NOT NULL COMMENT '考试创建机构类型：1-学校；2-区县；3-地市',
  `creatorId` VARCHAR(36) NOT NULL COMMENT '考试创建者标识',  
  `name` VARCHAR(128) NOT NULL COMMENT '考试名称',
  `gradeName` VARCHAR(16) NOT NULL COMMENT '考试年级名称',
  `studyYearStarts` SMALLINT(4) NOT NULL COMMENT '考试年级开始学年',
  `studyYearEnds` SMALLINT(4) NOT NULL COMMENT '考试年级结束学年',
  `starts` DATETIME  COMMENT '考试开始时间',  
  `ends` DATETIME  COMMENT '阅卷完成时间',    
  `status` TINYINT(1) DEFAULT '0' COMMENT '行进状态，1-初始；9-完成；0-作废', 
  `catagory` TINYINT(2) NOT NULL COMMENT '考试分类：1-周练；2-月考；3－单元测验；4－期中考试；5－期末考试；6－中考模拟考；7－高考模拟考',
  `scope` TINYINT(1)  COMMENT '考试范围：1-校内考试;2-校际联考;3-区域统考;'  ,   
  `createTime` TIMESTAMP DEFAULT NOW() COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP DEFAULT NOW() COMMENT '最后更新时间',
  `lastOperatorId` VARCHAR(36)  COMMENT '最后更新者唯一标识',
  `lastOperatorName` VARCHAR(64)  COMMENT '最后更新者姓名',    
  PRIMARY KEY (`id`), 
  KEY `x_ps_Exam_examId` (`examId`),
  KEY `x_ps_Exam_orgId` (`creatorOrgId`),
  KEY `x_ps_Exam_creatorId` (`creatorId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=eutf8 COMMENT='阅卷－考试信息';
SET @dict_uuid_index = 2;
SET @dict_uuid = CONCAT('DICT-0000000',@dict_uuid_index);
INSERT INTO `t_ps_Dict` (dictId,tableName,culumnName,dataType,maxLength,readonly) VALUES (@dict_uuid,'t_ps_Exam','creatorOrgType','TINYINT',1,0);
INSERT INTO `t_ps_DictValue` (dictId,fieldName,fieldValue) VALUES (@dict_uuid,'学校','1'),(@dict_uuid,'区县','2'),(@dict_uuid,'地市','3');

DROP TABLE IF EXISTS `t_ps_Subject`;
CREATE TABLE `t_ps_Subject` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `subjectId` VARCHAR(36) NOT NULL COMMENT '考试科目唯一标识',  
  `examId` VARCHAR(36) NOT NULL COMMENT '考试唯一标识',
  `targetSubjectId` VARCHAR(36) NOT NULL COMMENT '科目唯一标识,外部系统编码', 
  `targetSubjectName` VARCHAR(16) COMMENT '外部系统科目名称',    
  `creatorId` VARCHAR(36) NOT NULL COMMENT '科目创建者标识',    
  `name` VARCHAR(128) NOT NULL COMMENT '科目名称',
  `sheets` TINYINT(1) DEFAULT '1'  COMMENT '答题卡类型数，1-单卡;2-AB卡',   
  `score` FLOAT(3,2)  COMMENT '满分', 
  `kgScore` FLOAT(3,2)  COMMENT '客观题分',
  `zgScore` FLOAT(3,2)  COMMENT '主观题分',       
  `status` TINYINT(2) DEFAULT '0' COMMENT '评卷进行状态，0-初始；1－扫描；2－评卷；9-完成；-1-作废',  
  `markWay` TINYINT(1) DEFAULT '1' COMMENT '阅卷方式，1-网阅；2－机器阅卷；3－手阅 ', 
  `lastUpdateTime` TIMESTAMP DEFAULT NOW() COMMENT '最后更新时间',
  `lastOperatorId` VARCHAR(36)  COMMENT '最后更新者唯一标识',
  `lastOperatorName` VARCHAR(64)  COMMENT '最后更新者姓名',  
  PRIMARY KEY (`id`), 
  KEY `x_ps_Subject_examId` (`examId`),
  KEY `x_ps_Subject_targetSubjectId` (`targetSubjectId`) ,
  KEY `x_ps_Subject_subjectId` (`subjectId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－考试科目';

DROP TABLE IF EXISTS `ps_Paper`;
CREATE TABLE `ps_Paper` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `paperId` VARCHAR(36) NOT NULL COMMENT '考试科目唯一标识',  
  `subjectId` VARCHAR(36) NOT NULL COMMENT '考试科目唯一标识',  
  `examId` VARCHAR(36) NOT NULL COMMENT '考试唯一标识', 
  `targetSubjectId` VARCHAR(36) COMMENT '科目唯一标识,外部系统统一编码',  
  `personId` VARCHAR(36) NOT NULL COMMENT '试卷上传者唯一标识',
  `name` VARCHAR(128) NOT NULL COMMENT '试卷名称',
  `address` VARCHAR(256)  COMMENT '试卷存储的绝对地址',
  PRIMARY KEY (`id`), 
  KEY `x_ps_Paper_examId` (`paperId`),
  KEY `x_ps_Paper_targetSubjectId` (`targetSubjectId`) ,
  KEY `x_ps_Paper_subjectId` (`subjectId`),
  KEY `x_ps_Paper_personId` (`personId`)  
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷-考试科目用卷';


DROP TABLE IF EXISTS `t_ps_SubjectProgress`;
CREATE TABLE `t_ps_SubjectProgress` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `examId` VARCHAR(36) NOT NULL COMMENT '考试唯一标识',
  `subjectId` VARCHAR(36) NOT NULL COMMENT '考试科目唯一标识',   
  `name` VARCHAR(128) NOT NULL COMMENT '进程名称', 
  `status` TINYINT(1) DEFAULT '0' COMMENT '行进状态，0-未开始；1－正在进行；9-完成；',  
  PRIMARY KEY (`id`), 
  KEY `x_ps_Subject_examId` (`examId`),
  KEY `x_ps_Subject_subjectId` (`subjectId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－考试科目工作进程，科目创建时，通过考试类型及所选择的配置生成数据，在阅卷过程过更新数据';


DROP TABLE IF EXISTS `t_ps_ExamMarkControlStrategy`;
CREATE TABLE `t_ps_ExamMarkControlStrategy` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `examId` VARCHAR(36) NOT NULL COMMENT '考试唯一标识', 
  `target` VARCHAR(36) NOT NULL COMMENT '控制目标：Exam-考试；Subject-科目；Item-评题',  
  `targetId` VARCHAR(36) NOT NULL COMMENT '控制目标唯一标识,target=Exam时源于t_ps_exam;target=Subject时t_ps_subject;target=Item时t_ps_markItem',  
  `strategy` VARCHAR(36) NOT NULL COMMENT '控制器名称,可以是包＋类名或者Spring的classId',
  `val` VARCHAR(128)  COMMENT '控制策略自定义值',    
  PRIMARY KEY (`id`), 
  KEY `x_ps_ExamMarkControlStrategy_examId` (`examId`),
  KEY `x_ps_ExamMarkControlStrategy_targetId` (`targetId`) 
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－考试阅卷控制配置，可控制的范围：考试；考试科目；评题';

DROP TABLE IF EXISTS `ps_ExamMarkControlStrategyCustom`;
CREATE TABLE `ps_ExamMarkControlStrategyCustom` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `personId` VARCHAR(36) NOT NULL COMMENT '用户唯一标识', 
  `target` VARCHAR(36) NOT NULL COMMENT '控制目标：Exam-考试;Subject-科目;Item-评题',
  `targetId` VARCHAR(36) COMMENT '控制目标唯一标识,为空时表示通用控制；target=Exam时源于t_ps_exam;target=Subject时t_ps_subject;target=Item时t_ps_markItem',    
  `strategy` VARCHAR(36) NOT NULL COMMENT '控制策略名称,可以是包＋类名或者Spring的classId',
  `val` VARCHAR(128)  COMMENT '控制策略自定义值',  
  PRIMARY KEY (`id`), 
  INDEX `x_ps_ExamMarkControlStrategyCustom_personId` (`personId`),
  INDEX `x_ps_ExamMarkControlStrategyCustom_targetId` (`targetId`) 
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷-个人阅卷控制策略';

DROP TABLE IF EXISTS `t_ps_TestedOrg`;
CREATE TABLE `t_ps_TestedOrg` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `examId` VARCHAR(36) NOT NULL COMMENT '考试唯一标识',
  `targetOrgId` VARCHAR(36) NOT NULL COMMENT '机构唯一标识,源于外部系统',   
  `parentOrgId` VARCHAR(36) COMMENT '参考试上级机构标识',  
  `name` VARCHAR(128)  COMMENT '参考试机构名称',
  `code` VARCHAR(32)  COMMENT '参考试机构代码',
  `catagory` TINYINT(1)  COMMENT '参考试机构分类：1－班级；2：学校',
  `orgSeq` TINYINT(3)  COMMENT '机构序号',
  `lastUpdateTime` TIMESTAMP DEFAULT NOW() COMMENT '最后更新时间',
  `lastOperatorId` VARCHAR(36)  COMMENT '最后更新者唯一标识',
  `lastOperatorName` VARCHAR(64)  COMMENT '最后更新者姓名',   
  PRIMARY KEY (`id`), 
  KEY `x_ps_TestedOrg_examId` (`examId`),
  KEY `x_ps_TestedOrg_targetOrgId` (`targetOrgId`),
  KEY `x_ps_TestedOrg_parentOrgId` (`parentOrgId`) 
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－参加考试的机构；校考是班级；联考统考是学校';

/***阅卷管理人员**/
DROP TABLE IF EXISTS `t_ps_ExamManager`;
CREATE TABLE `t_ps_ExamManager` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `examId` VARCHAR(36) NOT NULL COMMENT '考试唯一标识',
  `personId` VARCHAR(36) NOT NULL COMMENT '负责唯一标识,源于外部系统',     
  `name` VARCHAR(128) NOT NULL COMMENT '负责人姓名' ,
  `role` TINYINT(2)  DEFAULT '1' COMMENT '管理角色：1－管理员；2－监督员；3-扫描负责人；4－识别异常处理员；５－识别多评仲裁员',  
  PRIMARY KEY (`id`), 
  KEY `x_ps_ExamManager_examId` (`examId`),
  KEY `x_ps_ExamManager_personId` (`personId`),
  INDEX `x_ps_ExamManager_role` (`role`)   
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－考试管理人员';

DROP TABLE IF EXISTS `t_ps_SubjectManager`;
CREATE TABLE `t_ps_SubjectManager` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `examId` VARCHAR(36) NOT NULL COMMENT '考试唯一标识',
  `subjectId` VARCHAR(36) NOT NULL COMMENT '考试科目唯一标识',  
  `personId` VARCHAR(36) NOT NULL COMMENT '负责唯一标识,源于外部系统',     
  `name` VARCHAR(128) NOT NULL COMMENT '负责人姓名' ,
  `role` TINYINT(2)  DEFAULT '1' COMMENT '管理角色：１－管理员；２－评卷人员分配者；３－主客观题参考答案录入者；４－主客观题参考答案审核者；５－答题卡设计师者',      
  PRIMARY KEY (`id`), 
  KEY `x_ps_SubjectManager_examId` (`examId`),
  KEY `x_ps_SubjectManager_subjectId` (`subjectId`),  
  KEY `x_ps_SubjectManager_personId` (`personId`),
  INDEX `x_ps_SubjectManager_role` (`role`) 
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－科目管理类人员';

/***考生***/
DROP TABLE IF EXISTS `t_ps_Examinee`;
CREATE TABLE `t_ps_Examinee` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `examineeId` VARCHAR(36) NOT NULL COMMENT '考生唯一标识',  
  `examId` VARCHAR(36) NOT NULL COMMENT '考试唯一标识',
  `personId` VARCHAR(36) NOT NULL COMMENT '考生唯一标识,源于外部系统',   
  `schoolId` VARCHAR(36)  COMMENT '考生所在学校唯一标识；源于ps_TestedOrg.targetOrgId',    
  `schoolName` VARCHAR(123) COMMENT '学校名称',
  `clazzId` VARCHAR(36)  COMMENT '考生所在班级唯一标识；源于ps_TestedOrg.targetOrgId',  
  `clazzName` INT(8)  COMMENT '考生所在班级名称',   
  `name` VARCHAR(128) NOT NULL COMMENT '考生姓名',    
  `examNumber` VARCHAR(32) NOT NULL COMMENT '考号',
  `point` VARCHAR(16)  COMMENT '考点号',  
  `room` VARCHAR(16)  COMMENT '考场号',  
  `seat` TINYINT(3) COMMENT '座位号',
  `lastUpdateTime` TIMESTAMP DEFAULT NOW() COMMENT '最后更新时间',
  `lastOperatorId` VARCHAR(36)  COMMENT '最后更新者唯一标识',
  `lastOperatorName` VARCHAR(64)  COMMENT '最后更新者姓名',   
  PRIMARY KEY (`id`), 
  KEY `x_ps_Examinee_examId` (`examId`),
  KEY `x_ps_Examinee_examineeId` (`examineeId`),
  KEY `x_ps_Examinee_schoolId` (`schoolId`) , 
  KEY `x_ps_Examinee_clazzId` (`clazzId`) ,
  KEY `x_ps_Examinee_examNumber` (`examNumber`) ,
  KEY `x_ps_Examinee_room` (`room`)  
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－考生信息';

DROP TABLE IF EXISTS `t_ps_SubjectRigister`;
CREATE TABLE `t_ps_SubjectRigister` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `examId` VARCHAR(36) NOT NULL COMMENT '报考考试唯一标识',
  `examineeId` VARCHAR(36) NOT NULL COMMENT '考生唯一标识',   
  `subjectId` VARCHAR(36) NOT NULL COMMENT '报考科目唯一标识', 
  `attended` TINYINT(1) DEFAULT '2' COMMENT '参与标志：1－参考(YES)；2-缺卡(None)；3-缺考(No)；4－作弊(Cogged),默认缺卡,扫描及考号异常处理时更新',
  `lastUpdateTime` TIMESTAMP DEFAULT NOW() COMMENT '最后更新时间',
  `lastOperatorId` VARCHAR(36)  COMMENT '最后更新者唯一标识',
  `lastOperatorName` VARCHAR(64)  COMMENT '最后更新者姓名', 
  PRIMARY KEY (`id`), 
  KEY `x_ps_SubjectRigister_examId` (`examId`),
  KEY `x_ps_SubjectRigister_subjectId` (`subjectId`),
  KEY `x_ps_SubjectRigister_examineeId` (`examineeId`) ,
  KEY `x_ps_SubjectRigister_attended` (`attended`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－考生报考科目';

/***考试答题卡**/
DROP TABLE IF EXISTS `t_ps_AnswerSheet`;
CREATE TABLE `t_ps_AnswerSheet` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `answerSheetId` VARCHAR(36) NOT NULL COMMENT '答题卡唯一标示',
  `examId` VARCHAR(36) NOT NULL COMMENT '考试唯一标识',
  `subjectId` VARCHAR(36) NOT NULL COMMENT '科目唯一标识',  
  `designerId` VARCHAR(36) NOT NULL COMMENT '设计唯一标识',   
  `name` VARCHAR(32) NOT NULL COMMENT '名称',
  `catagory` VARCHAR(4) DEFAULT '' COMMENT 'AB卡时值为AB',
  `sheets` TINYINT(2) DEFAULT '1' COMMENT '卡数',  
  `pages` TINYINT(2) DEFAULT '2' COMMENT '页数',
  `lastUpdateTime` TIMESTAMP DEFAULT NOW() COMMENT '最后更新时间',
  `lastOperatorId` VARCHAR(36)  COMMENT '最后更新者唯一标识',
  `lastOperatorName` VARCHAR(64)  COMMENT '最后更新者姓名',   
  PRIMARY KEY (`id`), 
  KEY `x_ps_AnswerSheet_answerSheetId` (`answerSheetId`),
  KEY `x_ps_AnswerSheet_examId` (`examId`),
  KEY `x_ps_AnswerSheet_subjectId` (`subjectId`),
  KEY `x_ps_AnswerSheet_designerId` (`designerId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－科目答题卡';

DROP TABLE IF EXISTS `t_ps_AnswerSheetPage`;
CREATE TABLE `t_ps_AnswerSheetPage` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `answerSheetId` VARCHAR(36) NOT NULL COMMENT '答题卡唯一标识',
  `paperType` VARCHAR(4) DEFAULT 'A4' COMMENT '纸型：A3｜A4等',  
  `sheet` TINYINT(2) NOT NULL COMMENT '张码', 
  `page` TINYINT(2) NOT NULL COMMENT '页码',  
  `w` SMALLINT(4)  COMMENT '答题卡页图片的宽度',
  `h` SMALLINT(4)  COMMENT '答题卡页图片的高度',  
  `imgUrl` VARCHAR(256)  COMMENT '纸张对应的扫瞄图片地址', 
  `roate` SMALLINT(3) DEFAULT '0' COMMENT '视觉与原图的旋转角度 -360--360', 
  `features` TEXT  COMMENT '答题卡信息特征,默认JSON',  
  `examNumberFeatures` TEXT  COMMENT '考号特征,默认JSON',
  `kgFeatures` TEXT  COMMENT '客观题信息特征,默认JSON', 
  `zgFeatures` TEXT  COMMENT '主观题信息特征,默认JSON', 
  `zgOptionalFeatures` TEXT  COMMENT '选做题信息特征,默认JSON',  
  `lastUpdateTime` TIMESTAMP DEFAULT NOW() COMMENT '最后更新时间',
  `lastOperatorId` VARCHAR(36)  COMMENT '最后更新者唯一标识',
  `lastOperatorName` VARCHAR(64)  COMMENT '最后更新者姓名',   
  PRIMARY KEY (`id`),  
  KEY `x_ps_AnswerSheetPage_answerSheetId` (`answerSheetId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－科目答题页';

DROP TABLE IF EXISTS `t_ps_AnswerSheetItem`;
CREATE TABLE `t_ps_AnswerSheetItem` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `answerSheetItemId` VARCHAR(36) NOT NULL COMMENT '答题卡题目唯一标识',
  `parentItemId` VARCHAR(36)  COMMENT '所属于大题唯一标识',  
  `examId` VARCHAR(36) NOT NULL COMMENT '考试唯一标识',
  `subjectId` VARCHAR(36) NOT NULL COMMENT '科目唯一标识',    
  `answerSheetId` VARCHAR(36) NOT NULL COMMENT '答题卡唯一标识',
  `originItemId` VARCHAR(36) NOT NULL COMMENT '库题题目唯一标识',
  `targetSubjectId` VARCHAR(36) NOT NULL COMMENT '科目唯一标识,外部系统编码', 
  `targetSubjectName` VARCHAR(16) COMMENT '外部系统科目名称',  
  `name` VARCHAR(128)  COMMENT '题目名称',
  `catagory1` TINYINT(1)  COMMENT '题目一级类型：1－客观题(KG)；2－主观题(KG)',
  `catagory2` TINYINT(2)  COMMENT '题目二级类型：1－单选题(Single)；2－多选题(Multi)；3－判断题(TF)；5－填空题(Blank))；6－填空题(Blank)；7－计算器题(Calculate)等',   
  `score` FLOAT(3,2)  COMMENT '满分',
  `required` TINYINT(1) DEFAULT '1' COMMENT '是必做:0-选做；1-必做',   
  `content` TEXT  COMMENT '参考答案',
  `stType` TINYINT(1)  COMMENT '答案类型：1-json；2-文本；3-url', 
  `readonly` TINYINT(1)  COMMENT '编辑状态：0－不可编辑；１－可编辑',  
  `lastUpdateTime` TIMESTAMP DEFAULT NOW() COMMENT '最后更新时间',
  `lastOperatorId` VARCHAR(36)  COMMENT '最后更新者唯一标识',
  `lastOperatorName` VARCHAR(64)  COMMENT '最后更新者姓名',    
  PRIMARY KEY (`id`), 
  KEY `x_ps_AnswerSheetItem_examId` (`examId`),
  KEY `x_ps_AnswerSheetItem_subjectId` (`subjectId`),
  INDEX `x_ps_AnswerSheetItem_targetSubjectId` (`targetSubjectId`),     
  KEY `x_ps_AnswerSheetItem_answerSheetId` (`answerSheetId`),
  KEY `x_ps_AnswerSheetItem_answerSheetItemId` (`answerSheetItemId`),
  KEY `x_ps_AnswerSheetItem_parentItemId` (`parentItemId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－科目答题卡题目';

DROP TABLE IF EXISTS `t_ps_AnswerSheetItemStEdit`;
CREATE TABLE `t_ps_AnswerSheetItemStEdit` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `answerSheetEditId` VARCHAR(36) NOT NULL COMMENT '答题卡唯一标识',  
  `answerSheetId` VARCHAR(36) NOT NULL COMMENT '答题卡唯一标识',
  `answerSheetItemId` VARCHAR(36) NOT NULL COMMENT '答题卡题目唯一标识', 
  `personId` VARCHAR(36)  COMMENT '编辑人唯一标识',    
  `editor` VARCHAR(128)  COMMENT '编辑人姓名',   
  `updateTime` TIMESTAMP DEFAULT NOW() COMMENT '更新时间',      
  PRIMARY KEY (`id`), 
  KEY `x_ps_AnswerSheetItemEdit_answerSheetId` (`answerSheetId`),
  KEY `x_ps_AnswerSheetItemEdit_answerSheetItemId` (`answerSheetItemId`),
  KEY `x_ps_AnswerSheetItemEdit_personId` (`personId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－科目答题卡题目参考答案编辑记录，如客观题答案录入';

DROP TABLE IF EXISTS `t_ps_AnswerSheetItemEditorSt`;
CREATE TABLE `t_ps_AnswerSheetItemEditorSt` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,  
  `answerSheetEditId` VARCHAR(36) NOT NULL COMMENT '答题卡题目唯一标识',   
  `content` TEXT  COMMENT '参考答案',
  `stType` TINYINT(1) DEFAULT '1' COMMENT '答案类型：1-json；2-文本；3-url', 
  `readonly` TINYINT(1)  COMMENT '编辑状态：0－不可编辑；１－可编辑',   
  PRIMARY KEY (`id`), 
  KEY `x_ps_AnswerSheetItemSt_answerSheetEditId` (`answerSheetEditId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－科目答题卡题编辑者录入的参考答案';


/***考生答题卡扫描***/
DROP TABLE IF EXISTS `t_ps_BatchScan`;
CREATE TABLE `t_ps_BatchScan` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `batchId` VARCHAR(36) NOT NULL COMMENT '扫描批次唯一标识',  
  `examId` VARCHAR(36) NOT NULL COMMENT '考试唯一标识',
  `subjectId` VARCHAR(36) NOT NULL COMMENT '科目唯一标识',
  `answerSheetId` VARCHAR(36) NOT NULL COMMENT '所属科答题卡唯一标识',  
  `personId` VARCHAR(36) NOT NULL COMMENT '扫描员唯一标识,源于外部系统',  
  `scanner` VARCHAR(36)  COMMENT '扫描员姓名',  
  `name` VARCHAR(64)  COMMENT '扫描批次名称',
  `fileName` VARCHAR(64)  COMMENT '扫描批次归档文件名称',
  `point` VARCHAR(16)  COMMENT '按考场扫描时-考点',  
  `room` VARCHAR(16)  COMMENT '按考场扫描时-考场号',
  `expected` SMALLINT(3) DEFAULT '-1' COMMENT '计划扫描人数,-1表示无计划扫描数',
  `actual` SMALLINT(3) COMMENT '实际扫描数',  
  `examNumberDoubts` SMALLINT(3) COMMENT '本批次中考号疑似错误卡数量', 
  `kgDoubts` SMALLINT(3) COMMENT '本批次中客观题疑似错误卡数量',
  `zgOptionalDoubts` SMALLINT(3) COMMENT '本批次中选做题疑似错误卡数量', 
  `submittingTime` TIMESTAMP DEFAULT NOW() COMMENT '开始提交时间',
  `examNumberDoubtsDone` TINYINT(1) DEFAULT '0' COMMENT '本批次中考号疑似错误是扫描时已经处理过,０－未处理；１－已处理;同一扫描批次中必须全部处理完成才算完成',  
  `submittedTime` TIMESTAMP  COMMENT '提交完成时间',
  `removed` TINYINT(1) DEFAULT(0) COMMENT '逻辑删除标志：0-未删除1-已经删除，所有的查询必须带上此条件',
  PRIMARY KEY (`id`),
  KEY `x_ps_BatchScan_batchId` (`batchId`),
  KEY `x_ps_BatchScan_examId` (`examId`),   
  KEY `x_ps_BatchScan_subjectId` (`subjectId`),  
  KEY `x_ps_BatchScan_answerSheetId` (`answerSheetId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－扫描批次';

DROP TABLE IF EXISTS `t_ps_SheetScan`;
CREATE TABLE `t_ps_SheetScan` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `sheetScanId` VARCHAR(36) NOT NULL COMMENT '扫描卡唯一标识',  
  `batchId` VARCHAR(36) NOT NULL COMMENT '扫描批次唯一标识',   
  `sheet` TINYINT(2)  COMMENT '扫描卡张码',
  `page` TINYINT(2)  COMMENT '扫描卡页码',
  `features` TEXT  COMMENT '答题卡信息特征,默认JSON',  
  `examNumberFeatures` TEXT  COMMENT '考号特征,默认JSON',
  `kgFeatures` TEXT  COMMENT '客观题信息特征,默认JSON', 
  `zgFeatures` TEXT  COMMENT '主观题信息特征,默认JSON', 
  `zgOptionalFeatures` TEXT  COMMENT '选做题信息特征,默认JSON',     
  `examNumberDoubt` TINYINT(1) DEFAULT '0' COMMENT '考号错误疑似:0－无疑似错误；1－有疑似错误', 
  `kgDoubt` TINYINT(1) DEFAULT '0' COMMENT '客观错误疑似:0－无疑似错误;1－有疑似错误',
  `zgOptionalDoubt` TINYINT(1) DEFAULT '0' COMMENT '选择题错误疑似:0－无疑似错误；1－有疑似错误',
  `examNumberDoubtDone` TINYINT(1) DEFAULT '0' COMMENT '考号疑似错误是扫描时已经处理过,0－未处理；1－已处理',
  `sheetKey` VARCHAR(36) COMMENT '扫描时生成的唯一标识，相同的sheetKey表示一个考生的答题卡',   
  PRIMARY KEY (`id`), 
  KEY `x_ps_SheetScan_sheetScanId` (`sheetScanId`),
  KEY `x_ps_SheetScan_batchId` (`batchId`),
  INDEX `x_ps_SheetScan_sheetKey` (`sheetKey`)  
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－扫描答题卡';

DROP TABLE IF EXISTS `t_ps_ScanStatistics`;
CREATE TABLE `t_ps_ScanStatistics` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `examId` VARCHAR(36) NOT NULL COMMENT '考试唯一标识',
  `subjectId` VARCHAR(36) NOT NULL COMMENT '科目唯一标识',
  `answerSheetId` VARCHAR(36) NOT NULL COMMENT '所属科答题卡唯一标识',  
  `statisticsTarget` VARCHAR(36) NOT NULL COMMENT '扫描统计目标唯一标识,Scanner-personId;School-t_ps_Examinee.schoolId;Clazz-t_ps_Examinee.clazzId;Point-t_ps_Examinee.point;Room-t_ps_Examinee.room',
  `targetType` VARCHAR(8)  COMMENT '扫描统计类型:Scanner-扫描员；School-学校；Clazz-班级；Point-考点；Room-考场',  
  `targetName` VARCHAR(32)  COMMENT '扫描统计类型:Scanner-扫描员；School-学校；Clazz-班级；Point-考点；Room-考场',  
  `expected` INT(8)  COMMENT '扫描完成量',
  `actual` SMALLINT(3) COMMENT '实际扫描数',  
  PRIMARY KEY (`id`), 
  KEY `x_ps_ScanStatistics_examId` (`examId`),   
  KEY `x_ps_ScanStatistics_subjectId` (`subjectId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－科目扫描统计';

DROP TABLE IF EXISTS `t_ps_ScanDoubtStatistics`;
CREATE TABLE `t_ps_ScanDoubtStatistics` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `examId` VARCHAR(36) NOT NULL COMMENT '考试唯一标识',
  `subjectId` VARCHAR(36) NOT NULL COMMENT '科目唯一标识', 
  `examNumberDoubts` SMALLINT(8) DEFAULT '0' COMMENT '科目考号疑似错误卡数量', 
  `kgDoubts` INT(8) DEFAULT '0' COMMENT '科目客观题疑似错误卡数量',
  `zgOptionalDoubts` INT(8) DEFAULT '0' COMMENT '科目选做题疑似错误卡数量',
  `examNumberDoubtsFinished` SMALLINT(8) DEFAULT '0' COMMENT '科目考号疑似错误卡处理完成数量', 
  `kgDoubtsFinished` INT(8) DEFAULT '0' COMMENT '科目客观题疑似错误卡处理完成数量',
  `zgOptionalDoubtsFinished` INT(8) DEFAULT '0' COMMENT '科目选做题疑似错误卡处理完成数量',   
  PRIMARY KEY (`id`), 
  KEY `x_ps_ScanStatistics_examId` (`examId`),   
  KEY `x_ps_ScanStatistics_subjectId` (`subjectId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－科目扫描怀疑及处理统计';

/***考生答题卡***/
DROP TABLE IF EXISTS `t_ps_ExamineeSheet`;
CREATE TABLE `t_ps_ExamineeSheet` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `sheetId` VARCHAR(36) NOT NULL COMMENT '答题卡唯一标识',  
  `answerSheetId` VARCHAR(36) NOT NULL COMMENT '所属科答题卡唯一标识',
  `examId` VARCHAR(36) NOT NULL COMMENT '考试唯一标识',
  `subjectId` VARCHAR(36) NOT NULL COMMENT '科目唯一标识',   
  `batchId` VARCHAR(32) COMMENT '扫描批次唯一标识', 
  `sheetKey` VARCHAR(36) COMMENT '扫描时生成的唯一标识，在考号异常示未处理完成时作为考生卡的唯一标识',   
  `catagory` VARCHAR(4)  COMMENT 'AB卡分类，有分类时值为Ａ或者Ｂ',  
  `examNumber` VARCHAR(32)  COMMENT '考号',
  `cryptCode` VARCHAR(32) NOT NULL COMMENT '密号，主观题评阅时使用的唯一标识',
  `scoredable` TINYINT(1) DEFAULT '1' COMMENT '是否出成绩单：0－不出成绩单，即在成绩单中直接记０分；1－出成绩单元格',
  `markable` TINYINT(1) DEFAULT '1' COMMENT '是否进行主观题评阅：0－不评；1－评阅',
  `score` FLOAT(3,2)  COMMENT '整卷得分', 
  `kgScore` FLOAT(3,2)  COMMENT '客观题得分',
  `zgScore` FLOAT(3,2)  COMMENT '主观题得分',    
  PRIMARY KEY (`id`), 
  KEY `x_ps_ExamineeSheet_sheetId` (`sheetId`),  
  KEY `x_ps_ExamineeSheet_examId` (`examId`),
  KEY `x_ps_ExamineeSheet_subjectId` (`subjectId`),  
  KEY `x_ps_ExamineeSheet_answerSheetId` (`answerSheetId`),
  KEY `x_ps_ExamineeSheet_batchId` (`batchId`),
  INDEX `x_ps_ExamineeSheet_sheetKey` (`sheetKey`),
  INDEX `x_ps_ExamineeSheet_cryptCode` (`cryptCode`),
  INDEX `x_ps_ExamineeSheet_examNumber` (`examNumber`) 
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－考生的答题卡';

DROP TABLE IF EXISTS `t_ps_ExamineeSheetPage`;
CREATE TABLE `t_ps_ExamineeSheetPage` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `sheetId` VARCHAR(36) NOT NULL COMMENT '答题卡唯一标识',
  `page` TINYINT(2) NOT NULL COMMENT '页码',  
  `sheet` TINYINT(2) NOT NULL COMMENT '张码',
  `imgUrl` VARCHAR(128)  COMMENT '纸张对应的扫瞄图片地址',
  `roate` SMALLINT(3)  COMMENT '旋转角度 -360--360',    
  PRIMARY KEY (`id`), 
  KEY `x_ps_ExamineeAnswerSheetPage_sheetId` (`sheetId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－科目答题卡纸张';

/***答题卡评题**/
DROP TABLE IF EXISTS `t_ps_SheetSlices`;
CREATE TABLE `t_ps_SheetSlices` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `sheetSlicesId` VARCHAR(36) NOT NULL COMMENT '答题卡切片唯一标识',
  `answerSheetId` VARCHAR(36) NOT NULL COMMENT '所属科答题卡唯一标识',
  `page` TINYINT(2) NOT NULL COMMENT '页码，来源于ps_AnswerSheetPage.page',  
  `sheet` TINYINT(2) NOT NULL COMMENT '张码，来源于ps_AnswerSheetPage.sheet',
  `x` SMALLINT(4) NOT NULL COMMENT '起点X轴坐标',  
  `y` SMALLINT(4) NOT NULL COMMENT '起点Y轴坐标', 
  `w` SMALLINT(4) NOT NULL COMMENT '切片宽',  
  `h` SMALLINT(4) NOT NULL COMMENT '切片高',
  `roate` SMALLINT(3) DEFAULT '0' COMMENT '旋转角度 -360--360，来源于ps_AnswerSheetPage.rote',
  `purpose` TINYINT(1) DEFAULT '1' COMMENT '切片的用途 1－主观题；2－考号；3－客观题', 
  `repeatable` TINYINT(1) DEFAULT '1' COMMENT '切片是否可以重复使用',  
  PRIMARY KEY (`id`), 
  KEY `x_ps_SheetSlices_sheetSlicesId` (`sheetSlicesId`),
  KEY `x_ps_SheetSlices_answerSheetId` (`answerSheetId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－科目答题卡切片';

DROP TABLE IF EXISTS `t_ps_MarkItem`;
CREATE TABLE `t_ps_MarkItem` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `markItemId` VARCHAR(36) NOT NULL COMMENT '评题唯一标识',
  `answerSheetId` VARCHAR(36) NOT NULL COMMENT '所属科答题卡唯一标识',
  `examId` VARCHAR(36) NOT NULL COMMENT '考试唯一标识',
  `subjectId` VARCHAR(36) NOT NULL COMMENT '科目唯一标识',   
  `name` VARCHAR(36) NOT NULL COMMENT '名称',  
  `seq` TINYINT(2) NOT NULL COMMENT '顺序号', 
  `purpose` TINYINT(1) DEFAULT '1' COMMENT '评题用途：1－主观题；2－考号；3－客观题；4－选做题分题',
  `requiredTimes` TINYINT(1) DEFAULT '1' COMMENT '评次',
  `status` TINYINT(1) DEFAULT '0' COMMENT '评卷进行状态，0-未开始;1-进行中;2-暂停;9-完成;',  
  PRIMARY KEY (`id`), 
  KEY `x_ps_MarkItem_markItemId` (`markItemId`),
  KEY `x_ps_MarkItem_answerSheetId` (`answerSheetId`),
  KEY `x_ps_MarkItem_examId` (`examId`),
  KEY `x_ps_MarkItem_subjectId` (`subjectId`)    
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－科目评题';

DROP TABLE IF EXISTS `t_ps_MarkItemOptionalGroup`;
CREATE TABLE `t_ps_MarkItemOptionalGroup` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,    
  `groupId` VARCHAR(36) NOT NULL COMMENT '选做题组唯一标识',  
  `name` VARCHAR(36) NOT NULL COMMENT '名称',
  `total` TINYINT(1) DEFAULT '1'  COMMENT '选做题总数',  
  `required` TINYINT(1) DEFAULT '1'  COMMENT '选做题必做数',
  `groups` TINYINT(1)  COMMENT '编组号',     
  PRIMARY KEY (`id`), 
  KEY `x_ps_MarkItemOptionalGroup_groupId` (`groupId`)   
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷-科目评题选做题分组';

DROP TABLE IF EXISTS `t_ps_MarkItemOptional`;
CREATE TABLE `t_ps_MarkItemOptional` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,    
  `markItemId` VARCHAR(36) NOT NULL COMMENT '评题唯一标识', 
  `groupId` VARCHAR(36) NOT NULL COMMENT '选做题组唯一标识',   
  `optional` VARCHAR(8)  COMMENT '选做题选项',     
  PRIMARY KEY (`id`), 
  KEY `x_ps_MarkItemOptional_markItemId` (`markItemId`),
  KEY `x_ps_MarkItemOptional_groupId` (`groupId`)  
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷-科目评题选做题选项';

DROP TABLE IF EXISTS `t_ps_MarkItemSlices`;
CREATE TABLE `t_ps_MarkItemSlices` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `markItemId` VARCHAR(36) NOT NULL COMMENT '评题唯一标识',
  `sheetSlicesId` VARCHAR(36) NOT NULL COMMENT '答题卡切片唯一标识', 
  `x` TINYINT(2) DEFAULT '0' COMMENT '评题面板的x轴',
  `y` TINYINT(2) DEFAULT '0' COMMENT '评题面板的y轴',
  PRIMARY KEY (`id`), 
  KEY `x_ps_MarkItemSlices_markItemId` (`markItemId`), 
  KEY `x_ps_MarkItemSlices_sheetSlicesId` (`sheetSlicesId`)  
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－组成科目评题的题卡切片';

DROP TABLE IF EXISTS `t_ps_MarkItemScore`;
CREATE TABLE `t_ps_MarkItemScore` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `markItemId` VARCHAR(36) NOT NULL COMMENT '评题唯一标识',
  `markItemScoreId` VARCHAR(36) NOT NULL COMMENT '评题唯一标识', 
  `parentScoreId` VARCHAR(36) COMMENT '上级评题唯一标识',    
  `name` VARCHAR(36) NOT NULL COMMENT '名称', 
  `levels` TINYINT(1) DEFAULT '1' COMMENT '层次,实际中最多两层，1层是评题的分数，2层是给分点',     
  `seq` TINYINT(2) NOT NULL COMMENT '顺序号', 
  `score` FLOAT(3,1) DEFAULT '0.0'  COMMENT '最大分值，不能超过ps_Item.score',
  `scoreLimite` VARCHAR(1024)  COMMENT '给分规则',
  `scoreLinear` TINYINT(1) DEFAULT '1' COMMENT '是否线性给分：1－线性；0－非线性',
  `error` FLOAT(3,1) DEFAULT '-1.0'  COMMENT 't_ps_MarkItem.requiredTimes>1时的得分允许的误差',
  `scope` TINYINT(1) DEFAULT '1'  COMMENT '适用范围，1-通用；0-个人专用，为0时，person_id不能为空',
  `personId` VARCHAR(36) COMMENT '评卷员自定义给分规则',    
  PRIMARY KEY (`id`), 
  KEY `x_ps_ItemPoint_markItemId` (`markItemId`),
  KEY `x_ps_ItemPoint_markItemScoreId` (`markItemScoreId`)     
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－科目评题给分规则';

DROP TABLE IF EXISTS `t_ps_MarkItemToSheetItem`;
CREATE TABLE `t_ps_MarkItemToSheetItem` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `markItemId` VARCHAR(36) NOT NULL COMMENT '评题唯一标识',
  `answerSheetItemId` VARCHAR(36) NOT NULL COMMENT '答题卡题目唯一标识',
  `markItemScoreId` VARCHAR(36) NOT NULL COMMENT '评题给分规则唯一标识',  
  PRIMARY KEY (`id`), 
  KEY `x_ps_MarkItemToSheetItem_markItemId` (`markItemId`),
  KEY `x_ps_MarkItemToSheetItem_answerSheetItemId` (`answerSheetItemId`),
  KEY `x_ps_MarkItemToSheetItem_markItemScoreId` (`markItemScoreId`)  
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷-科目评题答题卡评题的关系';

/***考生评题**/
DROP TABLE IF EXISTS `t_ps_ExamineeItem`;
CREATE TABLE `t_ps_ExamineeItem` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `markItemId` VARCHAR(36) NOT NULL COMMENT '评题唯一标识',
  `subjectId` VARCHAR(36) NOT NULL COMMENT '科目唯一标识',  
  `examineeItemId` VARCHAR(36) NOT NULL COMMENT '考生评题唯一标识',
  `cryptCode` VARCHAR(32) NOT NULL COMMENT '密号',
  `purpose` TINYINT(1) DEFAULT '1' COMMENT '评题用途：1－主观题；2－考号；3－客观题；4－选做题分题',                                    
  PRIMARY KEY (`id`), 
  KEY `x_ps_ExamineeItem_markItemId` (`markItemId`),
  KEY `x_ps_ExamineeItem_subjectId` (`subjectId`),  
  KEY `x_ps_ExamineeItem_examineeItemId` (`examineeItemId`),
  KEY `x_ps_ExamineeItem_cryptCode` (`cryptCode`)    
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－科目考生评题';

DROP TABLE IF EXISTS `t_ps_ExamineeItemSlices`;
CREATE TABLE `t_ps_ExamineeItemSlices` (.
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `markItemId` VARCHAR(36) NOT NULL COMMENT '评题唯一标识',
  `sheetSlicesId` VARCHAR(36) NOT NULL COMMENT '答题卡切片唯一标识', 
  `examineeItemId` VARCHAR(36) NOT NULL COMMENT '考生评题唯一标识',  
  `imgUrl` VARCHAR(128)  COMMENT '纸张对应的扫瞄图片地址',                                    
  PRIMARY KEY (`id`), 
  KEY `x_ps_ExamineeItemSlices_examineeItemId` (`examineeItemId`) ,   
  KEY `x_ps_ExamineeItemSlices_markItemId` (`markItemId`) , 
  KEY `x_ps_ExamineeItemSlices_sheetSlicesId` (`sheetSlicesId`)  
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－考生评题切片';

DROP TABLE IF EXISTS `t_ps_ExamineeItemScore`;
CREATE TABLE `t_ps_ExamineeItemScore` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `markItemId` VARCHAR(36) NOT NULL COMMENT '评题唯一标识',
  `markItemScoreId` VARCHAR(36) NOT NULL COMMENT '评题得分唯一标识', 
  `examineeItemId` VARCHAR(36) COMMENT '考生评题唯一标识',    
  `score` FLOAT(3,1) DEFAULT '-1.0'  COMMENT '得分',
  `scoreNames` VARCHAR(1024)  COMMENT '给分明细名称',   
  `scores` VARCHAR(1024)  COMMENT '给分明细', 
  `valid` TINYINT(1) DEFAULT '1' COMMENT '是否有效:1-有效；2-无效',                                   
  PRIMARY KEY (`id`), 
  KEY `x_ps_ExamineeItemScore_markItemId` (`markItemId`),
  KEY `x_ps_ExamineeItemScore_examineeItemId` (`examineeItemId`),    
  KEY `x_ps_ExamineeItemScore_markItemScoreId` (`markItemScoreId`)     
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－考生评题得分';

/***阅卷人员***/
DROP TABLE IF EXISTS `t_ps_MarkTeam`;
CREATE TABLE `t_ps_MarkTeam` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `teamId` VARCHAR(36) NOT NULL COMMENT '考试唯一标识',
  `parentTeamId` VARCHAR(36) COMMENT '考试唯一标识',  
  `examId` VARCHAR(36) NOT NULL COMMENT '考试唯一标识',
  `subjectId` VARCHAR(36) NOT NULL COMMENT '科目唯一标识', 
  `orgId` VARCHAR(36) COMMENT '校考，统考，联考使用评卷老师的学校唯一标识',   
  `itemId` VARCHAR(36) NOT NULL COMMENT '评题唯一标识',   
  `name` VARCHAR(128) NOT NULL COMMENT '组名' ,
  `planned` INT(8) DEFAULT '-1' COMMENT '组计划评卷量,-1表示无计划量' ,
  `finished` INT(8) NOT NULL COMMENT '组实际完成评卷量' ,  
  PRIMARY KEY (`id`), 
  KEY `x_ps_MarkTeam_examId` (`examId`),
  KEY `x_ps_MarkTeam_subjectId` (`subjectId`) ,
  KEY `x_ps_MarkTeam_teamId` (`teamId`) ,
  KEY `x_ps_MarkTeam_parentTeamId` (`parentTeamId`) ,
  KEY `x_ps_MarkTeam_itemId` (`itemId`)      
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－评卷组';

DROP TABLE IF EXISTS `t_ps_MarkTeamMember`;
CREATE TABLE `t_ps_MarkTeamMember` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `teamId` VARCHAR(36) NOT NULL COMMENT '考试唯一标识', 
  `markItemId` VARCHAR(36) NOT NULL COMMENT '评题唯一标识',   
  `markerId` VARCHAR(36) NOT NULL COMMENT '评卷员唯一标识' ,
  `role` VARCHAR(36) NOT NULL COMMENT '组员角色：Leader－组长；Worker-组员' ,  
  PRIMARY KEY (`id`), 
  KEY `x_ps_MarkTeamMember_teamId` (`teamId`) ,
  KEY `x_ps_MarkTeamMember_markItemId` (`markItemId`) ,
  KEY `x_ps_MarkTeamMember_markerId` (`markerId`)         
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－评卷组成员';

DROP TABLE IF EXISTS `t_ps_MarkTeamAuthor`;
CREATE TABLE `t_ps_MarkTeamAuthor` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `teamId` VARCHAR(36) NOT NULL COMMENT '评卷员机构唯一标识',
  `markItemId` VARCHAR(36) NOT NULL COMMENT '评题唯一标识', 
  `authorCode` VARCHAR(128) NOT NULL COMMENT '授权码' ,
  `role` VARCHAR(36) NOT NULL COMMENT '授权码对应组员角色：Leader-组长;Worker-组员' ,   
  `planned` INT(8) DEFAULT '-1' COMMENT '计划评卷量,-1表示无计划评卷量' ,
  `finished` INT(8) DEFAULT '0' COMMENT '实际完成评卷量' ,  
  `status` TINYINT(1) DEFAULT '1' COMMENT '可用状态,1-占用；0-可用;9-禁用' ,
  `personId` VARCHAR(36)  COMMENT '使用者，分配时status必须大于0',  
  PRIMARY KEY (`id`),
  KEY `x_ps_MarkTeamAuthor_teamId` (`teamId`),
  KEY `x_ps_MarkTeamAuthor_markItemId` (`markItemId`)  
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷-评卷员来源机构授权,统考、联考时无法直接分配到评卷员时用';


DROP TABLE IF EXISTS `t_ps_Marker`;
CREATE TABLE `t_ps_Marker` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `markerId` VARCHAR(36) NOT NULL COMMENT '评卷员唯一标识',
  `examId` VARCHAR(36) NOT NULL COMMENT '考试唯一标识',
  `subjectId` VARCHAR(36) NOT NULL COMMENT '科目唯一标识', 
  `markItemId` VARCHAR(36) NOT NULL COMMENT '评题唯一标识',
  `personId` VARCHAR(36) NOT NULL COMMENT '人员唯一标识,源于外部系统', 
  `orgId` VARCHAR(36) COMMENT '校考，统考，联考使用评卷老师的学校唯一标识',   
  `name` VARCHAR(128) NOT NULL COMMENT '姓名' ,
  `role` VARCHAR(16) NOT NULL COMMENT '评卷角色：Normal-普通评卷员；Arbiter-多评仲裁人；Broker-问题卷处理人;Sweepers-尾卷处理人；Terminator-抽查人 ' ,  
  `planned` INT(8) DEFAULT '-1' COMMENT '计划评卷量,-1表示无计划评卷量' ,
  `finished` INT(8) DEFAULT '0' COMMENT '实际完成评卷量' ,  
  `status` TINYINT(1) DEFAULT '1' COMMENT '工作状态：0－待命；1－正评；2－试评；3－暂停',
  `online` TINYINT(1) DEFAULT '0' COMMENT '在线状态：0－离线；1－在线',  
  PRIMARY KEY (`id`), 
  KEY `x_ps_Marker_examId` (`examId`),
  KEY `x_ps_Marker_subjectId` (`subjectId`) ,
  KEY `x_ps_Marker_markItemId` (`markItemId`) ,
  KEY `x_ps_Marker_markerId` (`markerId`),
  KEY `x_ps_Marker_personId` (`personId`),
  INDEX `x_ps_Marker_role` (`role`)  
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－评卷人';

/***评卷过程***/
DROP TABLE IF EXISTS `t_ps_ExamNumberMark`;
CREATE TABLE `t_ps_ExamNumberMark` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `markId` VARCHAR(36) NOT NULL COMMENT '考号题唯一标识',  
  `examId` VARCHAR(36) NOT NULL COMMENT '考试唯一标识',
  `subjectId` VARCHAR(36) NOT NULL COMMENT '科目唯一标识', 
  `batchId` VARCHAR(16) COMMENT '扫描批次唯一标识',  
  `markItemId` VARCHAR(36) NOT NULL COMMENT '评题唯一标，',
  `examineeItemId` VARCHAR(36) NOT NULL COMMENT '考生评题唯一标识',   
  `schoolId` VARCHAR(36) COMMENT '考生所在学校唯一标识',    
  `clazzId` VARCHAR(36) COMMENT '考生所在班级唯一标识',   
  `point` VARCHAR(16) COMMENT '考点号',   
  `room` VARCHAR(16)  COMMENT '考场号',
  `origin` VARCHAR(36) COMMENT '识别原始结果',
  `originUpdator` VARCHAR(36) COMMENT '识别原始值修改人唯一标识，扫描时若有更新时记录',   
  `fetchSeq` INT(8)  COMMENT '提取顺序号，每个考试科目都重新排序',  
  `required` TINYINT(2)  COMMENT '必须完成的评阅次数',
  `times` TINYINT(2) COMMENT '已完成的评阅次数' ,
  `fetched` TINYINT(1) COMMENT '正评提取状态，0－未取；1－已取',  
  `fetchsign` VARCHAR(16) COMMENT '正评提取签名',
  `examNumber` VARCHAR(36) COMMENT '考号',
  `existsRegister` TINYINT(1) COMMENT '是否报考1-是，0-否则',  
  `cryptCode` VARCHAR(32) NOT NULL COMMENT '密号',
  `doubt` TINYINT(1) DEFAULT '9' COMMENT '机评时怀疑卷分类：1－未报名；2－未填写;9－正常;', 
  `doubtDone` TINYINT(1)  COMMENT '机评时怀疑卷处理标志：1－已经处理；0－未处理',        
  PRIMARY KEY (`id`),
  KEY `x_ps_ExamNumberMark_markId` (`markId`),  
  KEY `x_ps_ExamNumberMark_examId` (`examId`),
  KEY `x_ps_ExamNumberMark_subjectId` (`subjectId`) ,
  KEY `x_ps_ExamNumberMark_batchId` (`batchId`) ,
  INDEX `x_ps_ExamNumberMark_schoolId` (`schoolId`) ,
  INDEX `x_ps_ExamNumberMark_clazzId` (`clazzId`) ,  
  KEY `x_ps_ExamNumberMark_markItemId` (`markItemId`),
  INDEX `x_ps_ScoringMark_cryptCode` (`cryptCode`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－考号评题';

DROP TABLE IF EXISTS `t_ps_ExamNumberMarkHandler`;
CREATE TABLE `t_ps_ExamNumberMarkHandler` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `handlerId` VARCHAR(36) NOT NULL COMMENT '考号处理唯一标识',  
  `markId` VARCHAR(36) NOT NULL COMMENT '考号题唯一标识',    
  `markerId` VARCHAR(36) NOT NULL COMMENT '评卷员唯一标识，markType=Mechine时值为：Mechine',
  `markType` TINYINT(1) NOT NULL  COMMENT '评卷类类型标识：1－识别(Mechine)；2－后台手工处理(Hand)；',  
  `fetchTimes` TINYINT(2)  COMMENT '取出时的评次',  
  `fetchTime` TIMESTAMP COMMENT '提取时间',
  `submitTime` TIMESTAMP COMMENT '提交时间',
  `submitTimes` TINYINT(2)  COMMENT '提交时的评次', 
  `examNumber` VARCHAR(36) COMMENT '考号',
  `existsRegister` TINYINT(1) COMMENT '是否报考:1-是，0-否则', 
  `seq` INT(8) COMMENT '评卷人的评卷顺序号',  
  PRIMARY KEY (`id`), 
  KEY `x_ps_ExamNumberMarkHandler_handlerId` (`handlerId`),
  KEY `x_ps_ExamNumberMarkHandler_markId` (`markId`),
  KEY `x_ps_ExamNumberMarkHandler_markerId` (`markerId`),
  INDEX `x_ps_ExamNumberMarkHandler_markType` (`markType`)  
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－考号评题处理';

DROP TABLE IF EXISTS `t_ps_ExamNumberRepeats`;
CREATE TABLE `t_ps_ExamNumberRepeats` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,  
  `examId` VARCHAR(36) NOT NULL COMMENT '考试唯一标识',
  `subjectId` VARCHAR(36) NOT NULL COMMENT '科目唯一标识',
  `examNumber` VARCHAR(36) COMMENT '重复的考号',
  `cryptCodes` TEXT COMMENT '重复的考号答题卡密号',        
  PRIMARY KEY (`id`), 
  KEY `x_ps_ExamNumberRepeats_examId` (`examId`),
  KEY `x_ps_ExamNumberRepeats_subjectId` (`subjectId`)  
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－考号重复记录,每产生一个ExamNumberMarkHandler记录，需要计算是否有考号重复;处理完成时删除数据';

DROP TABLE IF EXISTS `t_ps_KgMark`;
CREATE TABLE `t_ps_KgMark` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `markId` VARCHAR(36) NOT NULL COMMENT '考号题唯一标识',  
  `examId` VARCHAR(36) NOT NULL COMMENT '考试唯一标识',
  `subjectId` VARCHAR(36) NOT NULL COMMENT '科目唯一标识', 
  `batchId` VARCHAR(16) COMMENT '扫描批次唯一标识',  
  `markItemId` VARCHAR(36) NOT NULL COMMENT '评题唯一标识',
  `examineeItemId` VARCHAR(36) NOT NULL COMMENT '考生评题唯一标识',        
  `required` TINYINT(2)  COMMENT '必须完成的评阅次数',
  `times` TINYINT(2) COMMENT '已完成的评阅次数' ,
  `fetched` TINYINT(2) COMMENT '提取状态，0－未取；1－已取',  
  `fetchsign` VARCHAR(16) COMMENT '正评提取签名',
  `cryptCode` VARCHAR(32) NOT NULL COMMENT '密号',
  `score` FLOAT(3,1) DEFAULT '-1.0'  COMMENT '得分',
  `scores` VARCHAR(512) COMMENT '每题得分，以;分隔',
  `optional` TEXT COMMENT '每题选项，以;分隔，无选项以＃代替',
  `doubt` TINYINT(1) DEFAULT '9' COMMENT '机评时怀疑卷分类：1－无怀疑；0－有怀疑', 
  `doubtDone` TINYINT(1)  COMMENT '机评时怀疑卷处理标志：1－已经处理；0－未处理',     
  PRIMARY KEY (`id`),
  KEY `x_ps_KgMark_markId` (`markId`),  
  KEY `x_ps_KgMark_examId` (`examId`),
  KEY `x_ps_KgMark_subjectId` (`subjectId`) ,
  KEY `x_ps_KgMark_markItemId` (`markItemId`), 
  INDEX `x_ps_KgMark_cryptCode` (`cryptCode`) 
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－客观评题';

DROP TABLE IF EXISTS `t_ps_KgMarkHandler`;
CREATE TABLE `t_ps_KgMarkHandler` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `handlerId` VARCHAR(36) NOT NULL COMMENT '考号处理唯一标识',  
  `markId` VARCHAR(36) NOT NULL COMMENT '考号题唯一标识',    
  `markerId` VARCHAR(36) NOT NULL COMMENT '评卷员唯一标识，markType=Mechine时值为：Mechine',
  `markType` TINYINT(1) NOT NULL  COMMENT '评卷类类型标识：1－识别(Mechine)；2－后台手工处理(Hand)；',   
  `fetchTimes` TINYINT(2)  COMMENT '取出时的评次',  
  `fetchTime` TIMESTAMP COMMENT '提取时间',
  `submitTime` TIMESTAMP COMMENT '提交时间',
  `submitTimes` TINYINT(2)  COMMENT '提交时的评次',   
  `optional` TEXT COMMENT '客观题，默认JSON格式{"1":"A","2":"#","3":"ABC"},无选项以＃代替',
  `seq` INT(8) COMMENT '评卷人的评卷顺序号',   
  PRIMARY KEY (`id`), 
  KEY `x_ps_KgMarkHandler_handlerId` (`handlerId`),
  KEY `x_ps_KgMarkHandler_markId` (`markId`),
  KEY `x_ps_KgMarkHandler_markerId` (`markerId`),
  INDEX `x_ps_KgMarkHandler_markType` (`markType`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－客观评题处理';

DROP TABLE IF EXISTS `t_ps_ZgOptionalMark`;
CREATE TABLE `t_ps_ZgOptionalMark` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `markId` VARCHAR(36) NOT NULL COMMENT '选做题唯一标识',  
  `examId` VARCHAR(36) NOT NULL COMMENT '考试唯一标识',
  `subjectId` VARCHAR(36) NOT NULL COMMENT '科目唯一标识', 
  `batchId` VARCHAR(16) COMMENT '扫描批次唯一标识',  
  `markItemId` VARCHAR(36) NOT NULL COMMENT '评题唯一标识',
  `examineeItemId` VARCHAR(36) NOT NULL COMMENT '考生评题唯一标识',        
  `required` TINYINT(2)  COMMENT '必须完成的评阅次数',
  `times` TINYINT(2) COMMENT '已完成的评阅次数' ,
  `fetched` TINYINT(1) COMMENT '正评提取状态，0－未取；1－已取',  
  `fetchsign` VARCHAR(16) COMMENT '正评提取签名',
  `cryptCode` VARCHAR(32) NOT NULL COMMENT '密号',
  `optional` VARCHAR(32) COMMENT '选做题选项，多组时以;，无选项以＃代替', 
  `doubt` TINYINT(1) DEFAULT '9' COMMENT '机评时怀疑卷分类：1－无怀疑；0－有怀疑', 
  `doubtDone` TINYINT(1)  COMMENT '机评时怀疑卷处理标志：1－已经处理；0－未处理',      
  PRIMARY KEY (`id`),
  KEY `x_ps_ZgOptionalMark_markId` (`markId`),  
  KEY `x_ps_ZgOptionalMark_examId` (`examId`),
  KEY `x_ps_ZgOptionalMark_subjectId` (`subjectId`) ,
  KEY `x_ps_ZgOptionalMark_markItemId` (`markItemId`), 
  INDEX `x_ps_ZgOptionalMark_cryptCode` (`cryptCode`) 
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－客观题选做分题评题';

DROP TABLE IF EXISTS `t_ps_ZgOptionalMarkHandler`;
CREATE TABLE `t_ps_ZgOptionalMarkHandler` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `handlerId` VARCHAR(36) NOT NULL COMMENT '考号处理唯一标识',  
  `markId` VARCHAR(36) NOT NULL COMMENT '考号题唯一标识',    
  `markerId` VARCHAR(36) NOT NULL COMMENT '评卷员唯一标识，markType=Mechine时值为：Mechine',
  `markType` TINYINT(1) NOT NULL  COMMENT '评卷类类型标识：1－识别(Mechine)；2－后台手工处理(Hand)；', 
  `fetchTimes` TINYINT(2)  COMMENT '取出时的评次',  
  `fetchTime` TIMESTAMP COMMENT '提取时间',
  `submitTime` TIMESTAMP COMMENT '提交时间',
  `submitTimes` TINYINT(2)  COMMENT '提交时的评次',   
  `optional` VARCHAR(32) COMMENT '选做题选项，多组时以;分隔，无选项以＃代替',
  `seq` INT(8) COMMENT '评卷人的评卷顺序号',   
  PRIMARY KEY (`id`), 
  KEY `x_ps_ZgOptionalMarkHandler_handlerId` (`handlerId`),
  KEY `x_ps_ZgOptionalMarkHandler_markId` (`markId`),
  KEY `x_ps_ZgOptionalMarkHandler_markerId` (`markerId`),
  INDEX `x_ps_ZgOptionalMarkHandler_markType` (`markType`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－客观题选做分题评题处理';

DROP TABLE IF EXISTS `t_ps_ScoringMark`;
CREATE TABLE `t_ps_ScoringMark` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `markId` VARCHAR(36) NOT NULL COMMENT '评分题唯一标识',  
  `examId` VARCHAR(36) NOT NULL COMMENT '考试唯一标识',
  `subjectId` VARCHAR(36) NOT NULL COMMENT '科目唯一标识', 
  `batchId` VARCHAR(16) COMMENT '扫描批次唯一标识',  
  `markItemId` VARCHAR(36) NOT NULL COMMENT '评题唯一标识',
  `examineeItemId` VARCHAR(36) NOT NULL COMMENT '考生评题唯一标识',    
  `schoolId` VARCHAR(36) COMMENT '考生所在学校唯一标识',    
  `clazzId` VARCHAR(36) COMMENT '考生所在班级唯一标识', 
  `point` VARCHAR(16) COMMENT '考号',   
  `room` VARCHAR(16)  COMMENT '考场号',   
  `purpose` VARCHAR(16) NOT NULL DEFAULT 'Formal' COMMENT '评题用途：Formal-正评；Learnning-试评;Trainning-培训；Monitor-质控',  
  `cryptCode` VARCHAR(32) NOT NULL COMMENT '密号',
  `groupNo` INT(4) COMMENT '组号，purpose=Trainning时用',   
  `fetchSeq` INT(8)  COMMENT '提取顺序号，每个考试科目都重新排序',  
  `required` TINYINT(2)  COMMENT '必须完成的评阅次数',
  `times` TINYINT(2) COMMENT '已完成的评阅次数' ,
  `fetched` TINYINT(1) COMMENT '正评提取状态，0－未取；1－已取',  
  `fetchsign` VARCHAR(16) COMMENT '正评提取签名',
  `unabled` TINYINT(1) DEFAULT '1' COMMENT '问题卷标记：0－有问题；1－正常',
  `formalMarkerIds` TEXT  COMMENT '正评已分配评卷员,正评提交时更新，多人用;分隔', 
  `score` FLOAT(3,1) DEFAULT '-1.0'  COMMENT '得分',  
  PRIMARY KEY (`id`), 
  KEY `x_ps_ScoringMark_markId` (`markId`),  
  KEY `x_ps_ScoringMark_examId` (`examId`),
  KEY `x_ps_ScoringMark_subjectId` (`subjectId`) ,
  KEY `x_ps_ScoringMark_markItemId` (`markItemId`), 
  INDEX `x_ps_ScoringMark_purpose` (`purpose`),
  INDEX `x_ps_ScoringMark_cryptCode` (`cryptCode`) 
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－可评分的评题';

DROP TABLE IF EXISTS `t_ps_ScoringMarkHandler`;
CREATE TABLE `t_ps_ScoringMarkHandler` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `handlerId` VARCHAR(36) NOT NULL COMMENT '评分处理唯一标识',  
  `markId` VARCHAR(36) NOT NULL COMMENT '评分题唯一标识',
  `markerId` VARCHAR(36) NOT NULL COMMENT '评卷唯一标识',
  `markType` VARCHAR(16) NOT NULL DEFAULT 'Formal' COMMENT '评卷类类型标识：Formal－正评；ReFormal－回评；Learnning－试评；Self－自评；ForceFormal－发回重评；Monitor－质控；Arbiter－仲裁/抽查给分',  
  `fetchTimes` TINYINT(2)  COMMENT '正评取出时的评次',  
  `fetchTime` TIMESTAMP COMMENT '提取时间',
  `valid` TINYINT(1)  COMMENT '评分是否有效',  
  `submitTime` TIMESTAMP COMMENT '提交时间',
  `submitTimes` TINYINT(2)  COMMENT '正评提交时的评次',
  `score` FLOAT(3,1) DEFAULT '-1.0'  COMMENT '得分',    
  `scores` VARCHAR(128) COMMENT '得分；多个给分点以;分隔',
  `unabled` TINYINT(1) DEFAULT '1' COMMENT '问题卷标记：0－有问题；1－正常;标记为问题卷时scores为Null', 
  `unabledCatagory` TINYINT(1) DEFAULT '1' COMMENT '问题卷分类：1－看不清楚；2－答错位置;3－图片错位;4－图片错科',  
  `seq` INT(8) COMMENT '评卷人的评卷顺序号',   
  PRIMARY KEY (`id`), 
  KEY `x_ps_ScoringMarkHandler_handlerId` (`handlerId`),
  KEY `x_ps_ScoringMarkHandler_markId` (`markId`),
  KEY `x_ps_ScoringMarkHandler_markerId` (`markerId`),
  INDEX `x_ps_ScoringMarkHandler_markType` (`markType`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－主观题评题处理';

DROP TABLE IF EXISTS `t_ps_ScoringMarkCatagory`;
CREATE TABLE `t_ps_ScoringMarkCatagory` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `handlerId` VARCHAR(36) NOT NULL COMMENT '评分处理唯一标识',  
  `markId` VARCHAR(36) NOT NULL COMMENT '评分题唯一标识',    
  `catagory` TINYINT(1) COMMENT '评题分类:1-优秀题(Excellent)，2-样题(Sample)，3-典型题(Classic)',                                 
  PRIMARY KEY (`id`), 
  KEY `x_ps_ScoringMarkText_handlerId` (`handlerId`),    
  KEY `x_ps_ScoringMarkText_markId` (`markId`)     
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－主观题评题分类，如优秀题，样题，典型题等';

DROP TABLE IF EXISTS `t_ps_ScoringMarkText`;
CREATE TABLE `t_ps_ScoringMarkText` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `handlerId` VARCHAR(36) NOT NULL COMMENT '评分处理唯一标识',  
  `markId` VARCHAR(36) NOT NULL COMMENT '评分题唯一标识',    
  `comments` TEXT COMMENT '评语',
  `x` SMALLINT(4) NOT NULL COMMENT '评语框起点X轴坐标',  
  `y` SMALLINT(4) NOT NULL COMMENT '评语框起点Y轴坐标', 
  `w` SMALLINT(4) NOT NULL COMMENT '评语框宽',  
  `h` SMALLINT(4) NOT NULL COMMENT '评语框高',                                  
  PRIMARY KEY (`id`), 
  KEY `x_ps_ScoringMarkText_handlerId` (`handlerId`),    
  KEY `x_ps_ScoringMarkText_markId` (`markId`)     
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－主观题评题评语';

DROP TABLE IF EXISTS `t_ps_ScoringMarkSymbol`;
CREATE TABLE `t_ps_ScoringMarkSymbol` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `handlerId` VARCHAR(36) NOT NULL COMMENT '评分处理唯一标识',  
  `markId` VARCHAR(36) NOT NULL COMMENT '评分题唯一标识',   
  `pathJson` TEXT   COMMENT 'Json串标记路径',
  `symbol` VARCHAR(16)  COMMENT '标志：Tick－勾；X－叉；HalfTick－半勾；Stress－着重线；Circle－圆圈',  
  `x` SMALLINT(4) NOT NULL COMMENT '标志框起点X轴坐标',  
  `y` SMALLINT(4) NOT NULL COMMENT '标志框起点Y轴坐标', 
  `w` SMALLINT(4) NOT NULL COMMENT '标志框宽',  
  `h` SMALLINT(4) NOT NULL COMMENT '标志框高',                                   
  PRIMARY KEY (`id`), 
  KEY `x_ps_ScoringMarkSymbol_handlerId` (`handlerId`),
  KEY `x_ps_ScoringMarkSymbol_markId` (`markId`)     
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－主观题评题标记';

/***阅卷统计***/
DROP TABLE IF EXISTS `t_ps_ItemStatistics`;
CREATE TABLE `t_ps_ItemStatistics` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `subjectId` VARCHAR(36) NOT NULL COMMENT '科目唯一标识',
  `examId` VARCHAR(36) NOT NULL COMMENT '考试唯一标识',     
  `markItemId` VARCHAR(36) NOT NULL COMMENT '评题唯一标识',
  `itemName` VARCHAR(36) NOT NULL COMMENT '评题名称',   
  `statsMethod` TINYINT(2) NOT NULL COMMENT '统计指标方法，定义在dict表中',  
  `statsMethodName` TINYINT(2) NOT NULL COMMENT '统计指标方法名称，如完成数量，平均分，平均速度,多评产生量，多评处理量等',   
  `statsValue` FLOAT(3,2)  COMMENT '统计指标值' , 
  `statsTime` TIMESTAMP  COMMENT '统计时间' ,   
  PRIMARY KEY (`id`),
  KEY `x_ps_MarkerStatistics_subjectId` (`subjectId`),
  KEY `x_ps_MarkerStatistics_examId` (`examId`),  
  KEY `x_ps_MarkerStatistics_markItemId` (`markItemId`) 
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－评题统计,每统计一次新增一条记录';

DROP TABLE IF EXISTS `t_ps_MarkerStatistics`;
CREATE TABLE `t_ps_MarkerStatistics` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `subjectId` VARCHAR(36) NOT NULL COMMENT '科目唯一标识',
  `examId` VARCHAR(36) NOT NULL COMMENT '考试唯一标识',     
  `markItemId` VARCHAR(36) NOT NULL COMMENT '评题唯一标识',
  `itemName` VARCHAR(36) NOT NULL COMMENT '评题名称',  
  `statsTartget` VARCHAR(36) NOT NULL COMMENT '统计目标,Team;Marker', 
  `statsTartgetId` VARCHAR(36) NOT NULL COMMENT '统计目标唯一标识',   
  `statsMethod` TINYINT(2) NOT NULL COMMENT '统计指标方法，定义在dict表中',  
  `statsMethodName` TINYINT(2) NOT NULL COMMENT '统计指标方法名称，如完成数量，平均分，平均速度,多评产生量等',   
  `statsValue` FLOAT(3,2)  COMMENT '统计指标值' , 
  `statsTime` TIMESTAMP  COMMENT '统计时间' ,   
  PRIMARY KEY (`id`),
  KEY `x_ps_MarkerStatistics_subjectId` (`subjectId`),
  KEY `x_ps_MarkerStatistics_examId` (`examId`),  
  KEY `x_ps_MarkerStatistics_markItemId` (`markItemId`),
  INDEX `x_ps_MarkerStatistics_sstatsTartget` (`statsTartget`),    
  INDEX `x_ps_MarkerStatistics_statsTartgetId` (`statsTartgetId`)  
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－评员卷统计,每统计一次新增一条记录';

DROP TABLE IF EXISTS `t_ps_SubjectMarkStatistics`;
CREATE TABLE `t_ps_SubjectMarkStatistics` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `subjectId` VARCHAR(36) NOT NULL COMMENT '科目唯一标识',
  `examId` VARCHAR(36) NOT NULL COMMENT '考试唯一标识',    
  `statsMethod` TINYINT(2) NOT NULL COMMENT '统计指标方法，定义在dict表中', 
  `statsMethodName` TINYINT(2) NOT NULL COMMENT '统计指标方法名称，如待评量，完成数量，平均分等',    
  `statsValue` FLOAT(3,2)  COMMENT '统计指标值' ,
  `statsTime` TIMESTAMP  COMMENT '统计时间' ,     
  PRIMARY KEY (`id`), 
  KEY `x_ps_SubjectMarkStatistics_subjectId` (`subjectId`),
  KEY `x_ps_SubjectMarkStatistics_examId` (`examId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－科目评卷统计';

DROP TABLE IF EXISTS `t_ps_SubjectScoreStatistics`;
CREATE TABLE `t_ps_SubjectScoreStatistics` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `subjectId` VARCHAR(36) NOT NULL COMMENT '科目唯一标识',
  `examId` VARCHAR(36) NOT NULL COMMENT '考试唯一标识',    
  `statsMethod` TINYINT(2) NOT NULL COMMENT '统计指标方法，定义在dict表中', 
  `statsMethodName` TINYINT(2) NOT NULL COMMENT '统计指标方法名称，如客观题高分主题题低分，客观题空，客观题零分，主观题零分',    
  `examNumber` VARCHAR(32) NOT NULL COMMENT '考号',
  `kgs` TEXT  COMMENT '客观题选项及得分',
  `zgs` TEXT  COMMENT '主观题及得分', 
  `sheets` TEXT  COMMENT '答题卡图片URL',   
  PRIMARY KEY (`id`), 
  KEY `x_ps_SubjectScoreStatistics_subjectId` (`subjectId`),
  KEY `x_ps_SubjectScoreStatistics_examId` (`examId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－科目得分怀疑异常统计';

DROP TABLE IF EXISTS `t_ps_ExamFinished`;
CREATE TABLE `t_ps_ExamFinished` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `examId` VARCHAR(36) NOT NULL COMMENT '考试唯一标识',
  `creatorOrgId` VARCHAR(36) NOT NULL COMMENT '考试创建机构标识',  
  `creatorOrgName` VARCHAR(64)  COMMENT '考试创建机构标识',
  `name` VARCHAR(128)  COMMENT '考试名称',
  `typeName` VARCHAR(128)  COMMENT '考试类型名称',  
  `starts` DATETIME  COMMENT '考试开始时间',  
  `ends` DATETIME  COMMENT '阅卷完成时间',
  `adminIds` TEXT COMMENT '考试管理员唯一标识；多人时以全角；分隔',  
  `adminNames` TEXT COMMENT '考试管理员；多人时以全角；分隔',
  `testedOrgIds` TEXT COMMENT '考试参与机构唯一标识，多机构时以全角；分隔',    
  `testedOrgNames` TEXT COMMENT '考试参与机构，校考是班级名称；联考统考是学校名称；多机构时以全角；分隔',  
  `registerExaminees` INT(8) NOT NULL COMMENT '报名考生总数',
  `absentExaminees` INT(8) NOT NULL COMMENT '缺考总数', 
  PRIMARY KEY (`id`), 
  KEY `x_ps_ExamFinished_examId` (`examId`),
  KEY `x_ps_ExamFinished_orgId` (`creatorOrgId`)
)ENGINE=MYISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－考试完成表，考试状态等9时生成统计数据保存到本表';

DROP TABLE IF EXISTS `t_ps_SubjectFinished`;
CREATE TABLE `t_ps_SubjectFinished` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `examId` VARCHAR(36) NOT NULL COMMENT '考试唯一标识',
  `subjectId` VARCHAR(36) NOT NULL COMMENT '科目唯一标识',  
  `examName` VARCHAR(128)  COMMENT '考试名称', 
  `subjectName` VARCHAR(32)  COMMENT '考试科目名称',   
  `registers` INT(8) NOT NULL COMMENT '报名考生总数',
  `scans` INT(8) NOT NULL COMMENT '扫描总数',  
  `absentExaminees` INT(8) NOT NULL COMMENT '缺卡总数', 
  PRIMARY KEY (`id`), 
  KEY `x_ps_SubjectFinished_examId` (`examId`),
  KEY `x_ps_SubjectFinished_subjectId` (`subjectId`)
)ENGINE=MYISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－考试科目完成表，考试科目状态等9时生成统计数据保存到本表';

DROP TABLE IF EXISTS `t_ps_MarkItemFinished`;
CREATE TABLE `t_ps_MarkItemFinished` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `examId` VARCHAR(36) NOT NULL COMMENT '考试唯一标识',
  `subjectId` VARCHAR(36) NOT NULL COMMENT '科目唯一标识',
  `markItemId` VARCHAR(36) NOT NULL COMMENT '评题唯一标识',   
  `examName` VARCHAR(128)  COMMENT '考试名称', 
  `subjectName` VARCHAR(32)  COMMENT '考试科目名称',
  `itemName` VARCHAR(32)  COMMENT '科目评题名称',  
  `expected` INT(8) NOT NULL COMMENT '计划评题总数',
  `finished` INT(8) NOT NULL COMMENT '完成总数',
  `erros` INT(8) NOT NULL DEFAULT '0' COMMENT '问题卷总数', 
  `arbites2` INT(8) DEFAULT '0' COMMENT '双评仲裁总数', 
  `arbites3` INT(8) DEFAULT '0' COMMENT '三评仲裁总数',
  `markers` INT(8) DEFAULT '0' COMMENT '有效评人员总数',  
  PRIMARY KEY (`id`), 
  INDEX `x_ps_MarkItemFinished_examId` (`examId`),
  INDEX `x_ps_MarkItemFinished_subjectId` (`subjectId`),
  INDEX `x_ps_MarkItemFinished_markItemId` (`markItemId`)  
)ENGINE=MYISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷-考试科目评题完成表，考试科目状态等9时生成统计数据保存到本表';


/***考生成绩单***/
DROP TABLE IF EXISTS `t_ps_ExamTotalScoreList`;
CREATE TABLE `t_ps_ExamTotalScoreList` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `examId` VARCHAR(36) NOT NULL COMMENT '考试唯一标识',
  `schoolId` VARCHAR(36) COMMENT '考生所在学校唯一标识',     
  `clazzId` VARCHAR(36) COMMENT '考生所在班级唯一标识',
  `examineePersonId` VARCHAR(36) COMMENT '考生唯一标识',  
  `exam` VARCHAR(128) COMMENT '考试名称',
  `examDate` DATETIME COMMENT '考试时间',      
  `school` VARCHAR(128) COMMENT '学校',
  `grade` VARCHAR(8)  COMMENT '年级',  
  `clazz` VARCHAR(32) COMMENT '班级',
  `years` VARCHAR(16)  COMMENT '学年',
  `term` VARCHAR(32)  COMMENT '学期',              
  `point` VARCHAR(16) COMMENT '考号',   
  `room` VARCHAR(16)  COMMENT '考场号', 
  `examinee` VARCHAR(32) NOT NULL COMMENT '考生姓名',
  `examNumber` VARCHAR(32)  COMMENT '考号',  
  `score` FLOAT(3,1) DEFAULT '-1.0'  COMMENT '得分',  
  PRIMARY KEY (`id`), 
  KEY `x_ps_ExamTotalScoreList_examId` (`examId`),
  INDEX `x_ps_ExamTotalScoreList_schoolId` (`schoolId`),
  INDEX `x_ps_ExamTotalScoreList_clazzId` (`clazzId`), 
  INDEX `x_ps_ExamTotalScoreList_examineePersonId` (`examineePersonId`)     
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－考试总分成绩单';

DROP TABLE IF EXISTS `t_ps_SubjectTotalScoreList`;
CREATE TABLE `t_ps_SubjectTotalScoreList` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `examId` VARCHAR(36) NOT NULL COMMENT '考试唯一标识',
  `subjectId` VARCHAR(36) NOT NULL COMMENT '科目唯一标识',
  `targetSubjectId` VARCHAR(36) NOT NULL COMMENT '科目唯一标识,外部系统编码',  
  `schoolId` VARCHAR(36) COMMENT '考生所在学校唯一标识',     
  `clazzId` VARCHAR(36) COMMENT '考生所在班级唯一标识',
  `examineePersonId` VARCHAR(36) COMMENT '考生唯一标识',  
  `exam` VARCHAR(128) COMMENT '考试名称',
  `examDate` DATETIME COMMENT '考试时间',      
  `school` VARCHAR(128) COMMENT '学校',
  `grade` VARCHAR(8)  COMMENT '年级',   
  `clazz` VARCHAR(32) COMMENT '班级',
  `years` VARCHAR(16)  COMMENT '学年',
  `term` VARCHAR(32)  COMMENT '学期',   
  `subject` VARCHAR(32) COMMENT '科目名称',
  `targetSubjectName` VARCHAR(36) NOT NULL COMMENT '科目外部系统统一名称',
  `cryptCode` VARCHAR(32) NOT NULL COMMENT '密号',  
  `examinee` VARCHAR(32) NOT NULL COMMENT '考生姓名',
  `examNumber` VARCHAR(32)  COMMENT '考号',  
  `attended` VARCHAR(16) DEFAULT '正常' COMMENT '参与：正常；缺卡；缺考；作弊', 
  `score` FLOAT(3,1) DEFAULT '-1.0'  COMMENT '得分', 
  `kgScore` FLOAT(3,1) DEFAULT '-1.0'  COMMENT '主观题得分', 
  `zgScore` FLOAT(3,1) DEFAULT '-1.0'  COMMENT '客观题得分',
  `sheets` TEXT   COMMENT '答题卡图片地址绝对地址多图时以;分隔',    
  `marked` TEXT   COMMENT '评分轨迹,默认JSON数据结构',  
  PRIMARY KEY (`id`), 
  KEY `x_ps_SubjectTotalScoreList_examId` (`examId`),
  INDEX `x_ps_SubjectTotalScoreList_subjectId` (`subjectId`),  
  INDEX `x_ps_SubjectTotalScoreList_targetSubjectId` (`targetSubjectId`),  
  INDEX `x_ps_SubjectTotalScoreList_schoolId` (`schoolId`),
  INDEX `x_ps_SubjectTotalScoreList_clazzId` (`clazzId`), 
  INDEX `x_ps_SubjectTotalScoreList_examineePersonId` (`examineePersonId`)     
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－科目总分成绩单';

DROP TABLE IF EXISTS `t_ps_SubjectTotalScoreListSplit`;
CREATE TABLE `t_ps_SubjectTotalScoreListSplit` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `examId` VARCHAR(36) NOT NULL COMMENT '考试唯一标识',
  `subjectId` VARCHAR(36) NOT NULL COMMENT '科目唯一标识',  
  `targetSubjectId` VARCHAR(36) NOT NULL COMMENT '科目唯一标识,外部系统编码',  
  `schoolId` VARCHAR(36) COMMENT '考生所在学校唯一标识',     
  `clazzId` VARCHAR(36) COMMENT '考生所在班级唯一标识',
  `examineePersonId` VARCHAR(36) COMMENT '考生唯一标识',  
  `exam` VARCHAR(128) COMMENT '考试名称',
  `examDate` DATETIME COMMENT '考试时间',      
  `school` VARCHAR(128) COMMENT '学校',
  `grade` VARCHAR(8)  COMMENT '年级',   
  `clazz` VARCHAR(32) COMMENT '班级',
  `years` VARCHAR(16)  COMMENT '学年',
  `term` VARCHAR(32)  COMMENT '学期',
  `targetSubjectName` VARCHAR(36) NOT NULL COMMENT '科目外部系统统一名称',  
  `subject` VARCHAR(32) COMMENT '科目名称',
  `cryptCode` VARCHAR(32) NOT NULL COMMENT '密号',  
  `examinee` VARCHAR(32) NOT NULL COMMENT '考生姓名',
  `examNumber` VARCHAR(32)  COMMENT '考号',  
  `attended` VARCHAR(16) DEFAULT '正常' COMMENT '参与：正常；缺卡；缺考；作弊', 
  `score` FLOAT(3,1) DEFAULT '-1.0'  COMMENT '得分', 
  `kgScore` FLOAT(3,1) DEFAULT '-1.0'  COMMENT '主观题得分', 
  `zgScore` FLOAT(3,1) DEFAULT '-1.0'  COMMENT '客观题得分',
  `sheets` TEXT   COMMENT '答题卡图片地址绝对地址多图时以;分隔',    
  `marked` TEXT   COMMENT '评分轨迹,默认JSON数据结构',  
  PRIMARY KEY (`id`), 
  KEY `x_ps_SubjectTotalScoreListSplit_examId` (`examId`),
  INDEX `x_ps_SubjectTotalScoreListSplit_subjectId` (`subjectId`),   
  INDEX `x_ps_SubjectTotalScoreListSplit_targetSubjectId` (`targetSubjectId`),  
  INDEX `x_ps_SubjectTotalScoreListSplit_schoolId` (`schoolId`),
  INDEX `x_ps_SubjectTotalScoreListSplit_clazzId` (`clazzId`), 
  INDEX `x_ps_SubjectTotalScoreListSplit_examineePersonId` (`examineePersonId`)     
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－科目总分成绩单';

DROP TABLE IF EXISTS `t_ps_SubjectTotalScoreListTrace`;
CREATE TABLE `t_ps_SubjectTotalScoreListTrace` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `examId` VARCHAR(36) NOT NULL COMMENT '考试唯一标识',
  `subjectId` VARCHAR(36) NOT NULL COMMENT '科目唯一标识,外部系统统一编码',      
  `marked` TEXT   COMMENT '原有评分轨迹,默认JSON数据结构',    
  `markedNew` TEXT   COMMENT '更新后的评分轨迹,默认JSON数据结构',
  `lastUpdateTime` TIMESTAMP DEFAULT NOW() COMMENT '最后更新时间',
  `lastOperatorId` VARCHAR(36)  COMMENT '最后更新者唯一标识',
  `lastOperatorName` VARCHAR(64)  COMMENT '最后更新者姓名',   
  PRIMARY KEY (`id`), 
  KEY `x_ps_SubjectTotalScoreListTrace_examId` (`examId`),
  INDEX `x_ps_SubjectTotalScoreListTrace_subjectId` (`subjectId`)    
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷-科目总分成绩单变更记录';


/***评卷员评卷清单***/
DROP TABLE IF EXISTS `t_ps_SubjectMakedList`;
CREATE TABLE `t_ps_SubjectMakedList` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `examId` VARCHAR(36) NOT NULL COMMENT '考试唯一标识',
  `targetSubjectId` VARCHAR(36) NOT NULL COMMENT '科目唯一标识,外部系统编码',
  `targetSubjectName` VARCHAR(16) COMMENT '科目名称,外部系统名称',    
  `markerPersonId` VARCHAR(36) COMMENT '考生唯一标识',
  `marker` VARCHAR(32) COMMENT '评卷员姓名',  
  `exam` VARCHAR(128) COMMENT '考试名称',
  `examDate` DATETIME COMMENT '考试时间',      
  `grade` VARCHAR(8)  COMMENT '年级',   
  `years` VARCHAR(16)  COMMENT '学年',
  `term` VARCHAR(32)  COMMENT '学期',   
  `subject` VARCHAR(32) COMMENT '科目名称',    
  `item` VARCHAR(32) COMMENT '评题名称',
  `itemScore`  FLOAT(3,1) COMMENT '评题分数',           
  `total` INT(8)  COMMENT '完成数量',
  `arbits` INT(8)  COMMENT '多评仲裁产生数量',
  `unables` INT(8)  COMMENT '问题卷提交数量',    
  `average` FLOAT(3,1) DEFAULT '-1.0'  COMMENT '平均分',
  `median` FLOAT(3,1) DEFAULT '-1.0'  COMMENT '中位分', 
  `speed` FLOAT(3,1) DEFAULT '-1.0'  COMMENT '评卷速率',       
  `marked` TEXT   COMMENT '评分轨迹,以;分隔,问题卷以#表示',       
  PRIMARY KEY (`id`), 
  KEY `x_ps_SubjectMakedList_examId` (`examId`),
  INDEX `x_ps_SubjectMakedList_targetSubjectId` (`targetSubjectId`),  
  INDEX `x_ps_SubjectMakedList_markerPersonId` (`markerPersonId`)     
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷-评卷人科目可评分评题清单';

/***科目清单***/
DROP TABLE IF EXISTS `t_ps_SubjectPaperList`;
CREATE TABLE `t_ps_SubjectPaperList` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `examId` VARCHAR(36) NOT NULL COMMENT '考试唯一标识',
  `targetSubjectId` VARCHAR(36) NOT NULL COMMENT '科目唯一标识,外部系统编码',
  `targetSubjectName` VARCHAR(16) COMMENT '科目名称,外部系统名称',  
  `exam` VARCHAR(128) COMMENT '考试名称',
  `examDate` DATETIME COMMENT '考试时间',      
  `grade` VARCHAR(8)  COMMENT '年级',   
  `years` VARCHAR(16)  COMMENT '学年',
  `term` VARCHAR(32)  COMMENT '学期',   
  `subject` VARCHAR(32) COMMENT '科目名称',          
  `score` FLOAT(3,1)  COMMENT '科目满分',
  `examinees` INT(8)   COMMENT '有效考生人数',  
  `average` FLOAT(3,1) DEFAULT '-1.0'  COMMENT '考生平均分',  
  `median` FLOAT(3,1) DEFAULT '-1.0'  COMMENT '考生中位分',   
  `kgScore` FLOAT(3,1)  COMMENT '客观题分数',
  `zgScore` FLOAT(3,1)  COMMENT '主观题分数',    
  `papers` VARCHAR(512) COMMENT '所用试卷绝对地址',
  `sheets` VARCHAR(512)  COMMENT '所用答题卡图片绝对地址',       
  `scores` TEXT   COMMENT '有效考生的科目得分,以;分隔',       
  PRIMARY KEY (`id`), 
  KEY `x_ps_SubjectPaperList_examId` (`examId`),
  INDEX `x_ps_SubjectPaperList_targetSubjectId` (`targetSubjectId`)   
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='阅卷－科目评卷清单';
