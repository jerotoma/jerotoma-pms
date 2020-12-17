package com.jerotoma.database.assemblers.dao.systemconfig.impl;

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
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.constants.UserPreferenceConstant;
import com.jerotoma.common.viewobjects.UserPreferenceVO;
import com.jerotoma.database.assemblers.dao.systemconfig.AssemblerUserPreferenceDao;
import com.jerotoma.database.dao.DaoUtil;

@Repository
public class AssemblerUserPreferenceDaoImpl extends JdbcDaoSupport implements AssemblerUserPreferenceDao {
	
	private JdbcTemplate jdbcTemplate;
	@Autowired DataSource dataSource;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		this.jdbcTemplate = getJdbcTemplate();
	}

	@Override
	public UserPreferenceVO findObject(Integer primaryKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE up.id = ? ").toString();
		return this.jdbcTemplate.query(query, new UserPreferenceSingleResultProcessor(), primaryKey);
	}

	@Override
	public UserPreferenceVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE up.name = ? ").toString();
		return this.jdbcTemplate.query(query, new UserPreferenceSingleResultProcessor(), uniqueKey);
	}

	@Override
	public List<UserPreferenceVO> loadList() throws SQLException {
		StringBuilder builder = getBaseSelectQuery();				
		return this.jdbcTemplate.query(builder.toString(), new UserPreferenceResultProcessor());
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		
		Map<String, Object> map = new HashMap<>();
		StringBuilder builder = getBaseSelectQuery();
		builder.append(DaoUtil.getOrderBy(queryParam.getFieldName(), queryParam.getOrderby()))
		.append(" ")
		.append("limit ? offset ?");

		Long countResults = countObject();
		int pageCount = DaoUtil.getPageCount(queryParam.getPageSize(), countResults);
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		Object[] paramList = new Object[] {limit, offset};
		
		List<UserPreferenceVO> userPreferences = this.jdbcTemplate.query(builder.toString(), new UserPreferenceResultProcessor(), paramList);
		map.put(UserPreferenceConstant.USER_PREFERENCES, userPreferences);
		map.put(UserPreferenceConstant.COUNT, countResults);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		
		return map;
	}
	
	public class UserPreferenceResultProcessor implements RowMapper<UserPreferenceVO>{
		@Override
		public UserPreferenceVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return mapUserPreferenceResult(rs);
		}		
	}
	
	public class UserPreferenceSingleResultProcessor implements ResultSetExtractor<UserPreferenceVO>{
		@Override
		public UserPreferenceVO extractData(ResultSet rs) throws SQLException, DataAccessException {
			UserPreferenceVO systemConfig = null;
			if(rs.next()) {
				systemConfig = mapUserPreferenceResult(rs);			
			}
			return systemConfig;
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
		return new StringBuilder("SELECT up.id, up.user_id, up.name, up.value FROM public.user_preferences up ");		
	}
	
	@Override
	public Long countObject() throws SQLException {
		StringBuilder queryBuilder = new StringBuilder("SELECT count(*) FROM public.user_preferences ");
		return this.jdbcTemplate.query(queryBuilder.toString(), new LongResultProcessor());
	}
	
	
	public UserPreferenceVO mapUserPreferenceResult(ResultSet rs) throws SQLException {		
		return new UserPreferenceVO(rs);
	}

	@Override
	public List<UserPreferenceVO> loadUserPreferenceByUserId(Integer userId) throws SQLException {
		StringBuilder builder = getBaseSelectQuery().append(" WHERE up.user_id = ? ");
		return this.jdbcTemplate.query(builder.toString(), new UserPreferenceResultProcessor(), userId);
	}

	@Override
	public UserPreferenceVO findUserPreferenceByKeyAndUserId(Integer userId, String userPreferenceKey)
			throws SQLException {
		StringBuilder builder = getBaseSelectQuery().append(" WHERE up.user_id = ? AND up.name = ? ");
		return this.jdbcTemplate.query(builder.toString(), new UserPreferenceSingleResultProcessor(), userId, userPreferenceKey);
	}
}
