package com.jerotoma.services.assemblers.academic;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.viewobjects.StudentClassVO;
import com.jerotoma.services.AssemblerService;

public interface AssemblerStudentClassService extends AssemblerService<StudentClassVO> {

	public List<StudentClassVO> findStudentClassesByClassId(Integer classId) throws SQLException;
	boolean doesStudentClassRecordExist(Integer classId, Integer studentAcademicLevelId);

}
