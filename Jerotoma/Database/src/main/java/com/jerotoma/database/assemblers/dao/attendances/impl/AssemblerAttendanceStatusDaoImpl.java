package com.jerotoma.database.assemblers.dao.attendances.impl;

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
import com.jerotoma.common.constants.AttendanceConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.models.attendances.AttendanceStatus;
import com.jerotoma.database.assemblers.dao.attendances.AssemblerAttendanceStatusDao;
import com.jerotoma.database.dao.DaoUtil;

@Repository
public class AssemblerAttendanceStatusDaoImpl extends JdbcDaoSupport implements AssemblerAttendanceStatusDao {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired DataSource dataSource;
	Map<String, Object> map;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		this.jdbcTemplate = getJdbcTemplate();
	}
	
	@Override
	public AttendanceStatus findObject(Integer primaryKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE id = ? ").toString();
		return this.jdbcTemplate.query(query, new AttendanceStatusSingleResultProcessor(), primaryKey);
	}
	
	@Override
	public AttendanceStatus findObjectUniqueKey(String uniqueKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE name = ? ").toString();
		return this.jdbcTemplate.query(query, new AttendanceStatusSingleResultProcessor(), uniqueKey);
	}
	
	@Override
	public List<AttendanceStatus> loadList() throws SQLException {
		StringBuilder builder = getBaseSelectQuery();		
		return this.jdbcTemplate.query(builder.toString(), new AttendanceStatusResultProcessor());
	}
	
	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		
		map = new HashMap<>();
		StringBuilder builder = getBaseSelectQuery();
		builder.append(DaoUtil.getOrderBy(queryParam.getFieldName(), queryParam.getOrderby()))
		.append(" ")
		.append("limit ? offset ?");
	
		Long countResults = countObject();
		int pageCount = DaoUtil.getPageCount(queryParam.getPageSize(), countResults);
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		Object[] paramList = new Object[] {limit, offset};
		
		List<AttendanceStatus> attendanceStatuses = this.jdbcTemplate.query(builder.toString(), new AttendanceStatusResultProcessor(), paramList);
		map.put(AttendanceConstant.ATTENDANCE_STATUSES, attendanceStatuses);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		
		return map;
	}
	
	public class AttendanceStatusResultProcessor implements RowMapper<AttendanceStatus>{
		@Override
		public AttendanceStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
			AttendanceStatus attendanceStatus = new AttendanceStatus(rs);
					
			return attendanceStatus;
		}		
	}
	
	public class AttendanceStatusSingleResultProcessor implements ResultSetExtractor<AttendanceStatus>{
		@Override
		public AttendanceStatus extractData(ResultSet rs) throws SQLException, DataAccessException {
			AttendanceStatus attendanceStatus = null;
			if(rs.next()) {
				attendanceStatus = new AttendanceStatus(rs);			
			}
			return attendanceStatus;
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
		return new StringBuilder("SELECT id, name, description, added_by AS addedBy, created_on AS createdOn, updated_on AS updatedOn FROM public.attendance_statuses ");		
	}
	
	@Override
	public Long countObject() throws SQLException {
		StringBuilder queryBuilder = new StringBuilder("SELECT count(*) FROM public.attendance_statuses ");
		return this.jdbcTemplate.query(queryBuilder.toString(), new LongResultProcessor());
	}
	
	@Override
	public List<AttendanceStatus> loadAllList() throws SQLException {		
		return this.jdbcTemplate.query(getBaseSelectQuery().toString(), new AttendanceStatusResultProcessor());
	}
}
