package com.jerotoma.services.assemblers.academic;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.viewobjects.ClassVO;
import com.jerotoma.common.viewobjects.StudentClassVO;
import com.jerotoma.services.AssemblerService;

public interface AssemblerStudentClassService extends AssemblerService<StudentClassVO> {

	public StudentClassVO findStudentClassIdByParams(Integer studentId, Integer classId) throws SQLException;

	public StudentClassVO findStudentClassesByStudentId(Integer studentId) throws SQLException;

	public List<ClassVO> findStudentClassesByStudentIdAndAndAcademicLevelID(Integer studentId, Integer academicLevelId) throws SQLException;

	public List<ClassVO> findTeacherClassesByTeacherId(Integer id) throws SQLException;

}
