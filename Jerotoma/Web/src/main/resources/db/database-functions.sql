	/**************************************************************
	 * 															  *
	 * 															  *
	 * 			ADD DAY ENUM DATA TYPE					  		  *
	 * 															  *
	 *************************************************************/
	DO
	$BODY$
	BEGIN
	  IF NOT EXISTS (SELECT * FROM pg_type typ 
	  	INNER JOIN pg_namespace nsp ON nsp.oid = typ.typnamespace 
	  	WHERE nsp.nspname = current_schema() AND typ.typname = 'day') THEN
	    	CREATE TYPE DAY AS ENUM('1', '2', '3', '4', '5', '6', '7');
	  END IF;
	END;
	$BODY$
	LANGUAGE plpgsql;;

	 /**************************************************************
	 * 															  *
	 * 															  *
	 * 			ADD TABLE COLUMN RELATED TABLES					  *
	 * 															  *
	 *************************************************************/
	
	--RUN THIS SCRIPTS MUNUALLY
	
	/** 
	CREATE OR REPLACE function add_table_column_if_not_exists(tableName regclass, columnName  text, dataType regtype)
	RETURNS bool AS 
	$BODY$ 
		BEGIN 
		  	IF EXISTS (SELECT 1 FROM pg_attribute WHERE  attrelid = tableName AND attname = columnName AND NOT attisdropped) THEN		  	
		  		RETURN FALSE;
		  	ELSE
		  		EXECUTE format('ALTER TABLE %s ADD COLUMN %I %s', tableName, columnName, dataType);
		      	RETURN TRUE;
		   	END IF;	   
		END;
	$BODY$ 
	LANGUAGE plpgsql;; 
	**/
	
	 /**************************************************************
	 * 															  *
	 * 															  *
	 * 			ADD TABLE COLUMN RELATED TABLES					  *
	 * 															  *
	 *************************************************************/
	
	--RUN THIS SCRIPTS MUNUALLY
	
	/**
	CREATE OR REPLACE function rename_table_column_if_exists(tableName regclass, currentColumnName  TEXT, newColumnName TEXT)
	RETURNS bool AS 
	$BODY$ 
		BEGIN 
		  IF NOT EXISTS 
		  	(SELECT 1 FROM pg_attribute WHERE  attrelid = tableName AND attname = currentColumnName AND NOT attisdropped) 
		  THEN
		     RETURN FALSE;
		  ELSE
		     EXECUTE 'ALTER TABLE ' || tableName || ' RENAME COLUMN ' || currentColumnName || ' TO ' || newColumnName;
		     RETURN TRUE;
		  END IF;
		END;
	$BODY$ 
	LANGUAGE plpgsql;;  
	*/