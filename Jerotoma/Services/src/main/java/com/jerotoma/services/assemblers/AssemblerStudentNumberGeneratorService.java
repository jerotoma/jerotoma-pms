package com.jerotoma.services.assemblers;

import java.sql.SQLException;

public interface AssemblerStudentNumberGeneratorService {
	public Long getNextNumber() throws SQLException;
}
