package com.jerotoma.database.assemblers.dao.academic;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.viewobjects.AcademicYearVO;
import com.jerotoma.database.assemblers.AssemblerDao;

public interface AssemblerAcademicYearDao extends AssemblerDao<AcademicYearVO>{
	public List<AcademicYearVO> loadAllList() throws SQLException;
	
}
