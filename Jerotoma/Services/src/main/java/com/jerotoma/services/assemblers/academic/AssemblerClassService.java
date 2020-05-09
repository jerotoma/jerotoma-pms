package com.jerotoma.services.assemblers.academic;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.viewobjects.ClassVO;
import com.jerotoma.services.AssemblerService;

public interface AssemblerClassService  extends AssemblerService<ClassVO> {

	public List<ClassVO> loadClassesByAcademicYear(Integer academicYearId) throws SQLException;
	public List<ClassVO> loadStudentUnregisteredClassesByAcademicYear(Integer academicYearId, Integer studentId) throws SQLException;
	public List<ClassVO> loadStudentClassesByAcademicYear(Integer studentId, Integer academicYearId) throws SQLException;

}
