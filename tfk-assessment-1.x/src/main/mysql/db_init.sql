DROP TABLE IF EXISTS `as_Index`;
CREATE TABLE `as_Index` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `indexId` VARCHAR(36) NOT NULL  COMMENT '评价指标唯一标识',
  `parentId` VARCHAR(36)  COMMENT '评价指标上级唯一标识',
  `ownerId` VARCHAR(36)  COMMENT '定制指标唯一标识',
  `category` VARCHAR(16) NOT NULL  COMMENT '评价指标类别',
  `name` VARCHAR(16) NOT NULL  COMMENT '评价指标名称',
  `alias` VARCHAR(8)  COMMENT '评价指标简称',
  `score` DOUBLE(5,2)  COMMENT '评价指标分值',
  `weight` DOUBLE(3,2) COMMENT '评价指标得分计算权重,取值范围0-1',
  `description` VARCHAR(128)   COMMENT '评价指标说明',
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
  `indexId` varchar(36) NOT NULL COMMENT '评价指标唯一标识',
  `doneDate` TIMESTAMP DEFAULT NOW() COMMENT '评价完成时间',
  `category` VARCHAR(16) NOT NULL  COMMENT '评价指标类别',
  `score` DOUBLE(5,2)  COMMENT '评价得分',
  `removed` TINYINT(1) DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `x_as_Assess_assessId` (`assessId`),
  INDEX `x_as_Assess_assessorId` (`assessorId`),
  INDEX `x_as_Assess_assesseeId` (`assesseeId`),
  INDEX `x_as_Assess_indexId` (`indexId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='评价信息表';

DROP TABLE IF EXISTS `as_Medal`;
CREATE TABLE `as_Medal` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `medalId` varchar(36) NOT NULL COMMENT '勋章唯一标识',
  `highId` varchar(36)  COMMENT '上级勋章唯一标识',
  `lowId` varchar(36)  COMMENT '次级勋章唯一标识',
  `schoolId` varchar(36) NOT NULL COMMENT '勋章所属学校唯一标识',
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
