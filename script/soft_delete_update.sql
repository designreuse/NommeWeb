CALL addFieldIfNotExists ('dat_cart_header', 'status', 'INT DEFAULT 0');
CALL addFieldIfNotExists ('dat_cart_item', 'status', 'INT DEFAULT 0');
CALL addFieldIfNotExists ('dat_dish', 'status', 'INT DEFAULT 0');
CALL addFieldIfNotExists ('dat_garnish_item', 'status', 'INT DEFAULT 0');
CALL addFieldIfNotExists ('dat_order_header', 'status', 'INT DEFAULT 0');
CALL addFieldIfNotExists ('dat_order_item', 'status', 'INT DEFAULT 0');
CALL addFieldIfNotExists ('dat_restaurants', 'status', 'INT DEFAULT 0');
CALL addFieldIfNotExists ('res_cart_dish_garnish', 'status', 'INT DEFAULT 0');
CALL addFieldIfNotExists ('res_dish_garnish', 'status', 'INT DEFAULT 0');
CALL addFieldIfNotExists ('res_order_dish_garnish', 'status', 'INT DEFAULT 0');
CALL addFieldIfNotExists ('res_restaurant_areas', 'status', 'INT DEFAULT 0');
CALL addFieldIfNotExists ('res_restaurant_classification', 'status', 'INT DEFAULT 0');
CALL addFieldIfNotExists ('sys_admin', 'status', 'INT DEFAULT 0');
CALL addFieldIfNotExists ('sys_areas', 'status', 'INT DEFAULT 0');
CALL addFieldIfNotExists ('sys_classification', 'status', 'INT DEFAULT 0');
CALL addFieldIfNotExists ('tbl_chain', 'status', 'INT DEFAULT 0');
CALL addFieldIfNotExists ('tbl_charity', 'status', 'INT DEFAULT 0');
CALL addFieldIfNotExists ('tbl_complain', 'status', 'INT DEFAULT 0');
CALL addFieldIfNotExists ('tbl_complain_reply', 'status', 'INT DEFAULT 0');
CALL addFieldIfNotExists ('tbl_consumers', 'status', 'INT DEFAULT 0');
CALL addFieldIfNotExists ('tbl_consumers_address', 'status', 'INT DEFAULT 0');
CALL addFieldIfNotExists ('tbl_customer_bankaccount', 'status', 'INT DEFAULT 0');
CALL addFieldIfNotExists ('tbl_customer_favorites', 'status', 'INT DEFAULT 0');
CALL addFieldIfNotExists ('tbl_discount', 'status', 'INT DEFAULT 0');
CALL addFieldIfNotExists ('tbl_distance_price', 'status', 'INT DEFAULT 0');
CALL addFieldIfNotExists ('tbl_evaluate', 'status', 'INT DEFAULT 0');
CALL addFieldIfNotExists ('tbl_garnish_header', 'status', 'INT DEFAULT 0');
CALL addFieldIfNotExists ('tbl_open_time', 'status', 'INT DEFAULT 0');
CALL addFieldIfNotExists ('tbl_order_charity', 'status', 'INT DEFAULT 0');
CALL addFieldIfNotExists ('tbl_restaurant_menu', 'status', 'INT DEFAULT 0');
CALL addFieldIfNotExists ('tbl_restaurant_table', 'status', 'INT DEFAULT 0');
CALL addFieldIfNotExists ('tbl_restaurants_user', 'status', 'INT DEFAULT 0');