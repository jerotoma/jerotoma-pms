package com.jerotoma.database.assemblers.dao.academic;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.viewobjects.JClassVO;
import com.jerotoma.database.assemblers.AssemblerDao;

public interface AssemblerJClassDao  extends AssemblerDao<JClassVO> {
	public List<JClassVO> loadJClassesByStudentId(Integer studentId) throws SQLException;
	public List<JClassVO> loadJClassesByAcademicYear(Integer academicYearId)throws SQLException;
	public List<JClassVO> loadStudentUnregisteredJClassesByAcademicYear(Integer academicYearId, Integer studentId) throws SQLException;

}
