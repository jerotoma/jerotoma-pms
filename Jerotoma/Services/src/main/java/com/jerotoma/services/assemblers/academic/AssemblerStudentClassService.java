package com.jerotoma.services.assemblers.academic;

import java.sql.SQLException;

import com.jerotoma.common.viewobjects.StudentClassVO;
import com.jerotoma.services.AssemblerService;

public interface AssemblerStudentClassService extends AssemblerService<StudentClassVO> {

	public StudentClassVO findStudentClassIdByParams(Integer studentId, Integer classId) throws SQLException;

	public StudentClassVO findStudentClassByStudentId(Integer studentId) throws SQLException;

}
