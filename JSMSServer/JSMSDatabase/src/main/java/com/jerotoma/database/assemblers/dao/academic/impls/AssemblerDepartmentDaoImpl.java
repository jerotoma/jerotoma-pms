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
import com.jerotoma.common.constants.DatabaseConstant;
import com.jerotoma.common.constants.DepartmentConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.viewobjects.CourseVO;
import com.jerotoma.common.viewobjects.DepartmentVO;
import com.jerotoma.database.assemblers.dao.academic.AssemblerAcademicYearDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerCourseDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerDepartmentDao;
import com.jerotoma.database.dao.DaoUtil;

@Repository
public class AssemblerDepartmentDaoImpl extends JdbcDaoSupport implements AssemblerDepartmentDao {
	
	private JdbcTemplate jdbcTemplate;
	protected AssemblerCourseDao assemblerCourseDao;
	private Map<String, Object> map;
	
	@Autowired DataSource dataSource;
	@Autowired AssemblerAcademicYearDao assemblerAcademicYearDao;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		this.jdbcTemplate = getJdbcTemplate();		
	}

	@Override
	public DepartmentVO findObject(Integer primaryKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE d.id = ? ").toString();
		return this.jdbcTemplate.query(query, new DepartmentSingleResultProcessor(), primaryKey);
	}

	@Override
	public DepartmentVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE d.id = ? ").toString();
		return this.jdbcTemplate.query(query, new DepartmentSingleResultProcessor(), Integer.valueOf(uniqueKey));
	}

	@Override
	public List<DepartmentVO> loadList() throws SQLException {
		StringBuilder builder = getBaseSelectQuery();
		return this.jdbcTemplate.query(builder.toString(), new DepartmentResultProcessor());
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		map = new HashMap<>();
		StringBuilder builder = getBaseSelectQuery();
		builder.append(DaoUtil.getOrderBy( "d." + queryParam.getFieldName(), queryParam.getOrderby()))
		.append(" ")
		.append("limit ? offset ?");

		Long countResults = countObject();
		int pageCount = DaoUtil.getPageCount(queryParam.getPageSize(), countResults);
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		Object[] paramList = new Object[] {limit, offset};
		
		List<DepartmentVO> departments = this.jdbcTemplate.query(builder.toString(), new DepartmentResultProcessor(), paramList);
		map.put(DepartmentConstant.DEPARTMENTS, departments);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		
		return map;
	}

	@Override
	public Long countObject() throws SQLException {
		StringBuilder queryBuilder = new StringBuilder("SELECT count(*) FROM ").append(DatabaseConstant.TABLES.DEPARTMENTS);
		return this.jdbcTemplate.query(queryBuilder.toString(), new LongResultProcessor());
	}
	
	private StringBuilder getBaseSelectQuery() {		
		return new StringBuilder("SELECT d.id, d.name, d.created_on AS createdOn, d.updated_on AS updatedOn FROM ").append(DatabaseConstant.TABLES.DEPARTMENTS).append(" d ");		
	}
	
	public class DepartmentResultProcessor implements RowMapper<DepartmentVO>{
		@Override
		public DepartmentVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return mapDepartment(rs);
		}		
	}
	
	private DepartmentVO mapDepartment(ResultSet rs) throws SQLException {
		DepartmentVO department = new DepartmentVO(rs);
		department.setCourses(findCoursesByDepartmentId(department.getId()));		
		return department;		
	}
	
	private List<CourseVO> findCoursesByDepartmentId(Integer departmentId) throws SQLException {
		StringBuilder builder = new StringBuilder("SELECT c.id, c.code, c.name, c.description, c.department_id AS departmentId, c.academic_level_id AS academicLevelId, c.program_id AS programId, c.created_on, c.updated_on FROM public.courses c  WHERE c.department_id = ? ");		
		return this.jdbcTemplate.query(builder.toString(), new CourseResultProcessor(), departmentId);		
	}
	
	public class CourseResultProcessor implements RowMapper<CourseVO>{
		@Override
		public CourseVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			CourseVO course = new CourseVO(rs);				
			return course;
		}	
	}
	

	public class DepartmentSingleResultProcessor implements ResultSetExtractor<DepartmentVO>{
		@Override
		public DepartmentVO extractData(ResultSet rs) throws SQLException, DataAccessException {
			DepartmentVO department = null;
			if(rs.next()) {
				department = mapDepartment(rs);
				
			}
			return department;
		}				
	}
	
	public class LongResultProcessor implements ResultSetExtractor<Long>{
		@Override
		public Long extractData(ResultSet rs) throws SQLException, DataAccessException {
			Long l = null;
			if (rs.next()) {
				l = rs.getLong(1);
			 }
			return l;
		}				
	}

	@Override
	public List<DepartmentVO> getAllDepartment() throws SQLException {		
		return this.jdbcTemplate.query(getBaseSelectQuery().toString(), new DepartmentResultProcessor());
	}

	@Override
	public void setAssemblerCourseDao(AssemblerCourseDao assemblerCourseDao) {
		this.assemblerCourseDao = assemblerCourseDao;		
	}

}
