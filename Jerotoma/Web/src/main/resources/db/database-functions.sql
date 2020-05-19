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