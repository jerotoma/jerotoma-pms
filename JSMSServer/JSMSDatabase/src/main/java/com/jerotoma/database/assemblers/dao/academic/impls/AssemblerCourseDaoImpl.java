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
import com.jerotoma.common.constants.CourseConstant;
import com.jerotoma.common.constants.DatabaseConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.viewobjects.AcademicLevelVO;
import com.jerotoma.common.viewobjects.CourseVO;
import com.jerotoma.common.viewobjects.DepartmentVO;
import com.jerotoma.common.viewobjects.ProgramVO;
import com.jerotoma.database.assemblers.dao.AssemblerAcademicDisciplineDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerAcademicLevelDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerCourseDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerProgramDao;
import com.jerotoma.database.dao.DaoUtil;


@Repository
public class AssemblerCourseDaoImpl extends JdbcDaoSupport implements AssemblerCourseDao {
	
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired DataSource dataSource;
	@Autowired AssemblerAcademicLevelDao assemblerAcademicLevelDao;
	@Autowired AssemblerProgramDao assemblerProgramDao;	
	@Autowired AssemblerAcademicDisciplineDao assemblerAcademicDisciplineDao;
	Map<String, Object> map;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		this.jdbcTemplate = getJdbcTemplate();		
	}

	@Override
	public CourseVO findObject(Integer primaryKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE id = ? ").toString();
		return this.jdbcTemplate.query(query, new CourseSingleResultProcessor(), primaryKey);
	}

	@Override
	public CourseVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE code = ? ").toString();
		return this.jdbcTemplate.query(query, new CourseSingleResultProcessor(), uniqueKey);
	}

	@Override
	public List<CourseVO> loadList() throws SQLException {
		StringBuilder builder = getBaseSelectQuery();				
		return this.jdbcTemplate.query(builder.toString(), new CourseResultProcessor());
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
		
		List<CourseVO> courses = this.jdbcTemplate.query(builder.toString(), new CourseResultProcessor(), paramList);
		map.put(CourseConstant.COURSES, courses);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		
		return map;
	}
	
	public class CourseResultProcessor implements RowMapper<CourseVO>{
		@Override
		public CourseVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return mapCourse(rs);
		}		
	}
	
	private CourseVO mapCourse(ResultSet rs) throws SQLException {
		CourseVO course = new CourseVO(rs);
		course.setAcademicLevel(findAcademicLevel(course.getAcademicLevelId()));	
		course.setProgram(findProgram(course.getProgramId()));	
		course.setDepartment(findDepartment(course.getDepartmentId()));
		return course;
	}
	
	private ProgramVO findProgram(Integer programId) throws SQLException {		
		StringBuilder builder = new StringBuilder("SELECT p.id, p.name, p.code, p.description, p.created_on AS createdOn, p.updated_on AS updatedOn FROM ")
				.append(DatabaseConstant.TABLES.PROGRAMS)
				.append(" p ")
				.append("WHERE p.id = ? ");
		
		return this.jdbcTemplate.query(builder.toString(), new ResultSetExtractor<ProgramVO>(){
			@Override
			public ProgramVO extractData(ResultSet rs) throws SQLException, DataAccessException {
				if(rs.next()) {
					ProgramVO program = new ProgramVO(rs);				
					return program;
				}
				return null;
			}	
		}, programId);
	}

	private AcademicLevelVO findAcademicLevel(Integer academicLevelId) throws SQLException {	
		return assemblerAcademicLevelDao.findObject(academicLevelId);
	}

	private DepartmentVO findDepartment(Integer id) throws SQLException {		
		String buildSql = new StringBuilder("SELECT d.id, d.name, d.created_on AS createdOn, d.updated_on AS updatedOn FROM ")
				.append(DatabaseConstant.TABLES.DEPARTMENTS).append(" d ").append(" WHERE d.id = ? ").toString();		
		return this.jdbcTemplate.query(buildSql, new DepartmentSingleResultProcessor(), id);
	}

	public class CourseSingleResultProcessor implements ResultSetExtractor<CourseVO>{
		@Override
		public CourseVO extractData(ResultSet rs) throws SQLException, DataAccessException {
			CourseVO course = null;
			if(rs.next()) {
				return mapCourse(rs);
			}
			return course;
		}				
	}
	
	public class DepartmentSingleResultProcessor implements ResultSetExtractor<DepartmentVO>{
		@Override
		public DepartmentVO extractData(ResultSet rs) throws SQLException, DataAccessException {
			DepartmentVO department = null;
			if(rs.next()) {
				department = new DepartmentVO(rs);
				
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
	
	private StringBuilder getBaseSelectQuery() {		
		return new StringBuilder("SELECT c.id, c.code, c.name, c.description, c.department_id AS departmentId, c.academic_level_id AS academicLevelId, c.program_id AS programId, c.created_on, c.updated_on FROM public.courses c ");		
	}

	@Override
	public Long countObject() throws SQLException {
		StringBuilder queryBuilder = new StringBuilder("SELECT count(*) FROM public.courses ");
		return this.jdbcTemplate.query(queryBuilder.toString(), new LongResultProcessor());
	}
	
	@Override
	public List<CourseVO> findCoursesByAcademicLevelId(Integer academicLevelId) throws SQLException {		
		String query = getBaseSelectQuery().append("WHERE c.academic_level_id = ? ").toString();
		return this.jdbcTemplate.query(query, new CourseResultProcessor(), academicLevelId);		
	}

	@Override
	public List<CourseVO> findCoursesByDepartmentId(Integer departmentId) throws SQLException {
		StringBuilder builder = getBaseSelectQuery().append(" INNER JOIN ").append(DatabaseConstant.TABLES.DEPARTMENTS).append(" d ON d.id = c.department_id  WHERE c.department_id = ? ");
		return getJdbcTemplate().query(builder.toString(), new CourseResultProcessor(), departmentId);
	}
	
	@Override
	public List<CourseVO> findAllCourses() throws SQLException {
		StringBuilder builder = getBaseSelectQuery();
		return this.jdbcTemplate.query(builder.toString(), new CourseResultProcessor());
	}

	@Override
	public List<CourseVO> findCoursesByProgramAndAcademicLevelIDs(Integer programId, Integer academicLevelId)
			throws SQLException {
		String query = getBaseSelectQuery().append("WHERE c.academic_level_id = ? AND c.program_id = ? ").toString();
		return this.jdbcTemplate.query(query, new CourseResultProcessor(), academicLevelId, programId);	
	}
	
}
