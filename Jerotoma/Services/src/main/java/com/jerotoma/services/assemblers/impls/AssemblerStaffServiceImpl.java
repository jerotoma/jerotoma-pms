package com.jerotoma.services.assemblers.impls;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.viewobjects.StaffVO;
import com.jerotoma.database.assemblers.dao.AssemblerStaffDao;
import com.jerotoma.services.assemblers.AssemblerStaffService;

@Service
public class AssemblerStaffServiceImpl  implements AssemblerStaffService{

	@Autowired AssemblerStaffDao assemblerStaffDao;
	
	@Override
	public StaffVO findObject(Integer primaryKey) throws SQLException {
		return assemblerStaffDao.findObject(primaryKey);
	}

	@Override
	public StaffVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		return assemblerStaffDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public List<StaffVO> loadList(QueryParam queryParam) throws SQLException {
		return assemblerStaffDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return assemblerStaffDao.loadMapList(queryParam);
	}

	@Override
	public Long countObject() throws SQLException {
		return assemblerStaffDao.countObject();
	}

	@Override
	public List<StaffVO> search(QueryParam queryParam) throws SQLException {
		return assemblerStaffDao.search(queryParam);
	}

}
