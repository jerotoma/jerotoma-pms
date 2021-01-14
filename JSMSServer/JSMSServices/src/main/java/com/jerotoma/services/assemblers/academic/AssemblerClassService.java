package com.jerotoma.services.assemblers.academic;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.viewobjects.ClassVO;
import com.jerotoma.common.viewobjects.StudentAcademicLevelClass;
import com.jerotoma.services.AssemblerService;

public interface AssemblerClassService  extends AssemblerService<ClassVO> {

	public List<ClassVO> loadClassesByAcademicYear(Integer academicYearId) throws SQLException;
	public List<ClassVO> loadStudentUnregisteredClassesByAcademicYear(Integer studentId, Integer academicLevelrId, Integer academicYearId) throws SQLException;
	public List<ClassVO> loadStudentClassesByAcademicYear(Integer studentId, Integer academicYearId) throws SQLException;
	public List<ClassVO> loadStudentClasses(Integer studentId, Integer academicLevelId, Integer academicYearId) throws SQLException;
	public ClassVO findClassByUniqueParams(Integer teacherId, Integer courseId, Integer academicYearId) throws SQLException;
	public List<ClassVO> loadClassesByParams(Integer programId, Integer academicLevelrId, Integer academicYearId) throws SQLException;
	public List<ClassVO> loadTeacherClassList(Integer userId) throws SQLException;
	
	public List<StudentAcademicLevelClass> loadAllStudentAcademicLevelsClassList(Integer userId) throws SQLException;
}
