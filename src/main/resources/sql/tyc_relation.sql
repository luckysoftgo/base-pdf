/*
 * 记录企业企业id
 */
DROP TABLE IF EXISTS `tyc_relation_info`;
CREATE TABLE `tyc_relation_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增长的主键',
  `tyc_id` varchar(50)  DEFAULT '' COMMENT '天眼查返回的企业的Id',
  `credit_code` varchar(50)  DEFAULT '' COMMENT '统一社会信用代码',
  `company_name` varchar(50) DEFAULT '' COMMENT '公司名称',
  `business_type` varchar(50) DEFAULT '10000' COMMENT '业务类型',
  `request_url` varchar(256) DEFAULT '' COMMENT '请求地址',
  `data_json` longtext  COMMENT '业务类型',
  `create_by` bigint(20) DEFAULT 1 COMMENT '创建者用户ID',
  `create_time` datetime DEFAULT NOW() COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='天眼查和企业数据关联';
