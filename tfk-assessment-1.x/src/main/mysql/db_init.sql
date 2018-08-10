DROP TABLE IF EXISTS `as_AsIndex`;
CREATE TABLE `as_AsIndex` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `asIndexId` VARCHAR(36) NOT NULL  COMMENT '评价指标唯一标识',
  `parentAsIndexId` VARCHAR(36)  COMMENT '评价指标上级唯一标识',
  `category` VARCHAR(16) NOT NULL  COMMENT '评价指标类别',
  `name` VARCHAR(16) NOT NULL  COMMENT '评价指标名称',
  `alias` VARCHAR(8)  COMMENT '评价指标简称',
  `score` DOUBLE(5,2) NOT NULL  COMMENT '评价指标分值',
  `weight` DOUBLE(3,2) NOT NULL  COMMENT '评价指标得分计算权重,取值范围0-1',
  PRIMARY KEY (`id`),
  KEY `x_as_AsIndex_asIndexId` (`asIndexId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='评价指标';

DROP TABLE IF EXISTS `as_Assessee`;
CREATE TABLE `as_Assessee` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `collaboratorId` varchar(36) NOT NULL COMMENT '评价参与者唯一标识',
  `studentId` varchar(36) NOT NULL COMMENT '学生唯一标识',
  `schoolId` varchar(36) NOT NULL COMMENT '学校唯一标识',
  `personId` varchar(36) NOT NULL COMMENT '人员唯一标识',
  PRIMARY KEY (`id`),
  INDEX `x_as_Assessee_studentId` (`studentId`),
  INDEX `x_as_Assessee_schoolId` (`schoolId`),
  INDEX `x_as_Assessee_personId` (`personId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='被评者-学生';

DROP TABLE IF EXISTS `as_Assessor`;
CREATE TABLE `as_Assessor` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT ,
  `collaboratorId` varchar(36) NOT NULL COMMENT '评价参与者唯一标识',
  `teacherId` varchar(36) NOT NULL COMMENT '老师唯一标识',
  `schoolId` varchar(36) NOT NULL COMMENT '学校唯一标识',
  `personId` varchar(36) NOT NULL COMMENT '人员唯一标识',
  PRIMARY KEY (`id`),
  INDEX `x_as_Assessor_teacherId` (`teacherId`),
  INDEX `x_as_Assessor_schoolId` (`schoolId`),
  INDEX `x_as_Assessor_personId` (`personId`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='主评者-老师';