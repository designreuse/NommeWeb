CREATE DEFINER=`MetaUniverse`@`%` PROCEDURE `addFieldIfNotExists`(
    IN table_name_IN VARCHAR(100)
    , IN field_name_IN VARCHAR(100)
    , IN field_definition_IN VARCHAR(100)
)
BEGIN

    SET @isFieldThere = (SELECT COUNT(COLUMN_NAME) 
						FROM INFORMATION_SCHEMA.columns 
						WHERE TABLE_SCHEMA = DATABASE() 
						AND TABLE_NAME = table_name_IN 
						AND COLUMN_NAME = field_name_IN
						);
    
    IF (@isFieldThere = 0) THEN

        SET @ddl = CONCAT('ALTER TABLE ', table_name_IN);
        SET @ddl = CONCAT(@ddl, ' ', 'ADD COLUMN') ;
        SET @ddl = CONCAT(@ddl, ' ', field_name_IN);
        SET @ddl = CONCAT(@ddl, ' ', field_definition_IN);

        PREPARE stmt FROM @ddl;
        
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
	
		SELECT concat('Column added to table: ', table_name_IN) AS  Message;
    ELSE
		SELECT concat('Column is already existed in table: ', table_name_IN) AS  Message;
    END IF;
	
END