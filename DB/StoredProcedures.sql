DELIMITER $$

CREATE PROCEDURE sp_get_all_items()
BEGIN
SELECT 
Vote_Item.item_id, 
Vote_Item.item_name, 
Vote_Item.item_created_time, 
Vote_Item.item_updated_time, 
Vote_Item.is_enable, 
COUNT(Vote_Record.item_id) AS vote_count
	FROM Vote_Item LEFT JOIN Vote_Record ON Vote_Item.item_id = Vote_Record.item_id GROUP BY Vote_Item.item_id;
END $$ 

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_create_item(
IN p_item_name VARCHAR(100) 
)
BEGIN
	INSERT INTO Vote_Item(item_name) values (p_item_name);
END $$ 

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_update_item(
IN p_item_id INT,
IN p_item_name VARCHAR(100)
)
BEGIN
    UPDATE Vote_Item SET item_name = p_item_name WHERE item_id = p_item_id;
END $$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_toggle_item (
    IN p_item_id INT
)
BEGIN
    UPDATE Vote_Item SET is_enable = NOT is_enable WHERE item_id = p_item_id;
END $$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_delete_item(
    IN p_item_id INT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
    END;

    START TRANSACTION;
        DELETE FROM Vote_Record WHERE item_id = p_item_id;
DELETE FROM Vote_Item WHERE item_id = p_item_id;
    COMMIT;
END $$

DELIMITER ;

DELIMITER $$ 

CREATE PROCEDURE sp_get_active_items() 
BEGIN
SELECT 
    Vote_Item.item_id,
    Vote_Item.item_name,
    COUNT(Vote_Record.item_id) AS vote_count
FROM Vote_Item
LEFT JOIN Vote_Record ON Vote_Record.item_id = Vote_Item.item_id
WHERE Vote_Item.is_enable = True
GROUP BY Vote_Item.item_id;
END $$ 

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_cast_votes(
    IN p_voter_id CHAR(36),
    IN p_voter_name VARCHAR(100),
    IN p_item_ids TEXT
)
BEGIN

    DECLARE v_item_id INT;
    DECLARE v_remaining TEXT;
    DECLARE v_pos INT;

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
    END;

    START TRANSACTION;
        INSERT INTO Voter_Info(voter_id, voter_name) VALUES (p_voter_id, p_voter_name);        

        SET v_remaining = p_item_ids;
        
loop_label: LOOP
    SET v_pos = LOCATE(',', v_remaining);
    
    IF v_pos = 0 THEN
        SET v_item_id = CAST(v_remaining AS UNSIGNED);
        INSERT INTO Vote_Record(voter_id, item_id) VALUES (p_voter_id, v_item_id);
        LEAVE loop_label;
    ELSE
        SET v_item_id = CAST(SUBSTRING(v_remaining, 1, v_pos - 1) AS UNSIGNED);
        INSERT INTO Vote_Record(voter_id, item_id) VALUES (p_voter_id, v_item_id);
        SET v_remaining = SUBSTRING(v_remaining, v_pos + 1);
    END IF;

END LOOP loop_label;

    COMMIT;
END $$

DELIMITER ;
