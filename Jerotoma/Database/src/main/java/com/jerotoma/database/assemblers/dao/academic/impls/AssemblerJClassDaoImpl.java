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
import com.jerotoma.common.constants.JClassConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.viewobjects.AcademicYearVO;
import com.jerotoma.common.viewobjects.ClassRoomVO;
import com.jerotoma.common.viewobjects.CourseVO;
import com.jerotoma.common.viewobjects.JClassVO;
import com.jerotoma.common.viewobjects.StudentVO;
import com.jerotoma.common.viewobjects.TeacherVO;
import com.jerotoma.database.assemblers.dao.AssemblerStudentDao;
import com.jerotoma.database.assemblers.dao.AssemblerTeacherDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerAcademicYearDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerClassRoomDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerCourseDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerJClassDao;
import com.jerotoma.database.dao.DaoUtil;

@Repository
public class AssemblerJClassDaoImpl extends JdbcDaoSupport implements AssemblerJClassDao {
	
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired DataSource dataSource;
	@Autowired AssemblerTeacherDao assemblerTeacherDao;	
	@Autowired AssemblerCourseDao assemblerCourseDao;
	@Autowired AssemblerStudentDao assemblerStudentDao;	
	@Autowired AssemblerAcademicYearDao assemblerAcademicYearDao;
	@Autowired AssemblerClassRoomDao assemblerClassRoomDao;
	
	Map<String, Object> map;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		this.jdbcTemplate = getJdbcTemplate();
	}
	@Override
	public JClassVO findObject(Integer primaryKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE id = ? ").toString();
		return this.jdbcTemplate.query(query, new JClassSingleResultProcessor(), primaryKey);
	}

	@Override
	public JClassVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		throw new RuntimeException("findObjectUniqueKey has not been implemented yet");
	}

	@Override
	public List<JClassVO> loadList(QueryParam queryParam) throws SQLException {
		StringBuilder builder = getBaseSelectQuery();
				builder.append(DaoUtil.getOrderBy(queryParam.getFieldName(), queryParam.getOrderby()))
				.append(" ")
				.append("limit ? offset ?");

		Long countResults = countObject();
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		Object[] paramList = new Object[] {limit, offset};
		
		return this.jdbcTemplate.query(builder.toString(), new JClassResultProcessor(), paramList);
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
		
		List<JClassVO> classes = this.jdbcTemplate.query(builder.toString(), new JClassResultProcessor(), paramList);
		map.put(JClassConstant.JCLASSES, classes);
		map.put(JClassConstant.JCLASS_COUNT, countResults);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		
		return map;
	}
	
	public class JClassResultProcessor implements RowMapper<JClassVO>{
		@Override
		public JClassVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return mapJClassResult(rs);
		}		
	}
	
	public class JClassSingleResultProcessor implements ResultSetExtractor<JClassVO>{
		@Override
		public JClassVO extractData(ResultSet rs) throws SQLException, DataAccessException {
			JClassVO jClass = null;
			if(rs.next()) {
				jClass = mapJClassResult(rs);			
			}
			return jClass;
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
		return new StringBuilder("SELECT id, teacher_id AS teacherId, course_id AS courseId, class_room_id AS classRoomId, academic_year_id AS academicYearId, capacity, updated_by AS updatedBy, created_on AS createdOn, updated_on AS updatedOn FROM public.classes ");
		
	}
	
	@Override
	public List<JClassVO> loadJClassesByStudentId(Integer studentId) {
		String query = "SELECT class_id FROM public.student_classes WHERE student_id = ?";
		
		return this.jdbcTemplate.query(query,new Object[] {studentId}, new RowMapper<JClassVO>() {
			@Override
			public JClassVO mapRow(ResultSet rs, int rowNum) throws SQLException {				
				return findObject(rs.getInt("class_id"));
			}
			
		});
		
	}

	@Override
	public Long countObject() throws SQLException {
		StringBuilder queryBuilder = new StringBuilder("SELECT count(*) FROM public.classes ");
		return this.jdbcTemplate.query(queryBuilder.toString(), new LongResultProcessor());
	}
	
	
	public JClassVO mapJClassResult(ResultSet rs) throws SQLException {
		JClassVO jClass = new JClassVO(rs);
		jClass.setAcademicYear(loadAcademicYear(rs.getInt(JClassConstant.JCLASS_ACADEMIC_YEAR_ID)));
		jClass.setClassRoom(loadClassRoom(rs.getInt(JClassConstant.JCLASS_CLASS_ROOM_ID)));
		jClass.setCourse(loadCourse(rs.getInt(JClassConstant.JCLASS_COURSE_ID)));
		jClass.setTeacher(loadTeacher(rs.getInt(JClassConstant.JCLASS_TEACHER_ID)));
		jClass.setStudents(loadStudentsByJClassID(jClass.getId()));
		return jClass;
	}
	
	private List<StudentVO> loadStudentsByJClassID(Integer classId) {
		return assemblerStudentDao.loadStudentsByJClassID(classId);
	}
	private TeacherVO loadTeacher(Integer teacherId) throws SQLException {
		return assemblerTeacherDao.findObject(teacherId);
	}
	private CourseVO loadCourse(Integer courseId) throws SQLException {
		return assemblerCourseDao.findObject(courseId);
	}
	private ClassRoomVO loadClassRoom(Integer classRoomId) throws SQLException {		
		return assemblerClassRoomDao.findObject(classRoomId);
	}
	private AcademicYearVO loadAcademicYear(Integer academicYearId) throws SQLException {		
		return assemblerAcademicYearDao.findObject(academicYearId);
	}
}