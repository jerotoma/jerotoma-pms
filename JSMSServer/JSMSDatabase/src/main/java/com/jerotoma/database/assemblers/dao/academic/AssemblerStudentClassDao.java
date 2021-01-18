package com.jerotoma.database.assemblers.dao.academic;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.viewobjects.StudentClassVO;
import com.jerotoma.database.assemblers.AssemblerDao;

public interface AssemblerStudentClassDao  extends AssemblerDao<StudentClassVO> {

	public List<StudentClassVO> findStudentClassesByClassId(Integer classId) throws SQLException;
	boolean doesStudentClassRecordExist(Integer classId, Integer studentAcademicLevelId);

}
