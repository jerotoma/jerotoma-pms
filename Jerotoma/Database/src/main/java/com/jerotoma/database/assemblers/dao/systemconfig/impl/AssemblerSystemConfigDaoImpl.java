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
import com.jerotoma.common.constants.SystemConfigConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.viewobjects.SystemConfigVO;
import com.jerotoma.database.assemblers.dao.systemconfig.AssemblerSystemConfigDao;
import com.jerotoma.database.dao.DaoUtil;

@Repository
public class AssemblerSystemConfigDaoImpl extends JdbcDaoSupport implements AssemblerSystemConfigDao {
	
	private JdbcTemplate jdbcTemplate;
	@Autowired DataSource dataSource;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		this.jdbcTemplate = getJdbcTemplate();
	}

	@Override
	public SystemConfigVO findObject(Integer primaryKey) throws SQLException {
		String query = getBaseSelectQuery().append(" WHERE sc.id = ? ").toString();
		return this.jdbcTemplate.query(query, new SystemConfigSingleResultProcessor(), primaryKey);
	}

	@Override
	public SystemConfigVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		String query = getBaseSelectQuery().append(" WHERE sc.name = ? ").toString();
		return this.jdbcTemplate.query(query, new SystemConfigSingleResultProcessor(), uniqueKey);
	}

	@Override
	public List<SystemConfigVO> loadList(QueryParam queryParam) throws SQLException {
		StringBuilder builder = getBaseSelectQuery();
				builder.append(DaoUtil.getOrderBy(queryParam.getFieldName(), queryParam.getOrderby()))
				.append(" ")
				.append("limit ? offset ?");

		Long countResults = countObject();
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		Object[] paramList = new Object[] {limit, offset};
		
		return this.jdbcTemplate.query(builder.toString(), new SystemConfigResultProcessor(), paramList);
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
		
		List<SystemConfigVO> classes = this.jdbcTemplate.query(builder.toString(), new SystemConfigResultProcessor(), paramList);
		map.put(SystemConfigConstant.SYSTEM_CONFINGS, classes);
		map.put(SystemConfigConstant.SYSTEM_CONFING_COUNT, countResults);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		
		return map;
	}
	
	public class SystemConfigResultProcessor implements RowMapper<SystemConfigVO>{
		@Override
		public SystemConfigVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return mapSystemConfigResult(rs);
		}		
	}
	
	public class SystemConfigSingleResultProcessor implements ResultSetExtractor<SystemConfigVO>{
		@Override
		public SystemConfigVO extractData(ResultSet rs) throws SQLException, DataAccessException {
			SystemConfigVO systemConfig = null;
			if(rs.next()) {
				systemConfig = mapSystemConfigResult(rs);			
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
		return new StringBuilder("SELECT sc.id, sc.name, sc.value FROM public.system_configs sc ");		
	}
	
	@Override
	public Long countObject() throws SQLException {
		StringBuilder queryBuilder = new StringBuilder("SELECT count(*) FROM public.system_configs ");
		return this.jdbcTemplate.query(queryBuilder.toString(), new LongResultProcessor());
	}
	
	
	public SystemConfigVO mapSystemConfigResult(ResultSet rs) throws SQLException {		
		return new SystemConfigVO(rs);
	}

}
