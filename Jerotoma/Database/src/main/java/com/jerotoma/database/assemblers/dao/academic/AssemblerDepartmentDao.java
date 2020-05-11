package com.jerotoma.database.assemblers.dao.academic;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.viewobjects.DepartmentVO;
import com.jerotoma.database.assemblers.AssemblerDao;

public interface AssemblerDepartmentDao extends AssemblerDao<DepartmentVO> {
	public List<DepartmentVO> getAllDepartment() throws SQLException;
	public void setAssemblerCourseDao(AssemblerCourseDao assemblerCourseDao);

}
