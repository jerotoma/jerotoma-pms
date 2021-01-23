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
import com.jerotoma.common.constants.StudentConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.viewobjects.StudentClassVO;
import com.jerotoma.database.assemblers.dao.AssemblerStudentDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerClassDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerStudentClassDao;
import com.jerotoma.database.dao.DaoUtil;
import com.jerotoma.database.dao.academic.ScoreStandingDao;

@Repository
public class AssemblerStudentClassDaoImpl extends JdbcDaoSupport implements AssemblerStudentClassDao {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired DataSource dataSource;
	@Autowired AssemblerStudentDao assemblerStudentDao;	
	@Autowired ScoreStandingDao scoreStandingDao;
	@Autowired AssemblerClassDao assemblerClassDao;
	
	Map<String, Object> map;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		this.jdbcTemplate = getJdbcTemplate();
	}

	@Override
	public StudentClassVO findObject(Integer primaryKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE id = ? ").toString();
		return this.jdbcTemplate.query(query, new StudentClassSingleResultProcessor(), primaryKey);
	}

	@Override
	public StudentClassVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE code = ? ").toString();
		return this.jdbcTemplate.query(query, new StudentClassSingleResultProcessor(), uniqueKey);
	}

	@Override
	public List<StudentClassVO> loadList() throws SQLException {
		StringBuilder builder = getBaseSelectQuery();				
		return this.jdbcTemplate.query(builder.toString(), new StudentClassResultProcessor());
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
		
		List<StudentClassVO> studentClasses = this.jdbcTemplate.query(builder.toString(), new StudentClassResultProcessor(), paramList);
		map.put(StudentConstant.Class.STUDENT_CLASSES, studentClasses);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		
		return map;
	}
	
	public class StudentClassResultProcessor implements RowMapper<StudentClassVO>{
		@Override
		public StudentClassVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return mapResult(rs);
		}		
	}
	
	private StudentClassVO mapResult(ResultSet rs) throws SQLException {
		StudentClassVO studentClass = new StudentClassVO(rs);
		studentClass.setScoreStanding(DaoUtil.getEntity(scoreStandingDao.findById(rs.getInt(StudentConstant.Class.SCORE_STANDING_ID))));
		studentClass.setStudent(assemblerStudentDao.findObject(rs.getInt(StudentConstant.Class.STUDENT_ID)));
		return studentClass;	
	}
	
	public class StudentClassSingleResultProcessor implements ResultSetExtractor<StudentClassVO>{
		@Override
		public StudentClassVO extractData(ResultSet rs) throws SQLException, DataAccessException {
			StudentClassVO classStudentClass = null;
			if(rs.next()) {
				classStudentClass = mapResult(rs);			
			}
			return classStudentClass;
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
		return new StringBuilder("SELECT ")
				.append("sc.id, sc.class_id AS classId, sc.student_academic_level_id AS studentAcademicLevelId, ")
				.append("sc.completion_status_id AS completionStatusId,  sal.student_id AS studentId, sc.score, ")
				.append("co.name AS courseName,  c.course_id AS courseId, ")
				.append("sc.score_standing_id AS scoreStandingId, sc.created_on AS createdOn, sc.updated_on AS updatedOn, ")
				.append(" sc.updated_by AS updatedBy FROM public.student_classes sc ")
				.append("INNER JOIN classes c ON c.id =  sc.class_id ")
				.append("INNER JOIN courses co ON co.id =  c.course_id ")
				.append("INNER JOIN student_academic_levels sal ON sal.id = sc.student_academic_level_id ");		
	}

	@Override
	public Long countObject() throws SQLException {
		StringBuilder queryBuilder = new StringBuilder("SELECT count(*) FROM public.student_classes ");
		return this.jdbcTemplate.query(queryBuilder.toString(), new LongResultProcessor());
	}

	@Override
	public boolean doesStudentClassRecordExist(Integer classId, Integer studentAcademicLevelId) {
		StringBuilder queryBuilder = new StringBuilder("SELECT count(*) FROM public.student_classes  WHERE class_id = ? AND student_academic_level_id = ? ");
		return this.jdbcTemplate.query(queryBuilder.toString(), new LongResultProcessor(), classId, studentAcademicLevelId) > 0;		
	}

	@Override
	public List<StudentClassVO> findStudentClassesByClassId(Integer classId) throws SQLException {
		StringBuilder builder = getBaseSelectQuery().append(" WHERE class_id = ? ");
		return this.jdbcTemplate.query(builder.toString(),  new StudentClassResultProcessor(), classId);
	}
	
	@Override
	public List<StudentClassVO> findStudentClassesByStudentId(Integer studentId) throws SQLException {
		StringBuilder builder = getBaseSelectQuery().append(" WHERE sal.student_id = ? ");
		return this.jdbcTemplate.query(builder.toString(),  new StudentClassResultProcessor(), studentId);
	}

	@Override
	public List<StudentClassVO> findStudentClasses(Integer studentId, Integer academicLevelId) throws SQLException {
		StringBuilder builder = getBaseSelectQuery().append(" WHERE sal.student_id = ? AND sal.academic_level_id = ?");
		return this.jdbcTemplate.query(builder.toString(),  new StudentClassResultProcessor(), studentId);
	}

	@Override
	public List<StudentClassVO> findStudentClasses(Integer studentId, Integer academicLevelId, Integer academicYearId)
			throws SQLException {
		StringBuilder builder = getBaseSelectQuery().append(" WHERE sal.student_id = ? AND sal.academic_year_id = ? AND sal.academic_level_id = ? ");
		return this.jdbcTemplate.query(builder.toString(),  new StudentClassResultProcessor(), studentId, academicLevelId, academicYearId);
	}
}
