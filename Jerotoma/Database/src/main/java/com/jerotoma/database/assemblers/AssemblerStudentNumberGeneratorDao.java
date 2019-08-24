package com.jerotoma.database.assemblers;

import java.sql.SQLException;

public interface AssemblerStudentNumberGeneratorDao {
	Long getNextNumber() throws SQLException;
}
