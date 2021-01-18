package com.jerotoma.database.assemblers.dao.academic;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.viewobjects.ClassVO;
import com.jerotoma.common.viewobjects.StudentAcademicLevelClass;
import com.jerotoma.database.assemblers.AssemblerDao;

public interface AssemblerClassDao  extends AssemblerDao<ClassVO> {
	public List<ClassVO> loadClassesByStudentId(Integer studentId) throws SQLException;
	public List<ClassVO> loadClassesByAcademicYear(Integer academicYearId)throws SQLException;
	public List<ClassVO> loadStudentUnregisteredClassesByAcademicYear(Integer studentId, Integer academicLevelId, Integer academicYearId) throws SQLException;
	public List<ClassVO> loadStudentClassesByAcademicYear(Integer studentId, Integer academicYearId) throws SQLException;
	public ClassVO findClassByUniqueParams(Integer teacherId, Integer courseId, Integer academicYearId) throws SQLException;
	public List<ClassVO> loadClassesByParams(Integer programId, Integer academicLevelrId, Integer academicYearId) throws SQLException;
	public List<ClassVO> loadTeacherClassListByTeacherId(Integer teacherId) throws SQLException;
	public List<ClassVO> loadStudentClasses(Integer studentId, Integer academicLevelId, Integer academicYearId) throws SQLException;
	public List<StudentAcademicLevelClass> loadAllStudentAcademicLevelsClassList(Integer studentId)  throws SQLException;
	public List<ClassVO> loadClassesByTeacherClassParams(Integer teacherId, Integer programId, Integer academicLevelId,
			Integer academicYearId);

}
