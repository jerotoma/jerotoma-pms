package com.jerotoma.services.assemblers.academic.impls;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.viewobjects.StudentClassVO;
import com.jerotoma.database.assemblers.dao.academic.AssemblerStudentClassDao;
import com.jerotoma.services.assemblers.academic.AssemblerStudentClassService;

@Service
public class AssemblerStudentClassServiceImpl implements AssemblerStudentClassService {
	
	@Autowired AssemblerStudentClassDao assemblerStudentClassDao;
	

	@Override
	public StudentClassVO findObject(Integer primaryKey) throws SQLException {
		return assemblerStudentClassDao.findObject(primaryKey);
	}

	@Override
	public StudentClassVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		return assemblerStudentClassDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public List<StudentClassVO> loadList(QueryParam queryParam) throws SQLException {
		return assemblerStudentClassDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return assemblerStudentClassDao.loadMapList(queryParam);
	}

	@Override
	public Long countObject() throws SQLException {
		return assemblerStudentClassDao.countObject();
	}

	@Override
	public StudentClassVO findStudentClassIdByParams(Integer studentId, Integer classId) throws SQLException {
		return assemblerStudentClassDao.findStudentClassByParams(studentId, classId);
	}

	@Override
	public StudentClassVO findStudentClassByStudentId(Integer studentId) throws SQLException {
		return assemblerStudentClassDao.findStudentClassByStudentId(studentId);
	}

}
