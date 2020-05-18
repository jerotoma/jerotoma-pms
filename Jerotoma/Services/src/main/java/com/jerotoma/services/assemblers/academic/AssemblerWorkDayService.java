package com.jerotoma.services.assemblers.academic;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.viewobjects.WorkDayVO;
import com.jerotoma.services.AssemblerService;

public interface AssemblerWorkDayService extends AssemblerService<WorkDayVO> {
	public List<WorkDayVO> findAllWorkDays() throws SQLException;

}

