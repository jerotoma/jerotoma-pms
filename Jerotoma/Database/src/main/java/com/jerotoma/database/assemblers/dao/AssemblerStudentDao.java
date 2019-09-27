package com.jerotoma.database.assemblers.dao;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.viewobjects.StudentVO;
import com.jerotoma.database.assemblers.AssemblerDao;

public interface AssemblerStudentDao extends AssemblerDao<StudentVO>{

	List<StudentVO> loadStudentsByJClassID(Integer classId) throws SQLException;
	List<StudentVO> search(QueryParam queryParam) throws SQLException;

}
