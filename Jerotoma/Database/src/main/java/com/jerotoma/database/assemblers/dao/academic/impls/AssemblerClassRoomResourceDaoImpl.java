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
import com.jerotoma.common.constants.ClassRoomResourceConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.viewobjects.ClassRoomResourceVO;
import com.jerotoma.database.assemblers.dao.academic.AssemblerClassRoomResourceDao;
import com.jerotoma.database.dao.DaoUtil;


@Repository
public class AssemblerClassRoomResourceDaoImpl  extends JdbcDaoSupport implements AssemblerClassRoomResourceDao {
	
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired DataSource dataSource;
	Map<String, Object> map;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		this.jdbcTemplate = getJdbcTemplate();
	}

	@Override
	public ClassRoomResourceVO findObject(Integer primaryKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE id = ? ").toString();
		return this.jdbcTemplate.query(query, new ClassRoomResourceSingleResultProcessor(), primaryKey);
	}

	@Override
	public ClassRoomResourceVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE code = ? ").toString();
		return this.jdbcTemplate.query(query, new ClassRoomResourceSingleResultProcessor(), uniqueKey);
	}

	@Override
	public List<ClassRoomResourceVO> loadList() throws SQLException {
		StringBuilder builder = getBaseSelectQuery();
		return this.jdbcTemplate.query(builder.toString(), new ClassRoomResourceResultProcessor());
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
		
		List<ClassRoomResourceVO> classRoomResources = this.jdbcTemplate.query(builder.toString(), new ClassRoomResourceResultProcessor(), paramList);
		map.put(ClassRoomResourceConstant.CLASS_ROOM_RESOURCES, classRoomResources);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		
		return map;
	}
	
	public class ClassRoomResourceResultProcessor implements RowMapper<ClassRoomResourceVO>{
		@Override
		public ClassRoomResourceVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			ClassRoomResourceVO classRoomResource = new ClassRoomResourceVO(rs);
					
			return classRoomResource;
		}		
	}
	
	public class ClassRoomResourceSingleResultProcessor implements ResultSetExtractor<ClassRoomResourceVO>{
		@Override
		public ClassRoomResourceVO extractData(ResultSet rs) throws SQLException, DataAccessException {
			ClassRoomResourceVO classRoomResource = null;
			if(rs.next()) {
				classRoomResource = new ClassRoomResourceVO(rs);			
			}
			return classRoomResource;
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
		return new StringBuilder("SELECT id, class_room_id AS classRoomId, name, quantity, description, updated_by AS updatedBy, created_on, updated_on FROM public.class_room_resources ");
		
	}

	@Override
	public Long countObject() throws SQLException {
		StringBuilder queryBuilder = new StringBuilder("SELECT count(*) FROM public.class_room_resources ");
		return this.jdbcTemplate.query(queryBuilder.toString(), new LongResultProcessor());
	}

}
