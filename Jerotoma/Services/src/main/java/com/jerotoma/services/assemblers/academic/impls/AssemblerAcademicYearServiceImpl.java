package com.jerotoma.services.assemblers.academic.impls;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.SystemConfigConstant;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.models.config.SystemConfig;
import com.jerotoma.common.viewobjects.AcademicYearVO;
import com.jerotoma.database.assemblers.dao.academic.AssemblerAcademicYearDao;
import com.jerotoma.services.assemblers.academic.AssemblerAcademicYearService;
import com.jerotoma.services.configs.SystemConfigService;


@Service
public class AssemblerAcademicYearServiceImpl implements AssemblerAcademicYearService{
	
	@Autowired AssemblerAcademicYearDao assemblerAcademicYearDao;
	@Autowired SystemConfigService systemConfigService;
	
	@Override
	public AcademicYearVO findObject(Integer primaryKey) throws SQLException {
		return assemblerAcademicYearDao.findObject(primaryKey);
	}

	@Override
	public AcademicYearVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		return assemblerAcademicYearDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public List<AcademicYearVO> loadList() throws SQLException {
		return assemblerAcademicYearDao.loadList();
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

	@Override
	public AcademicYearVO getCurrentAcademicYear() {
		SystemConfig systemConfig = null;
		AcademicYearVO academicYear = null;
		try {
			systemConfig = systemConfigService.findObjectUniqueKey(SystemConfigConstant.GENERAL_CONFIG.CURRENT_ACADEMIC_YEAR.getDbName());			
		} catch (SQLException | EmptyResultDataAccessException e) {			
			if (e instanceof EmptyResultDataAccessException) {
				systemConfig = null;
			} else {
				throw new JDataAccessException(e.getMessage(), e);
			}		
		}
		
		if (systemConfig != null) {
			try {
				academicYear = assemblerAcademicYearDao.findObject(Integer.valueOf(systemConfig.getValue()));
			} catch (SQLException | NumberFormatException e) {
				academicYear = null;
				e.printStackTrace();
			}
		}
		return academicYear;
	}

}
