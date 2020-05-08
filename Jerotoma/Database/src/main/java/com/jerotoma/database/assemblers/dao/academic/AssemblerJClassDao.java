package com.jerotoma.database.assemblers.dao.academic;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.viewobjects.ClassVO;
import com.jerotoma.database.assemblers.AssemblerDao;

public interface AssemblerJClassDao  extends AssemblerDao<ClassVO> {
	public List<ClassVO> loadJClassesByStudentId(Integer studentId) throws SQLException;
	public List<ClassVO> loadJClassesByAcademicYear(Integer academicYearId)throws SQLException;
	public List<ClassVO> loadStudentUnregisteredJClassesByAcademicYear(Integer academicYearId, Integer studentId) throws SQLException;
	public List<ClassVO> loadStudentJClassesByAcademicYear(Integer studentId, Integer academicYearId) throws SQLException;

}
