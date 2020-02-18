package com.jerotoma.services.assemblers;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.viewobjects.ParentVO;
import com.jerotoma.common.viewobjects.StudentVO;
import com.jerotoma.services.AssemblerService;

public interface AssemblerParentService extends AssemblerService<ParentVO>{
	public List<ParentVO> search(QueryParam queryParam) throws SQLException;
	public List<StudentVO> loadStudentsByParentId(Integer parentId) throws SQLException;

}
