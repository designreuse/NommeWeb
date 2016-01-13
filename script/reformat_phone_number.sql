UPDATE nomme.tbl_consumers
SET phone = REPLACE(REPLACE(REPLACE(REPLACE(phone, '(', ''), ')', '') , '-', '') , ' ', '') 
WHERE id > 0;