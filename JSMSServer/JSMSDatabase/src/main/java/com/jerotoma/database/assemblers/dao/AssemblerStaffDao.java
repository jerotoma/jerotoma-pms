package com.jerotoma.database.assemblers.dao;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.viewobjects.StaffVO;
import com.jerotoma.database.assemblers.AssemblerDao;

public interface AssemblerStaffDao extends AssemblerDao<StaffVO>{

	public List<StaffVO> search(QueryParam queryParam) throws SQLException;

}
