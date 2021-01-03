package com.jerotoma.database.assemblers.dao.academic;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import com.jerotoma.common.viewobjects.AcademicLevelPrerequisiteVO;
import com.jerotoma.common.viewobjects.AcademicLevelVO;
import com.jerotoma.database.assemblers.AssemblerDao;

public interface AssemblerAcademicLevelDao extends AssemblerDao<AcademicLevelVO>{

	public List<AcademicLevelVO> getAllAcademicLevel() throws SQLException;
	public List<AcademicLevelVO> loadUnAddedAcademicLevelByProgram(Integer programId) throws SQLException;
	public List<AcademicLevelVO> loadAcademicLevelByProgram(Integer programId) throws SQLException;
	public List<AcademicLevelVO> loadAvailableAcademicLevelsByStudentId(Integer programId, Integer studentId) throws SQLException;
	public Set<AcademicLevelPrerequisiteVO> findAcademicLevelPrerequisitesByAcademicLevelId(Integer programId, Integer academicLevelId) throws SQLException;

}
