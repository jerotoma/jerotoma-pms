package com.jerotoma.database.assemblers.dao.schedules;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
import com.jerotoma.common.constants.DatabaseConstant;
import com.jerotoma.common.constants.WorkDayConstant;
import com.jerotoma.common.viewobjects.WorkDayVO;

@Repository
public class AssemblerWorkDayDaoImpl  extends JdbcDaoSupport implements AssemblerWorkDayDao {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired DataSource dataSource;
		
	Map<String, Object> map;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		this.jdbcTemplate = getJdbcTemplate();
	}

	@Override
	public WorkDayVO findObject(Integer primaryKey) throws SQLException {
		return this.jdbcTemplate.query(getBaseSelectQuery().append(" WHERE id = ? ").toString(), new WorkDaySingleResultProcessor(), primaryKey);
	}

	@Override
	public WorkDayVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		return this.jdbcTemplate.query(getBaseSelectQuery().append(" WHERE id = ? ").toString(), new WorkDaySingleResultProcessor(), uniqueKey);
	}

	@Override
	public List<WorkDayVO> loadList() throws SQLException {
		return this.jdbcTemplate.query(getBaseSelectQuery().toString(), new WorkDayResultProcessor());
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		List<WorkDayVO> workDays = new ArrayList<>();
		workDays = this.jdbcTemplate.query(getBaseSelectQuery().toString(), new WorkDayResultProcessor());		
		map.put(WorkDayConstant.WORK_DAYS, workDays);		
		return map;
	}

	@Override
	public Long countObject() throws SQLException {
		return this.jdbcTemplate.query("SELECT count(*) FROM " + DatabaseConstant.TABLES.WORK_DAYS, new LongResultProcessor());
	}
	public class WorkDayResultProcessor implements RowMapper<WorkDayVO>{
		@Override
		public WorkDayVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			WorkDayVO workDay = mapWorkDayResult(rs);			
			return workDay;
		}		
	}
	
	public class WorkDaySingleResultProcessor implements ResultSetExtractor<WorkDayVO>{
		@Override
		public WorkDayVO extractData(ResultSet rs) throws SQLException, DataAccessException {
			WorkDayVO workDay = null;
			if(rs.next()) {
				workDay = mapWorkDayResult(rs);
			}
			return workDay;
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
	
	private WorkDayVO mapWorkDayResult(ResultSet rs) throws SQLException {			
		return new WorkDayVO(rs);
	}
	
	private StringBuilder getBaseSelectQuery() {
		return new StringBuilder("SELECT id, day_id AS dayId, created_on AS createdOn, updated_on AS updatedOn FROM ").append(DatabaseConstant.TABLES.WORK_DAYS).append("  ");
	}

	@Override
	public List<WorkDayVO> findAllWorkDays() throws SQLException {
		return this.jdbcTemplate.query(getBaseSelectQuery().toString(), new WorkDayResultProcessor());
	}

}
