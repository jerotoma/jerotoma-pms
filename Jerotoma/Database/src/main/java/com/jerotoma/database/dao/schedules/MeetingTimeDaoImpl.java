package com.jerotoma.database.dao.schedules;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.schedules.MeetingTime;

@Transactional
@Repository
public class MeetingTimeDaoImpl implements MeetingTimeDao {
	
	@PersistenceContext 
	private EntityManager entityManager;	
	
	
	@Override
	public MeetingTime findObject(Integer primaryKey) throws SQLException {
		return entityManager.find(MeetingTime.class, primaryKey);	
	}

	@Override
	public MeetingTime findObjectUniqueKey(String uniqueKey) throws SQLException {
		return findObject(Integer.valueOf(uniqueKey));
	}

	@Override
	public MeetingTime createObject(MeetingTime object) throws SQLException {
		entityManager.persist(object);
		return findObject(object.getId());
	}

	@Override
	public MeetingTime updateObject(MeetingTime object) throws SQLException {
		return entityManager.merge(object);
	}

	@Override
	public Boolean deleteObject(MeetingTime object) throws SQLException {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));		
		return true;
	}

	@Override
	public List<MeetingTime> loadList(QueryParam queryParam) throws SQLException {
		return entityManager.createQuery("FROM MeetingTime ", MeetingTime.class).getResultList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return null;
	}

	@Override
	public Long countObject() throws SQLException {
		return null;
	}
		
	public class MeetingTimeResultProcessor implements RowMapper<MeetingTime>{
		@Override
		public MeetingTime mapRow(ResultSet rs, int rowNum) throws SQLException {
			MeetingTime meetingTime = mapMeetingTimeResult(rs);			
			return meetingTime;
		}		
	}
	
	public class MeetingTimeSingleResultProcessor implements ResultSetExtractor<MeetingTime>{
		@Override
		public MeetingTime extractData(ResultSet rs) throws SQLException, DataAccessException {
			MeetingTime meetingTime = null;
			if(rs.next()) {
				meetingTime = mapMeetingTimeResult(rs);
			}
			return meetingTime;
		}				
	}
	private MeetingTime mapMeetingTimeResult(ResultSet rs) throws SQLException {			
		return new MeetingTime(rs);
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
}
