package com.jerotoma.services.assemblers.academic;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.viewobjects.ClassVO;
import com.jerotoma.common.viewobjects.StudentAcademicLevelVO;
import com.jerotoma.services.AssemblerService;

public interface AssemblerStudentAcademicLevelService extends AssemblerService<StudentAcademicLevelVO> {

	public List<StudentAcademicLevelVO> findStudentClassIdByParams(Integer studentId, Integer classId) throws SQLException;

	public List<StudentAcademicLevelVO> findStudentAcademicLevelsByStudentId(Integer studentId) throws SQLException;

	public List<ClassVO> findStudentClassesByStudentIdAndAndAcademicLevelID(Integer studentId, Integer academicLevelId) throws SQLException;

	public List<ClassVO> findTeacherClassesByTeacherId(Integer id) throws SQLException;	
}
