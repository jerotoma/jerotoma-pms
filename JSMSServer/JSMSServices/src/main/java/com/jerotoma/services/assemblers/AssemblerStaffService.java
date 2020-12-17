package com.jerotoma.services.assemblers;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.viewobjects.StaffVO;
import com.jerotoma.services.AssemblerService;

public interface AssemblerStaffService extends AssemblerService<StaffVO>{

	public List<StaffVO> search(QueryParam queryParam) throws SQLException;

}
