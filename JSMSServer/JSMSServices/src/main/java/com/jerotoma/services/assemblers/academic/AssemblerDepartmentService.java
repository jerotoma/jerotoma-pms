package com.jerotoma.services.assemblers.academic;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.viewobjects.DepartmentVO;
import com.jerotoma.services.AssemblerService;

public interface AssemblerDepartmentService extends AssemblerService<DepartmentVO> {
	public List<DepartmentVO> getAllDepartment() throws SQLException;

}
