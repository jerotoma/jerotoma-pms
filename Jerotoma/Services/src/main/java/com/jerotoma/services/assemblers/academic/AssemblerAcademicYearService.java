package com.jerotoma.services.assemblers.academic;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.viewobjects.AcademicYearVO;
import com.jerotoma.services.AssemblerService;

public interface AssemblerAcademicYearService extends AssemblerService<AcademicYearVO> {
	public List<AcademicYearVO> loadAllList() throws SQLException;
	public AcademicYearVO getCurrentAcademicYear();
	
	public List<AcademicYearVO> findAcademicYears(Integer studentId, Integer academicLevelId) throws SQLException;
}
