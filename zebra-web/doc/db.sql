-- MySQL dump 10.14  Distrib 5.5.56-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: simcloud
-- ------------------------------------------------------
-- Server version	5.5.56-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bill`
--

DROP TABLE IF EXISTS `bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bill` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `month` varchar(16) DEFAULT NULL,
  `cost` decimal(10,2) DEFAULT NULL,
  `flow` decimal(10,3) DEFAULT NULL,
  `status` tinyint(4) DEFAULT '0',
  `uid` int(11) DEFAULT NULL,
  `cid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bill_record`
--

DROP TABLE IF EXISTS `bill_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bill_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `flow` decimal(10,3) DEFAULT '0.000',
  `iccid` varchar(45) DEFAULT NULL,
  `cost` decimal(10,2) DEFAULT '0.00',
  `month` varchar(16) DEFAULT NULL,
  `type` int(11) DEFAULT '0' COMMENT '0:单卡',
  `detail` text,
  `create_time` datetime DEFAULT NULL,
  `bill_id` int(11) DEFAULT NULL,
  `uid` int(11) DEFAULT NULL,
  `cid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cart_card`
--

DROP TABLE IF EXISTS `cart_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cart_card` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `flow` int(11) DEFAULT NULL,
  `package_id` int(11) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `num` int(11) DEFAULT NULL,
  `term` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `status` tinyint(4) DEFAULT '1',
  `order_id` int(11) DEFAULT '0',
  `cid` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `type` int(11) DEFAULT '0' COMMENT '0=购物车\n1=已提交\n2=已经分配',
  `operator` int(11) DEFAULT '3',
  `name` varchar(64) DEFAULT NULL,
  `alloc_num` int(11) DEFAULT '0' COMMENT '已经分配的卡片',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `charging`
--

