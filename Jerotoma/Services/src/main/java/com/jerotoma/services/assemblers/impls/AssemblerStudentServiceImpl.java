package com.jerotoma.services.assemblers.impls;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.viewobjects.StudentVO;
import com.jerotoma.database.assemblers.dao.AssemblerStudentDao;
import com.jerotoma.services.assemblers.AssemblerStudentService;

@Service
public class AssemblerStudentServiceImpl  implements AssemblerStudentService{
	
	@Autowired AssemblerStudentDao assemblerStudentDao;

	@Override
	public StudentVO findObject(Integer primaryKey) throws SQLException {
		return assemblerStudentDao.findObject(primaryKey);
	}

	@Override
	public StudentVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		return assemblerStudentDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public List<StudentVO> loadList(QueryParam queryParam) throws SQLException {
		return assemblerStudentDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return assemblerStudentDao.loadMapList(queryParam);
	}

	@Override
	public Long countObject() throws SQLException {
		return assemblerStudentDao.countObject();
	}

	@Override
	public List<StudentVO> search(QueryParam queryParam)  throws SQLException{
		return assemblerStudentDao.search(queryParam);
	}

}
