package com.jerotoma.services.assemblers.academic;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.viewobjects.AcademicLevelVO;
import com.jerotoma.services.AssemblerService;

public interface AssemblerAcademicLevelService extends AssemblerService<AcademicLevelVO> {
	public List<AcademicLevelVO> getAllAcademicLevels() throws SQLException;

	public List<AcademicLevelVO> loadUnAddedAcademicLevelByProgram(Integer programId) throws SQLException;

	public List<AcademicLevelVO> loadAcademicLevelByProgram(Integer programId) throws SQLException;

	public List<AcademicLevelVO> loadStudentUnregisteredAcademicLevels(Integer programId, Integer studentId) throws SQLException;

	public List<AcademicLevelVO> loadStudentRegisteredAcademicLevels(Integer programId, Integer studentId) throws SQLException;
}
