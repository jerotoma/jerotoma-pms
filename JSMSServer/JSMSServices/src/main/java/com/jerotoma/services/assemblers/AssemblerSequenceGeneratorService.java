package com.jerotoma.services.assemblers;

import java.sql.SQLException;

public interface AssemblerSequenceGeneratorService {
	public Long getNextNumber() throws SQLException;
}
