package com.jerotoma.services.academic.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.AcademicConstants;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.models.academic.AcademicYear;
import com.jerotoma.database.dao.academic.AcademicYearDao;
import com.jerotoma.services.academic.AcademicYearService;
import com.jerotoma.services.utils.ServiceUtil;

@Service
@Transactional
public class AcademicYearServiceImpl implements AcademicYearService {
	
	@Autowired AcademicYearDao academicYearDao;
	
	@Override
	public AcademicYear findObject(Integer primaryKey) throws SQLException {
		return ServiceUtil.getEntity(academicYearDao.findById(primaryKey));
	}

	@Override
	public AcademicYear findObjectUniqueKey(String uniqueKey) throws SQLException {
		return academicYearDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public AcademicYear createObject(AcademicYear object) throws SQLException {
		return academicYearDao.save(object);
	}

	@Override
	public AcademicYear updateObject(AcademicYear object) throws SQLException {
		return academicYearDao.save(object);
	}

	@Override
	public Boolean deleteObject(AcademicYear object) throws SQLException {
		academicYearDao.delete(object);
		return true;
	}

	@Override
	public List<AcademicYear> loadList(QueryParam queryParam) throws SQLException {
		if (queryParam == null) {
			return academicYearDao.findAll();
		}		
		return academicYearDao.findAll(ServiceUtil.getPageable(queryParam)).toList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> map = new HashMap<>();		
		Page<AcademicYear> pageAcademicYear = academicYearDao.findAll(ServiceUtil.getPageable(queryParam));
		map.put(AcademicConstants.ACADEMIC_YEARS, pageAcademicYear.toList());
		map.put(SystemConstant.COUNT, pageAcademicYear.getTotalElements());
		map.put(SystemConstant.PAGE_COUNT, pageAcademicYear.getTotalPages());		
		return map;
	}

}
