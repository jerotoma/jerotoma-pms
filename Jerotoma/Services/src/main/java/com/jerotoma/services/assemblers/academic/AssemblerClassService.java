package com.jerotoma.services.assemblers.academic;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.viewobjects.ClassVO;
import com.jerotoma.services.AssemblerService;

public interface AssemblerClassService  extends AssemblerService<ClassVO> {

	public List<ClassVO> loadJClassesByAcademicYear(Integer academicYearId) throws SQLException;
	public List<ClassVO> loadStudentUnregisteredJClassesByAcademicYear(Integer academicYearId, Integer studentId) throws SQLException;
	public List<ClassVO> loadStudentJClassesByAcademicYear(Integer studentId, Integer academicYearId) throws SQLException;

}
