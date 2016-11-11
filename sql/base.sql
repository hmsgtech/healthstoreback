CREATE DATABASE healthstore DEFAULT CHARACTER SET utf8;
GRANT ALL ON healthstore.* TO healthstore@'%' IDENTIFIED BY 'healthstore';

--创建定时任务及存储过程

DELIMITER $$

-- SET GLOBAL event_scheduler = ON$$     -- required for event to execute but not create    
/*更新商品评分*/
CREATE
    /*[DEFINER = { user | CURRENT_USER }]*/
    PROCEDURE `healthstore`.`updateGoodsRank`()
    /*LANGUAGE SQL
    | [NOT] DETERMINISTIC
    | { CONTAINS SQL | NO SQL | READS SQL DATA | MODIFIES SQL DATA }
    | SQL SECURITY { DEFINER | INVOKER }
    | COMMENT 'string'*/
    BEGIN
	 DECLARE Done INT DEFAULT 0;
	 DECLARE rank INT;
	 DECLARE goodsid INT;
	 DECLARE goodsIds CURSOR FOR SELECT id FROM goods;
	 DECLARE CONTINUE HANDLER FOR NOT FOUND SET Done = 1;
	 OPEN goodsIds;
	 REPEAT
		FETCH goodsIds INTO goodsid;
		SELECT AVG(goods_rank) INTO rank FROM goods_rank WHERE goods_id=goodsid;
		UPDATE goods SET goods_rank=rank WHERE id=goodsid;
	 UNTIL Done END REPEAT;
	 CLOSE goodsIds;
	 UPDATE goods SET goods_rank=0 WHERE goods_rank IS NULL;
    END$$

/*更新商品评分--每日0点执行*/
CREATE EVENT dailyJob

ON SCHEDULE
	EVERY 1 DAY STARTS '2016-09-2 00:01:00'
DO
	BEGIN
	    CALL updateGoodsRank();
	END$$

DELIMITER ;