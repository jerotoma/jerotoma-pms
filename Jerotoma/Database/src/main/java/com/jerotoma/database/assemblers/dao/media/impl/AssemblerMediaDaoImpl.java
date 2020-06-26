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

import com.google.common.collect.Lists;
import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.MediaConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.viewobjects.MediaVO;
import com.jerotoma.database.assemblers.dao.media.AssemblerMediaDao;
import com.jerotoma.database.dao.DaoUtil;

@Repository
public class AssemblerMediaDaoImpl extends JdbcDaoSupport implements AssemblerMediaDao {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired DataSource dataSource;
	Map<String, Object> map;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		this.jdbcTemplate = getJdbcTemplate();
	}

	@Override
	public MediaVO findObject(Integer primaryKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE m.id = ? ").toString();
		return this.jdbcTemplate.query(query, new MediaSingleResultProcessor(), primaryKey);
	}

	@Override
	public MediaVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		throw new RuntimeException("findObjectUniqueKey has not been implemented yet");
	}

	@Override
	public List<MediaVO> loadList(QueryParam queryParam) throws SQLException {
		StringBuilder builder = getBaseSelectQuery();
				builder.append(DaoUtil.getOrderBy(queryParam.getFieldName(), queryParam.getOrderby()))
				.append(" ")
				.append("limit ? offset ?");

		Long countResults = countObject();
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		Object[] paramList = new Object[] {limit, offset};
		
		return this.jdbcTemplate.query(builder.toString(), new MediaResultProcessor(), paramList);
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
		
		List<MediaVO> mediaList = this.jdbcTemplate.query(builder.toString(), new MediaResultProcessor(), paramList);
		
		map.put(MediaConstant.MEDIA_CHUNKS, Lists.partition(mediaList, 2));
		map.put(MediaConstant.MEDIA_LIST, mediaList);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		
		return map;
	}
	
	public class MediaResultProcessor implements RowMapper<MediaVO>{
		@Override
		public MediaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new MediaVO(rs);
		}		
	}
	
	public class MediaSingleResultProcessor implements ResultSetExtractor<MediaVO>{
		@Override
		public MediaVO extractData(ResultSet rs) throws SQLException, DataAccessException {
			MediaVO media = null;
			if(rs.next()) {
				media = new MediaVO(rs);			
			}
			return media;
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
		return new StringBuilder("SELECT m.id, m.added_by AS addedBy, m.title, m.src, m.size, m.type, m.absolute_path AS absolutePath, m.description, m.created_on AS createdOn, m.updated_on AS updatedOn FROM public.media m ");
		
	}

	@Override
	public Long countObject() throws SQLException {
		StringBuilder queryBuilder = new StringBuilder("SELECT count(*) FROM public.media ");
		return this.jdbcTemplate.query(queryBuilder.toString(), new LongResultProcessor());
	}

	@Override
	public List<MediaVO> getMediaList() throws SQLException {
		StringBuilder builder = getBaseSelectQuery();
		return this.jdbcTemplate.query(builder.toString(), new MediaResultProcessor());
	}
}
