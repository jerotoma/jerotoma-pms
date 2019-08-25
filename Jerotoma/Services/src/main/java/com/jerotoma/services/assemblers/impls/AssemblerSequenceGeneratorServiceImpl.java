package com.jerotoma.services.assemblers.impls;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.database.assemblers.AssemblerSequenceGeneratorDao;
import com.jerotoma.services.assemblers.AssemblerSequenceGeneratorService;

@Service
public class AssemblerSequenceGeneratorServiceImpl implements AssemblerSequenceGeneratorService {
	
	@Autowired AssemblerSequenceGeneratorDao sequenceGeneratorDao;

	@Override
	public Long getNextNumber() throws SQLException {
		return sequenceGeneratorDao.getNextNumber();
	}

}
