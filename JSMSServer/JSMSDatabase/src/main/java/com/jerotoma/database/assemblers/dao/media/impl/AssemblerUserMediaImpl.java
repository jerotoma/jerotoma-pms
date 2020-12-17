package com.jerotoma.database.assemblers.dao.media.impl;

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
import com.jerotoma.common.constants.MediaConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.viewobjects.UserMediaVO;
import com.jerotoma.database.assemblers.dao.AssemblerParentDao;
import com.jerotoma.database.assemblers.dao.media.AssemblerUserMediaDao;
import com.jerotoma.database.dao.DaoUtil;

@Repository
public class AssemblerUserMediaImpl extends JdbcDaoSupport implements AssemblerUserMediaDao {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired DataSource dataSource;
		
	@Autowired AssemblerParentDao parentDao;
	
	Map<String, Object> map;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		this.jdbcTemplate = getJdbcTemplate();	
	}

	@Override
	public UserMediaVO findObject(Integer primaryKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE um.id = ? ").toString();
		return this.jdbcTemplate.query(query, new UserMediaSingleResultProcessor(), primaryKey);
	}

	@Override
	public UserMediaVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		throw new RuntimeException("findObjectUniqueKey has not been implemented yet");
	}

	@Override
	public List<UserMediaVO> loadList() throws SQLException {
		StringBuilder builder = getBaseSelectQuery();				
		return this.jdbcTemplate.query(builder.toString(), new UserMediaResultProcessor());
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		
		map = new HashMap<>();
		StringBuilder builder = getBaseSelectQuery();
		builder.append(DaoUtil.getOrderBy(queryParam.getFieldName(), queryParam.getOrderby(), "um"))
		.append(" ")
		.append("limit ? offset ?");

		Long countResults = countObject();
		int pageCount = DaoUtil.getPageCount(queryParam.getPageSize(), countResults);
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		Object[] paramList = new Object[] {limit, offset};
		
		List<UserMediaVO> userMediaList = this.jdbcTemplate.query(builder.toString(), new UserMediaResultProcessor(), paramList);
		map.put(MediaConstant.USER_MEDIA_LIST, userMediaList);
		map.put(MediaConstant.USER_MEDIA_COUNT, countResults);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		
		return map;
	}
	
	public class UserMediaResultProcessor implements RowMapper<UserMediaVO>{
		@Override
		public UserMediaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return mapUserMediaResult(rs);
		}		
	}
	
	public class UserMediaSingleResultProcessor implements ResultSetExtractor<UserMediaVO>{
		@Override
		public UserMediaVO extractData(ResultSet rs) throws SQLException, DataAccessException {
			UserMediaVO userMedia = null;
			if(rs.next()) {
				userMedia = mapUserMediaResult(rs);					
			}
			return userMedia;
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
		return new StringBuilder("SELECT um.id, um.media_id, um.user_id ").append(" FROM public.user_media um ");
		
	}

	public UserMediaVO mapUserMediaResult(ResultSet rs) throws SQLException {
		return new UserMediaVO(rs);
	}

	@Override
	public Long countObject() throws SQLException {
		StringBuilder queryBuilder = new StringBuilder("SELECT count(*) FROM public.user_media");
		return this.jdbcTemplate.query(queryBuilder.toString(), new LongResultProcessor());
	}

	@Override
	public boolean doesUserMediaExist(Integer mediaId, Integer userId) {
		String query = getBaseSelectQuery().append("WHERE um.media_id = ?  AND um.user_id = ? ").toString();
		UserMediaVO userMedia = this.jdbcTemplate.query(query, new UserMediaSingleResultProcessor(), mediaId, userId);		
		return userMedia != null;
	}
	
}
