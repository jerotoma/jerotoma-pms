package com.jerotoma.database.assemblers.dao.schedules;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
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
import com.jerotoma.common.viewobjects.WorkDayVO;

@Repository
public class AssemblerMeetingTimeDaoImpl extends JdbcDaoSupport implements AssemblerMeetingTimeDao {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired DataSource dataSource;
	@Autowired AssemblerWorkDayDao assemblerWorkDayDao;
		
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
		MeetingTimeVO meetingTime = new MeetingTimeVO(rs);
		meetingTime.setWorkDay(loadWorkDay(meetingTime.getWorkDayId()));		
		return meetingTime;
	}
	
	private WorkDayVO loadWorkDay(Integer workDayId) throws SQLException {
		return assemblerWorkDayDao.findObject(workDayId);
	}

	private StringBuilder getBaseSelectQuery() {
		return new StringBuilder("SELECT id, work_day_id AS workDayId, time, start_time AS startTime, end_time AS endTime, created_on AS createdOn, updated_on AS updatedOn FROM ").append(DatabaseConstant.TABLES.MEETING_TIMES).append("  ");
	}

	@Override
	public List<MeetingTimeVO> findAllMeetingTimes() throws SQLException {
		return this.jdbcTemplate.query(getBaseSelectQuery().toString(), new MeetingTimeResultProcessor());
	}

	@Override
	public List<MeetingTimeVO> findAllMeetingTimesByWorkDay(Integer workDayId) throws SQLException {		
		return this.jdbcTemplate.query(getBaseSelectQuery().append(" WHERE work_day_id = ? ").toString(), new MeetingTimeResultProcessor(), workDayId);
	}

	@Override
	public List<MeetingTimeVO> findAllOverapsMeetingTimesByWorkDay(Integer workDayId, LocalTime startTime, LocalTime endTime) throws SQLException {
		
		String start = startTime.toString();
		String end = endTime.toString();
		StringBuilder query = getBaseSelectQuery()
				.append(" WHERE work_day_id = ? AND ((start_time <= ?::time without time zone AND end_time > ?::time without time zone ) OR (start_time >= ?::time without time zone AND end_time < ?::time without time zone ))");
		
		return this.jdbcTemplate.query(query.toString(), new MeetingTimeResultProcessor(), workDayId, start, start, start, end);
	}
}
