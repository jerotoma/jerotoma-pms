package com.jerotoma.database.assemblers.dao;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.viewobjects.ParentVO;
import com.jerotoma.database.assemblers.AssemblerDao;

public interface AssemblerParentDao extends AssemblerDao<ParentVO> {
	public List<ParentVO> search(QueryParam queryParam) throws SQLException;
	public List<ParentVO> findParentsByStudentId(Integer studentId) throws SQLException;

}