DROP TABLE IF EXISTS `charging`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `charging` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `money` decimal(11,4) DEFAULT '0.0000',
  `wallet_id` int(11) NOT NULL DEFAULT '0',
  `type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '1-在线购卡、2-流量池续费、3-卡片续费、4-短信服务、5-充值',
  `create_user_id` int(11) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL COMMENT '0-未删除，1-删除',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `business_code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `business_url` varchar(255) DEFAULT NULL,
  `business_auth` tinyint(1) DEFAULT '0' COMMENT '0:工商未认证\n1:工商认证',
  `legal_name` varchar(45) DEFAULT NULL COMMENT '法人姓名',
  `legal_code` varchar(45) DEFAULT NULL COMMENT '法人身份证号',
  `legal_positive` varchar(255) DEFAULT NULL COMMENT '证明身份证',
  `legal_back` varchar(255) DEFAULT NULL COMMENT '背面',
  `create_time` varchar(45) DEFAULT NULL,
  `update_time` varchar(45) DEFAULT NULL,
  `status` tinyint(1) DEFAULT '0',
  `legal_auth` tinyint(1) DEFAULT '0' COMMENT '1:法人认证\n0:法人未认证',
  `uid` int(11) DEFAULT '0',
  `create_user_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `finance`
--

DROP TABLE IF EXISTS `finance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `finance` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `cid` int(11) DEFAULT NULL,
  `order_code` varchar(45) DEFAULT NULL,
  `money` decimal(10,2) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `balance` decimal(10,2) DEFAULT NULL,
  `type` tinyint(4) DEFAULT '0' COMMENT '0=充值\n1=购卡',
  `update_time` datetime DEFAULT NULL,
  `status` tinyint(4) DEFAULT '1',
  `create_user_id` int(10) DEFAULT NULL,
  `order_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `flow_pool`
--

DROP TABLE IF EXISTS `flow_pool`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flow_pool` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `pool_name` varchar(100) NOT NULL,
  `total_pool` int(11) DEFAULT '0',
  `use_pool` int(11) DEFAULT '0',
  `add_pool` int(11) DEFAULT '0',
  `operator` tinyint(4) DEFAULT '1' COMMENT '1-移动、2-电信、3-联通',
  `uid` int(11) DEFAULT NULL,
  `cid` int(11) DEFAULT NULL,
  `create_user_id` int(11) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `pool_name_UNIQUE` (`pool_name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order_goods`
--

DROP TABLE IF EXISTS `order_goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `deliver_cost` decimal(10,2) DEFAULT '0.00',
  `total_cost` decimal(10,2) DEFAULT '0.00',
  `card_cost` decimal(10,2) DEFAULT '0.00',
  `order_code` varchar(45) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `uid` int(11) NOT NULL,
  `status` tinyint(4) DEFAULT '1',
  `type` tinyint(4) DEFAULT '0' COMMENT '0',
  `card_count` int(11) DEFAULT NULL,
  `remark` text,
  `addr_id` int(11) DEFAULT NULL,
  `deliver_type` int(11) DEFAULT '0' COMMENT '0=中通\n1=顺丰',
  `order_time` datetime DEFAULT NULL,
  `pay_time` datetime DEFAULT NULL,
  `audit_time` datetime DEFAULT NULL,
  `outer_time` datetime DEFAULT NULL COMMENT '发货时间',
  `suc_time` datetime DEFAULT NULL COMMENT '完成时间',
  `deliver_code` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `unicom` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `package`
--

DROP TABLE IF EXISTS `package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `package` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `cid` int(11) DEFAULT NULL,
  `package_type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1-按月套餐、2-加油包、3-超额流量包、4-自定义套餐包',
  `flow` int(11) NOT NULL DEFAULT '0',
  `messages` int(11) DEFAULT '0',
  `call_minutes` int(11) DEFAULT '0',
  `term` int(4) DEFAULT '1',
  `plat_quote` decimal(10,4) NOT NULL DEFAULT '0.0000',
  `external_quote` decimal(10,4) NOT NULL DEFAULT '0.0000',
  `operator` tinyint(1) NOT NULL DEFAULT '0',
  `plan_id` int(11) DEFAULT '0',
  `account` varchar(100) DEFAULT NULL,
  `remark` text,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_user_id` int(11) DEFAULT '0',
  `status` tinyint(1) DEFAULT '1',
  `type` tinyint(1) DEFAULT '0' COMMENT '0:用户套餐\n1：系统套餐',
  `card_type` int(11) DEFAULT '0',
  `name` varchar(64) DEFAULT NULL,
  `real_flow` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `recharge`
--

DROP TABLE IF EXISTS `recharge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recharge` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '1-支付宝、2-手动充值',
  `recharge_amount` decimal(10,4) DEFAULT '0.0000',
  `pay_teller` varchar(200) DEFAULT NULL,
  `pay_account` varchar(100) DEFAULT NULL,
  `pay_pic_url` varchar(255) DEFAULT NULL,
  `cid` int(11) NOT NULL DEFAULT '0',
  `uid` int(11) NOT NULL DEFAULT '0',
  `is_pay_succes` tinyint(11) DEFAULT '0' COMMENT '1-已经到账  0-未到账',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_user_id` int(11) DEFAULT '0',
  `status` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sim_card`
--

DROP TABLE IF EXISTS `sim_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sim_card` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `iccid` varchar(100) NOT NULL DEFAULT '0',
  `phone` varchar(50) DEFAULT '0',
  `uid` int(11) NOT NULL DEFAULT '0',
  `cid` int(11) DEFAULT NULL,
  `type` tinyint(1) DEFAULT '1' COMMENT '1-单卡、2-流量池卡',
  `net_type` tinyint(1) DEFAULT NULL,
  `operator` tinyint(1) DEFAULT '1',
  `obj_type` tinyint(1) DEFAULT NULL,
  `used_flow` decimal(10,3) DEFAULT '0.000',
  `remark` text,
  `package_id` int(11) DEFAULT '0',
  `open_time` datetime DEFAULT NULL,
  `expire_time` datetime DEFAULT NULL,
  `last_sync_time` datetime DEFAULT NULL,
  `account` varchar(50) DEFAULT NULL,
  `create_user_id` int(11) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `status` tinyint(1) DEFAULT '1',
  `used_messages` int(11) DEFAULT '0',
  `used_minutes` int(11) DEFAULT '0',
  `ip` varchar(45) DEFAULT NULL,
  `terminal_id` varchar(128) DEFAULT NULL,
  `cart_card_id` int(11) DEFAULT '0' COMMENT '订单ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `iccid` (`iccid`)
) ENGINE=MyISAM AUTO_INCREMENT=75 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sim_card_flow`
--

DROP TABLE IF EXISTS `sim_card_flow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sim_card_flow` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `day` date NOT NULL,
  `iccid` varchar(45) NOT NULL,
  `flow` decimal(10,3) DEFAULT '0.000' COMMENT 'kb',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index2` (`iccid`,`day`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sim_pool_relation`
--

DROP TABLE IF EXISTS `sim_pool_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sim_pool_relation` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `sim_card_id` int(11) NOT NULL,
  `flow_pool_id` int(11) NOT NULL,
  `cid` int(11) NOT NULL DEFAULT '0',
  `uid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary table structure for view `simcard_package_view`
--

DROP TABLE IF EXISTS `simcard_package_view`;
/*!50001 DROP VIEW IF EXISTS `simcard_package_view`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `simcard_package_view` (
  `id` tinyint NOT NULL,
  `iccid` tinyint NOT NULL,
  `phone` tinyint NOT NULL,
  `uid` tinyint NOT NULL,
  `cid` tinyint NOT NULL,
  `type` tinyint NOT NULL,
  `net_type` tinyint NOT NULL,
  `operator` tinyint NOT NULL,
  `obj_type` tinyint NOT NULL,
  `used_flow` tinyint NOT NULL,
  `remark` tinyint NOT NULL,
  `package_id` tinyint NOT NULL,
  `open_time` tinyint NOT NULL,
  `expire_time` tinyint NOT NULL,
  `last_sync_time` tinyint NOT NULL,
  `account` tinyint NOT NULL,
  `create_user_id` tinyint NOT NULL,
  `create_time` tinyint NOT NULL,
  `update_time` tinyint NOT NULL,
  `terminal_id` tinyint NOT NULL,
  `status` tinyint NOT NULL,
  `cart_card_id` tinyint NOT NULL,
  `used_messages` tinyint NOT NULL,
  `used_minutes` tinyint NOT NULL,
  `ip` tinyint NOT NULL,
  `package_type` tinyint NOT NULL,
  `flow` tinyint NOT NULL,
  `real_flow` tinyint NOT NULL,
  `external_quote` tinyint NOT NULL,
  `package_left` tinyint NOT NULL,
  `card_type` tinyint NOT NULL,
  `plan_id` tinyint NOT NULL,
  `package_used` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `sys_address`
--

DROP TABLE IF EXISTS `sys_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(45) DEFAULT NULL,
  `province_id` int(11) DEFAULT NULL,
  `city_id` int(11) DEFAULT NULL,
  `dis_id` int(11) DEFAULT NULL,
  `location` text,
  `phone` varchar(45) DEFAULT NULL,
  `opt` int(11) DEFAULT '0' COMMENT '0:非默认地址\n1：默认地址',
  `uid` int(11) DEFAULT NULL,
  `area` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_area`
--

DROP TABLE IF EXISTS `sys_area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_area` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `deep` int(11) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uids` (`deep`,`name`,`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=45154 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(64) NOT NULL COMMENT '用户姓名',
  `passwd` varchar(64) NOT NULL,
  `email` varchar(64) DEFAULT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1 有效\\n0：无效',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `create_user_id` int(11) DEFAULT '0' COMMENT '父用户ID',
  `cid` int(11) DEFAULT NULL COMMENT '企业ID',
  `phone` varchar(64) DEFAULT NULL COMMENT '手机号',
  `remark` text,
  `role` int(11) DEFAULT '1' COMMENT '0:超级管理员\n1：普通管理员\n2:普通用户',
  `auth` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone_UNIQUE` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tariff_plan`
--

DROP TABLE IF EXISTS `tariff_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tariff_plan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `type` tinyint(4) DEFAULT '0' COMMENT '1-月付单个、2-月付灵活共享、3-月付固定共享、4-预付单个（按期限）、5-预付灵活共享、6-预付固定共享、7-追加（预付）、8-事件（预付）、堆叠事件（预付）',
  `cost` decimal(8,2) DEFAULT '0.00' COMMENT '流量费用',
  `flow` int(11) DEFAULT '0' COMMENT '流量 mb',
  `messages` int(11) DEFAULT '0',
  `call_minutes` int(11) DEFAULT '0',
  `version_id` int(11) DEFAULT '0',
  `account` varchar(64) DEFAULT NULL,
  `operator` tinyint(1) DEFAULT '3' COMMENT '1-移动、2-电信、3-联通	',
  `is_m_service` tinyint(1) DEFAULT '0' COMMENT '0-未订阅该服务  1-订阅该服务',
  `is_call_service` tinyint(1) DEFAULT '0' COMMENT '0-未订阅该服务  1-',
  `uid` int(11) DEFAULT '0',
  `is_flow_service` tinyint(1) DEFAULT '0' COMMENT '0-未订阅该服务  1-',
  `update_time` datetime DEFAULT NULL,
  `create_user_id` int(11) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `status` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wallet`
--

DROP TABLE IF EXISTS `wallet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wallet` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL DEFAULT '0',
  `cid` int(11) DEFAULT NULL,
  `balance` decimal(11,4) DEFAULT '0.0000',
  `threshold` int(11) DEFAULT '2',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_user_id` int(11) DEFAULT '0',
  `status` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Final view structure for view `simcard_package_view`
--

/*!50001 DROP TABLE IF EXISTS `simcard_package_view`*/;
/*!50001 DROP VIEW IF EXISTS `simcard_package_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`sim`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `simcard_package_view` AS select `a`.`id` AS `id`,`a`.`iccid` AS `iccid`,`a`.`phone` AS `phone`,`a`.`uid` AS `uid`,`a`.`cid` AS `cid`,`a`.`type` AS `type`,`a`.`net_type` AS `net_type`,`a`.`operator` AS `operator`,`a`.`obj_type` AS `obj_type`,`a`.`used_flow` AS `used_flow`,`a`.`remark` AS `remark`,`a`.`package_id` AS `package_id`,`a`.`open_time` AS `open_time`,`a`.`expire_time` AS `expire_time`,`a`.`last_sync_time` AS `last_sync_time`,`a`.`account` AS `account`,`a`.`create_user_id` AS `create_user_id`,`a`.`create_time` AS `create_time`,`a`.`update_time` AS `update_time`,`a`.`terminal_id` AS `terminal_id`,`a`.`status` AS `status`,`a`.`cart_card_id` AS `cart_card_id`,`a`.`used_messages` AS `used_messages`,`a`.`used_minutes` AS `used_minutes`,`a`.`ip` AS `ip`,`b`.`package_type` AS `package_type`,`b`.`flow` AS `flow`,`b`.`real_flow` AS `real_flow`,`b`.`external_quote` AS `external_quote`,(case when isnull(`b`.`id`) then 0.0 else (`b`.`flow` - `a`.`used_flow`) end) AS `package_left`,`b`.`card_type` AS `card_type`,`b`.`plan_id` AS `plan_id`,(case when isnull(`b`.`id`) then 0.0 when (`a`.`used_flow` >= `b`.`flow`) then `b`.`flow` else `a`.`used_flow` end) AS `package_used` from (`sim_card` `a` left join `package` `b` on(((`a`.`package_id` = `b`.`id`) and (`b`.`status` = 1)))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-23 17:27:22
