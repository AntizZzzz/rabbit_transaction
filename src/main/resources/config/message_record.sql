CREATE TABLE `message_record` (
  `id_` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `business_id` varchar(64) DEFAULT NULL COMMENT '业务数据ID',
  `business_type` tinyint(2) DEFAULT NULL COMMENT '业务类型：具体业务',
  `message_id` varchar(64) NOT NULL COMMENT '消息ID',
  `retries_number` tinyint(2) DEFAULT '0' COMMENT '重试次数',
  `status_` tinyint(2) DEFAULT '0' COMMENT '结果 1 成功  0 失败',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id_`),
  UNIQUE KEY `inx_message_id` (`message_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='rabbit消息记录';