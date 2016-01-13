CREATE TABLE `tbl_nomme_discount` (
  `uuid` varchar(32) NOT NULL,
  `coupon_code` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `price` decimal(18,2) DEFAULT NULL,
  `consume_price` decimal(18,2) DEFAULT NULL,
  `discount` decimal(5,2) DEFAULT NULL,
  `type` int(11) NOT NULL COMMENT '1:cash 2:percentage 3:free items',
  `order_uuid` varchar(32) DEFAULT NULL,
  `consumer_uuid` varchar(32) DEFAULT NULL,
  `start_time` varchar(50) DEFAULT NULL,
  `end_time` varchar(50) DEFAULT NULL,
  `order_type` int(11) DEFAULT NULL,
  `dish_id` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `delete_status` int(11) DEFAULT '0',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='talbe that stores global discounts, one time or many times use coupon, promotion codes';
