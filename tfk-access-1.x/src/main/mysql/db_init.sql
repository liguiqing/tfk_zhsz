
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


