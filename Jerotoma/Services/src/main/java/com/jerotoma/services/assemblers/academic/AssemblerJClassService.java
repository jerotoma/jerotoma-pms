package com.jerotoma.services.assemblers.academic;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.viewobjects.JClassVO;
import com.jerotoma.services.AssemblerService;

public interface AssemblerJClassService  extends AssemblerService<JClassVO> {

	public List<JClassVO> loadJClassesByAcademicYear(Integer academicYearId) throws SQLException;
	public List<JClassVO> loadUnregisteredJClassesByStudent(Integer academicYearId, Integer studentId) throws SQLException;

}
