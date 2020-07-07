package com.jerotoma.database.assemblers.dao.academic;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.viewobjects.ClassVO;
import com.jerotoma.database.assemblers.AssemblerDao;

public interface AssemblerJClassDao  extends AssemblerDao<ClassVO> {
	public List<ClassVO> loadJClassesByStudentId(Integer studentId) throws SQLException;
	public List<ClassVO> loadClassesByAcademicYear(Integer academicYearId)throws SQLException;
	public List<ClassVO> loadStudentUnregisteredClassesByAcademicYear(Integer academicYearId, Integer studentId, Integer academicLevelId) throws SQLException;
	public List<ClassVO> loadStudentClassesByAcademicYear(Integer studentId, Integer academicYearId) throws SQLException;
	public ClassVO findClassByUniqueParams(Integer teacherId, Integer courseId, Integer academicYearId) throws SQLException;;

}
