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
import com.jerotoma.common.constants.ClassRoomConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.viewobjects.ClassRoomVO;
import com.jerotoma.database.assemblers.dao.academic.AssemblerClassRoomDao;
import com.jerotoma.database.dao.DaoUtil;


@Repository
public class AssemblerClassRoomDaoImpl extends JdbcDaoSupport implements AssemblerClassRoomDao {
	
	
private JdbcTemplate jdbcTemplate;
	
	@Autowired DataSource dataSource;
	Map<String, Object> map;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		this.jdbcTemplate = getJdbcTemplate();
	}

	@Override
	public ClassRoomVO findObject(Integer primaryKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE id = ? ").toString();
		return this.jdbcTemplate.query(query, new ClassRoomSingleResultProcessor(), primaryKey);
	}

	@Override
	public ClassRoomVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE code = ? ").toString();
		return this.jdbcTemplate.query(query, new ClassRoomSingleResultProcessor(), uniqueKey);
	}

	@Override
	public List<ClassRoomVO> loadList(QueryParam queryParam) throws SQLException {
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
		
		List<ClassRoomVO> classRooms = this.jdbcTemplate.query(builder.toString(), new ClassRoomResultProcessor(), paramList);
		map.put(ClassRoomConstant.CLASS_ROOMS, classRooms);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		
		return map;
	}
	
	public class ClassRoomResultProcessor implements RowMapper<ClassRoomVO>{
		@Override
		public ClassRoomVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			ClassRoomVO classRoom = new ClassRoomVO(rs);
					
			return classRoom;
		}		
	}
	
	public class ClassRoomSingleResultProcessor implements ResultSetExtractor<ClassRoomVO>{
		@Override
		public ClassRoomVO extractData(ResultSet rs) throws SQLException, DataAccessException {
			ClassRoomVO classRoom = null;
			if(rs.next()) {
				classRoom = new ClassRoomVO(rs);			
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
		return new StringBuilder("SELECT id, code, name, capacity, description, created_on, updated_on FROM public.class_rooms ");
		
	}

	@Override
	public Long countObject() throws SQLException {
		StringBuilder queryBuilder = new StringBuilder("SELECT count(*) FROM public.class_rooms ");
		return this.jdbcTemplate.query(queryBuilder.toString(), new LongResultProcessor());
	}
}
