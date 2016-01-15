DROP TABLE IF EXISTS tbl_nomme_discount;

CREATE TABLE `tbl_nomme_discount` (
  `uuid` varchar(32) NOT NULL,
  `coupon_code` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `price` decimal(18,2) DEFAULT NULL,
  `consume_price` decimal(18,2) DEFAULT NULL,
  `discount` decimal(5,2) DEFAULT NULL,
  `type` int(11) NOT NULL COMMENT '1:cash 2:percentage 3:free items',
  `start_time` varchar(50) DEFAULT NULL,
  `end_time` varchar(50) DEFAULT NULL,
  `order_type` int(11) DEFAULT NULL COMMENT 'apply coupon to: 1:delivery 2:pick up 3：dine-in/reservation 4:apply to all',
  `dish_id` int(11) DEFAULT NULL,
  `max_uses` int(11) DEFAULT NULL COMMENT 'if max_uses=1 then this is a one time use coupon',
  `used_count` int(11) DEFAULT '0' COMMENT 'count number of uses, if equal to max_uses then the coupon will be expired',  
  `status` int(11) DEFAULT '0',
  `delete_status` int(11) DEFAULT '0',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='talbe that stores global discounts, one time or many times use coupon, promotion codes';
