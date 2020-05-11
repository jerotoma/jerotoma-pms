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
import com.jerotoma.common.constants.MeetingTimeConstant;
import com.jerotoma.common.viewobjects.MeetingTimeVO;

@Repository
public class AssemblerMeetingTimeDaoImpl extends JdbcDaoSupport implements AssemblerMeetingTimeDao {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired DataSource dataSource;
		
	Map<String, Object> map;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		this.jdbcTemplate = getJdbcTemplate();
	}

	@Override
	public MeetingTimeVO findObject(Integer primaryKey) throws SQLException {
		return this.jdbcTemplate.query(getBaseSelectQuery().append(" WHERE id = ? ").toString(), new MeetingTimeSingleResultProcessor(), primaryKey);
	}

	@Override
	public MeetingTimeVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		return this.jdbcTemplate.query(getBaseSelectQuery().append(" WHERE id = ? ").toString(), new MeetingTimeSingleResultProcessor(), uniqueKey);
	}

	@Override
	public List<MeetingTimeVO> loadList(QueryParam queryParam) throws SQLException {
		return this.jdbcTemplate.query(getBaseSelectQuery().toString(), new MeetingTimeResultProcessor());
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		
		Map<String, Object> map = new HashMap<>();
		List<MeetingTimeVO> meetingTimes = new ArrayList<>();
		meetingTimes = this.jdbcTemplate.query(getBaseSelectQuery().toString(), new MeetingTimeResultProcessor());		
		map.put(MeetingTimeConstant.MEETING_TIMES, meetingTimes);		
		return map;
	}

	@Override
	public Long countObject() throws SQLException {
		return this.jdbcTemplate.query("SELECT count(*) FROM " + DatabaseConstant.TABLES.MEETING_TIMES, new LongResultProcessor());
	}
	
	public class MeetingTimeResultProcessor implements RowMapper<MeetingTimeVO>{
		@Override
		public MeetingTimeVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			MeetingTimeVO meetingTime = mapMeetingTimeResult(rs);			
			return meetingTime;
		}		
	}
	
	public class MeetingTimeSingleResultProcessor implements ResultSetExtractor<MeetingTimeVO>{
		@Override
		public MeetingTimeVO extractData(ResultSet rs) throws SQLException, DataAccessException {
			MeetingTimeVO meetingTime = null;
			if(rs.next()) {
				meetingTime = mapMeetingTimeResult(rs);
			}
			return meetingTime;
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
	
	private MeetingTimeVO mapMeetingTimeResult(ResultSet rs) throws SQLException {			
		return new MeetingTimeVO(rs);
	}
	
	private StringBuilder getBaseSelectQuery() {
		return new StringBuilder("SELECT id, time, created_on, updated_on FROM ").append(DatabaseConstant.TABLES.MEETING_TIMES).append("  ");
	}

	@Override
	public List<MeetingTimeVO> findAllMeetingTimes() throws SQLException {
		return this.jdbcTemplate.query(getBaseSelectQuery().toString(), new MeetingTimeResultProcessor());
	}
}
