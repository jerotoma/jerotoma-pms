package com.jerotoma.database.assemblers.dao.academic.impls;

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
import com.jerotoma.common.constants.RoomConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.viewobjects.RoomVO;
import com.jerotoma.database.assemblers.dao.academic.AssemblerRoomDao;
import com.jerotoma.database.dao.DaoUtil;


@Repository
public class AssemblerRoomDaoImpl extends JdbcDaoSupport implements AssemblerRoomDao {
	
	
private JdbcTemplate jdbcTemplate;
	
	@Autowired DataSource dataSource;
	Map<String, Object> map;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		this.jdbcTemplate = getJdbcTemplate();
	}

	@Override
	public RoomVO findObject(Integer primaryKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE id = ? ").toString();
		return this.jdbcTemplate.query(query, new ClassRoomSingleResultProcessor(), primaryKey);
	}

	@Override
	public RoomVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE code = ? ").toString();
		return this.jdbcTemplate.query(query, new ClassRoomSingleResultProcessor(), uniqueKey);
	}

	@Override
	public List<RoomVO> loadList(QueryParam queryParam) throws SQLException {
		StringBuilder builder = getBaseSelectQuery();
				builder.append(DaoUtil.getOrderBy(queryParam.getFieldName(), queryParam.getOrderby()))
				.append(" ")
				.append("limit ? offset ?");

		Long countResults = countObject();
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		Object[] paramList = new Object[] {limit, offset};
		
		return this.jdbcTemplate.query(builder.toString(), new ClassRoomResultProcessor(), paramList);
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
		
		List<RoomVO> classRooms = this.jdbcTemplate.query(builder.toString(), new ClassRoomResultProcessor(), paramList);
		map.put(RoomConstant.ROOMS, classRooms);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		
		return map;
	}
	
	public class ClassRoomResultProcessor implements RowMapper<RoomVO>{
		@Override
		public RoomVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			RoomVO classRoom = new RoomVO(rs);
					
			return classRoom;
		}		
	}
	
	public class ClassRoomSingleResultProcessor implements ResultSetExtractor<RoomVO>{
		@Override
		public RoomVO extractData(ResultSet rs) throws SQLException, DataAccessException {
			RoomVO classRoom = null;
			if(rs.next()) {
				classRoom = new RoomVO(rs);			
			}
			return classRoom;
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
		return new StringBuilder("SELECT id, code, name, capacity, description, created_on, updated_on FROM public.rooms ");
		
	}

	@Override
	public Long countObject() throws SQLException {
		StringBuilder queryBuilder = new StringBuilder("SELECT count(*) FROM public.rooms ");
		return this.jdbcTemplate.query(queryBuilder.toString(), new LongResultProcessor());
	}

	@Override
	public List<RoomVO> findList() throws SQLException {
		StringBuilder builder = getBaseSelectQuery();
		return this.jdbcTemplate.query(builder.toString(), new ClassRoomResultProcessor());
	}

	@Override
	public List<RoomVO> getRoomsByCapacity(Integer capacity) throws SQLException {
		StringBuilder builder = getBaseSelectQuery().append(" WHERE capacity >= ? ");
		return this.jdbcTemplate.query(builder.toString(), new ClassRoomResultProcessor(), capacity);
	}
}
