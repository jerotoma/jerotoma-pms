package com.jerotoma.database.assemblers;

import java.sql.SQLException;

public interface AssemblerSequenceGeneratorDao {
	Long getNextNumber() throws SQLException;
}
