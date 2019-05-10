# sharetalent
一个集api和后台管理的单机项目，包含mp的代码自动生成

需要初始化的sql语句
```
DROP TABLE IF EXISTS `sys_oper`;
CREATE TABLE `sys_oper` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `user_name` varchar(200) NOT NULL COMMENT '账号',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `real_name` varchar(20) NOT NULL COMMENT '真是姓名',
  `mobile_number` varchar(20) NOT NULL COMMENT '手机号码',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态，1:正常;2:已删除;3:已失效',
  `remark` varchar(100) NOT NULL DEFAULT '' COMMENT '备注',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `modify_date` datetime NOT NULL COMMENT '修改时间',
  `time_stamp` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '时间戳',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_username_unique` (`user_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_oper
-- ----------------------------
INSERT INTO `sys_oper` VALUES ('1', 'admin', '123456', 'admin', '13511111111', '1', '', '2019-05-10 15:12:25', '2019-05-10 15:12:28', null);

DROP TABLE IF EXISTS `user_wechat`;
CREATE TABLE `user_wechat` (
  `id` bigint(20) NOT NULL COMMENT '主键id',
  `open_id` varchar(80) CHARACTER SET utf8 DEFAULT NULL COMMENT '微信获取用户信息的凭证，对于某个小程序具有唯一性',
  `oper_id` bigint(20) DEFAULT NULL COMMENT '关联管理员的ID',
  `phone` varchar(80) CHARACTER SET utf8 DEFAULT NULL COMMENT '手机号',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `real_name` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '真实姓名',
  `nick_name` varchar(64) DEFAULT NULL COMMENT '昵称',
  `pic_url` varchar(300) CHARACTER SET utf8 DEFAULT NULL COMMENT '头像',
  `sex` tinyint(1) DEFAULT NULL COMMENT '性别（0:男 1:女）',
  `city` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '市',
  `province` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '省',
  `country` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '国家',
  `language` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '语言',
  `time_stamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间戳',
  `remark` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```
