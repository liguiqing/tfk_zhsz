DROP TABLE IF EXISTS `as_Index`;
CREATE TABLE `as_Index` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `indexId` VARCHAR(36) NOT NULL  COMMENT '评价指标唯一标识',
  `parentIndexId` VARCHAR(36)  COMMENT '评价指标上级唯一标识',
  `mappendIndexId` VARCHAR(36)  COMMENT '评价指标上级唯一标识',
  `category` VARCHAR(16) NOT NULL  COMMENT '评价指标类别',
  `name` VARCHAR(16) NOT NULL  COMMENT '评价指标名称',
  `alias` VARCHAR(8)  COMMENT '评价指标简称',
  `score` DOUBLE(5,2)  COMMENT '评价指标分值',
  `weight` DOUBLE(3,2) COMMENT '评价指标得分计算权重,取值范围0-1',
  `description` VARCHAR(128)   COMMENT '评价指标说明',
  PRIMARY KEY (`id`),
  KEY `x_as_Index_indexId` (`indexId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='评价指标';

DROP TABLE IF EXISTS `as_Assessee`;
CREATE TABLE `as_Assessee` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `assesseeId` varchar(36) NOT NULL COMMENT '被评者唯一标识',
  `schoolId` varchar(36) NOT NULL COMMENT '学校唯一标识',
  `personId` varchar(36) NOT NULL COMMENT '人员唯一标识',
  PRIMARY KEY (`id`),
  INDEX `x_as_Assessee_assesseeId` (`assesseeId`),
  INDEX `x_as_Assessee_schoolId` (`schoolId`),
  INDEX `x_as_Assessee_personId` (`personId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='被评者-学生';

DROP TABLE IF EXISTS `as_Assessor`;
CREATE TABLE `as_Assessor` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `assessorId` varchar(36) NOT NULL COMMENT '主评者唯一标识',
  `schoolId` varchar(36) NOT NULL COMMENT '学校唯一标识',
  `personId` varchar(36) NOT NULL COMMENT '人员唯一标识',
  PRIMARY KEY (`id`),
  INDEX `x_as_Assessor_assessorId` (`assessorId`),
  INDEX `x_as_Assessor_schoolId` (`schoolId`),
  INDEX `x_as_Assessor_personId` (`personId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='主评者-老师';

DROP TABLE IF EXISTS `as_Assessor`;
CREATE TABLE `as_Assessor` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `assessorId` varchar(36) NOT NULL COMMENT '主评者唯一标识',
  `schoolId` varchar(36) NOT NULL COMMENT '学校唯一标识',
  `personId` varchar(36) NOT NULL COMMENT '人员唯一标识',
  PRIMARY KEY (`id`),
  INDEX `x_as_Assessor_assessorId` (`assessorId`),
  INDEX `x_as_Assessor_schoolId` (`schoolId`),
  INDEX `x_as_Assessor_personId` (`personId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='主评者-老师';
