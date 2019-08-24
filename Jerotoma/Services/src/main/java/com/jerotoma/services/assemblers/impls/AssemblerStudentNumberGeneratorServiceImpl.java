package com.jerotoma.services.assemblers.impls;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.database.assemblers.AssemblerStudentNumberGeneratorDao;
import com.jerotoma.services.assemblers.AssemblerStudentNumberGeneratorService;

@Service
public class AssemblerStudentNumberGeneratorServiceImpl implements AssemblerStudentNumberGeneratorService {
	
	@Autowired AssemblerStudentNumberGeneratorDao studentNumberGeneratorDao;

	@Override
	public Long getNextNumber() throws SQLException {
		return studentNumberGeneratorDao.getNextNumber();
	}

}
