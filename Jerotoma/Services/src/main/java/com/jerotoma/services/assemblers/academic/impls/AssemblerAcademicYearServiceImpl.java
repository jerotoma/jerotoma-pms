package com.jerotoma.services.assemblers.academic.impls;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.viewobjects.AcademicYearVO;
import com.jerotoma.database.assemblers.dao.academic.AssemblerAcademicYearDao;
import com.jerotoma.services.assemblers.academic.AssemblerAcademicYearService;


@Service
public class AssemblerAcademicYearServiceImpl implements AssemblerAcademicYearService{
	
	@Autowired AssemblerAcademicYearDao assemblerAcademicYearDao;
	
	@Override
	public AcademicYearVO findObject(Integer primaryKey) throws SQLException {
		return assemblerAcademicYearDao.findObject(primaryKey);
	}

	@Override
	public AcademicYearVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		return assemblerAcademicYearDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public List<AcademicYearVO> loadList(QueryParam queryParam) throws SQLException {
		return assemblerAcademicYearDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return assemblerAcademicYearDao.loadMapList(queryParam);
	}

	@Override
	public Long countObject() throws SQLException {
		return assemblerAcademicYearDao.countObject();
	}

	@Override
	public List<AcademicYearVO> loadAllList() throws SQLException {
		
		return assemblerAcademicYearDao.loadAllList();
	}

}
