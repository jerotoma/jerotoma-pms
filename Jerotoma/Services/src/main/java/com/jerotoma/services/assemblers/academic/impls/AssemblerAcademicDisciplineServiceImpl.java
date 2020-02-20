package com.jerotoma.services.assemblers.academic.impls;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.viewobjects.AcademicDisciplineVO;
import com.jerotoma.database.assemblers.dao.AssemblerAcademicDisciplineDao;
import com.jerotoma.services.assemblers.academic.AssemblerAcademicDisciplineService;


@Service
public class AssemblerAcademicDisciplineServiceImpl implements AssemblerAcademicDisciplineService {
	
	@Autowired AssemblerAcademicDisciplineDao assemblerAcademicDisciplineDao;

	@Override
	public AcademicDisciplineVO findObject(Integer primaryKey) throws SQLException {
		return assemblerAcademicDisciplineDao.findObject(primaryKey);
	}

	@Override
	public AcademicDisciplineVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		return assemblerAcademicDisciplineDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public List<AcademicDisciplineVO> loadList(QueryParam queryParam) throws SQLException {
		return assemblerAcademicDisciplineDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return assemblerAcademicDisciplineDao.loadMapList(queryParam);
	}

	@Override
	public Long countObject() throws SQLException {
		return assemblerAcademicDisciplineDao.countObject();
	}

	@Override
	public List<AcademicDisciplineVO> findAcademicDisciplinesByCourseId(Integer courseId) throws SQLException {
		return assemblerAcademicDisciplineDao.findAcademicDisciplinesByCourseId(courseId);
	}

}
