package com.jerotoma.database.assemblers.dao.settings.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.SystemConfigConstant;
import com.jerotoma.common.constants.UserPreferenceConstant;
import com.jerotoma.common.models.config.SystemConfig;
import com.jerotoma.common.viewobjects.AppSetttingVO;
import com.jerotoma.common.viewobjects.TeacherVO;
import com.jerotoma.common.viewobjects.UserPreferenceVO;
import com.jerotoma.database.assemblers.dao.AssemblerTeacherDao;
import com.jerotoma.database.assemblers.dao.settings.AppSettingAssemblerDao;
import com.jerotoma.database.dao.configs.SystemConfigDao;
import com.jerotoma.database.dao.configs.UserPreferenceDao;

@Repository
public class AppSettingAssemblerDaoImpl extends JdbcDaoSupport implements AppSettingAssemblerDao {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired DataSource dataSource;
	@Autowired SystemConfigDao systemConfigDao;
	@Autowired UserPreferenceDao userPreferenceDao;
	@Autowired AssemblerTeacherDao assemblerTeacherDao;	
	
	Map<String, Object> map;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		this.jdbcTemplate = getJdbcTemplate();
	}
 

	@Override
	public AppSetttingVO findObject(Integer primaryKey) throws SQLException {
		
		return null;
	}

	@Override
	public AppSetttingVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		
		return null;
	}

	@Override
	public List<AppSetttingVO> loadList(QueryParam queryParam) throws SQLException {
		
		return null;
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		
		return null;
	}
	
	public class AppSetttingResultProcessor implements RowMapper<AppSetttingVO>{
		@Override
		public AppSetttingVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return mapAppSetttingVOResult(rs);
		}		
	}
	
	public class UserReferenceResultProcessor implements RowMapper<UserPreferenceVO>{
		@Override
		public UserPreferenceVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return mapUserPreferenceResult(rs);
		}

		private UserPreferenceVO mapUserPreferenceResult(ResultSet rs) throws SQLException {			
			return new UserPreferenceVO(rs, loadTeacher(rs.getInt(UserPreferenceConstant.USER_ID)));
		}
		
		private TeacherVO loadTeacher(Integer teacherId) throws SQLException {
			return assemblerTeacherDao.findObject(teacherId);
		}
	}

	public class AppSetttingSingleResultProcessor implements ResultSetExtractor<AppSetttingVO>{
		@Override
		public AppSetttingVO extractData(ResultSet rs) throws SQLException, DataAccessException {
			AppSetttingVO appSettting = null;
			if(rs.next()) {
				appSettting = mapAppSetttingVOResult(rs);				
			}
			return appSettting;
		}				
	}
	
	public class LongResultProcessor implements ResultSetExtractor<Long>{
		@Override
		public Long extractData(ResultSet rs) throws SQLException, DataAccessException {
			Long l = null;
			if(rs.next()) {
			 l = rs.getLong(1);
			}
			return l;
		}				
	}
	
	private StringBuilder getBaseSelectQuery() {		
		return new StringBuilder("SELECT id, user_id, name, value FROM public.user_preferences ");
		
	}

	public AppSetttingVO mapAppSetttingVOResult(ResultSet rs) throws SQLException {
		AppSetttingVO appSetting = new AppSetttingVO();
		map = new HashMap<>();
		
		SystemConfig systemConfig  = systemConfigDao.findObjectUniqueKey(SystemConfigConstant.THEME.CURRENT_THEME);
		map.put(SystemConfigConstant.CURRENT_THEME, systemConfig.getValue());
		appSetting.setSystemConfigs(map);
		
		
		
		return appSetting;
	}
	
	public AppSetttingVO mapAppSetttingVOResult(ResultSet rs, Integer userID) throws SQLException {
		AppSetttingVO appSetting = new AppSetttingVO();
		map = new HashMap<>();
		
		SystemConfig systemConfig  = systemConfigDao.findObjectUniqueKey(SystemConfigConstant.THEME.CURRENT_THEME);
		map.put(SystemConfigConstant.CURRENT_THEME, systemConfig.getValue());
		appSetting.setSystemConfigs(map);
		
		
		
		return appSetting;
	}


	@Override
	public Long countObject() throws SQLException {
		StringBuilder queryBuilder = new StringBuilder("SELECT count(*) FROM public.user_preferences ");
		return this.jdbcTemplate.query(queryBuilder.toString(), new LongResultProcessor());
	}


	@Override
	public AppSetttingVO findObjectByUserID(Integer userID) throws SQLException {
		AppSetttingVO appSetting = new AppSetttingVO();
		map = new HashMap<>();	
		
		StringBuilder builder = getBaseSelectQuery().append(" WHERE user_id = ? ");		
		List<UserPreferenceVO> userPreferences =  this.jdbcTemplate.query(builder.toString(), new UserReferenceResultProcessor(), userID);
		
		SystemConfig systemConfig  = systemConfigDao.findObjectUniqueKey(SystemConfigConstant.THEME.CURRENT_THEME);
		map.put(SystemConfigConstant.CURRENT_THEME, systemConfig.getValue());
		appSetting.setSystemConfigs(map);
		appSetting.setUserPreferences(userPreferences);
		
		return appSetting;
	}


}
