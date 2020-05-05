package com.jerotoma.database.assemblers.dao.academic;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.viewobjects.StudentClassVO;
import com.jerotoma.database.assemblers.AssemblerDao;

public interface AssemblerStudentClassDao extends AssemblerDao<StudentClassVO> {

	public StudentClassVO findStudentClassByParams(Integer studentId, Integer classId) throws SQLException;
	public List<StudentClassVO> findStudentClassesByStudentId(Integer studentId) throws SQLException;

}
