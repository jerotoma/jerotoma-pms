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
import com.jerotoma.common.models.academic.CompletionOrder;
import com.jerotoma.database.assemblers.dao.AssemblerStudentDao;
import com.jerotoma.database.assemblers.dao.AssemblerTeacherDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerAcademicLevelDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerAcademicYearDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerCompletionOrderDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerCourseDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerRoomDao;
import com.jerotoma.database.assemblers.dao.schedules.AssemblerMeetingTimeDao;
import com.jerotoma.database.dao.DaoUtil;

@Repository
public class AssemblerCompletionOrderDaoImpl extends JdbcDaoSupport implements AssemblerCompletionOrderDao {
	
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired DataSource dataSource;
	@Autowired AssemblerTeacherDao assemblerTeacherDao;	
	@Autowired AssemblerMeetingTimeDao assemblerMeetingTimeDao;	
	@Autowired AssemblerCourseDao assemblerCourseDao;
	@Autowired AssemblerStudentDao assemblerStudentDao;	
	@Autowired AssemblerAcademicLevelDao assemblerAcademicLevelDao;
	@Autowired AssemblerAcademicYearDao assemblerAcademicYearDao;
	@Autowired AssemblerRoomDao assemblerCompletionOrderDao;
	
	Map<String, Object> map;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		this.jdbcTemplate = getJdbcTemplate();
	}
	
	@Override
	public CompletionOrder findObject(Integer primaryKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE id = ? ").toString();
		return this.jdbcTemplate.query(query, new CompletionOrderSingleResultProcessor(), primaryKey);
	}

	@Override
	public CompletionOrder findObjectUniqueKey(String uniqueKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE code = ? ").toString();
		return this.jdbcTemplate.query(query, new CompletionOrderSingleResultProcessor(), uniqueKey);
	}

	@Override
	public List<CompletionOrder> loadList() throws SQLException {
		StringBuilder builder = getBaseSelectQuery();				
		return this.jdbcTemplate.query(builder.toString(), new CompletionOrderResultProcessor());
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
		
		List<CompletionOrder> completionOrders = this.jdbcTemplate.query(builder.toString(), new CompletionOrderResultProcessor(), paramList);
		map.put(RoomConstant.ROOMS, completionOrders);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		
		return map;
	}
	
	public class CompletionOrderResultProcessor implements RowMapper<CompletionOrder>{
		@Override
		public CompletionOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
			CompletionOrder completionOrder = new CompletionOrder(rs);					
			return completionOrder;
		}		
	}
	
	public class CompletionOrderSingleResultProcessor implements ResultSetExtractor<CompletionOrder>{
		@Override
		public CompletionOrder extractData(ResultSet rs) throws SQLException, DataAccessException {
			CompletionOrder completionOrder = null;
			if(rs.next()) {
				completionOrder = new CompletionOrder(rs);			
			}
			return completionOrder;
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
		return new StringBuilder("SELECT co.id, co.completion_order AS completionOrder, co.name, co.updated_by AS updatedBy, co.created_on AS createdOn, co.updated_on AS updatedOn FROM public.completion_orders co ");		
	}

	@Override
	public Long countObject() throws SQLException {
		StringBuilder queryBuilder = new StringBuilder("SELECT count(*) FROM public.completion_orders ");
		return this.jdbcTemplate.query(queryBuilder.toString(), new LongResultProcessor());
	}

	@Override
	public CompletionOrder getCompletionOrder(Integer programId, Integer academicLevelId) {
		StringBuilder builder = getBaseSelectQuery()
				.append("INNER JOIN pal_completion_orders pco ON pco.completion_order_id = co.id ")
				.append("INNER JOIN program_academic_levels pal ON pal.id = pco.pal_id ")
				.append(" WHERE pal.program_id = ? AND pal.academic_level_id = ? ");
		
		return this.jdbcTemplate.query(builder.toString(), new CompletionOrderSingleResultProcessor(), programId, academicLevelId);
	}

	@Override
	public List<CompletionOrder> getCompletionOrders(Integer programId) {
		StringBuilder builder = getBaseSelectQuery()
				.append("INNER JOIN pal_completion_orders pco ON pco.completion_order_id = co.id ")
				.append("INNER JOIN program_academic_levels pal ON pal.id = pco.pal_id ")
				.append(" WHERE pal.program_id = ? ");
		
		return this.jdbcTemplate.query(builder.toString(), new CompletionOrderResultProcessor(), programId);
	}
}
