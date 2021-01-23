package com.jerotoma.database.assemblers.dao.academic.impls;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.ClassConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.viewobjects.AcademicLevelVO;
import com.jerotoma.common.viewobjects.AcademicYearVO;
import com.jerotoma.common.viewobjects.RoomVO;
import com.jerotoma.common.viewobjects.StudentAcademicLevelClass;
import com.jerotoma.common.viewobjects.CourseVO;
import com.jerotoma.common.viewobjects.ClassVO;
import com.jerotoma.common.viewobjects.MeetingTimeVO;
import com.jerotoma.common.viewobjects.StudentVO;
import com.jerotoma.common.viewobjects.TeacherVO;
import com.jerotoma.database.assemblers.dao.AssemblerStudentDao;
import com.jerotoma.database.assemblers.dao.AssemblerTeacherDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerAcademicLevelDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerAcademicYearDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerRoomDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerCourseDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerClassDao;
import com.jerotoma.database.assemblers.dao.schedules.AssemblerMeetingTimeDao;
import com.jerotoma.database.dao.DaoUtil;

@Repository
public class AssemblerClassDaoImpl extends JdbcDaoSupport implements AssemblerClassDao {
	
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired DataSource dataSource;
	@Autowired AssemblerTeacherDao assemblerTeacherDao;	
	@Autowired AssemblerMeetingTimeDao assemblerMeetingTimeDao;	
	@Autowired AssemblerCourseDao assemblerCourseDao;
	@Autowired AssemblerStudentDao assemblerStudentDao;	
	@Autowired AssemblerAcademicLevelDao assemblerAcademicLevelDao;
	@Autowired AssemblerAcademicYearDao assemblerAcademicYearDao;
	@Autowired AssemblerRoomDao assemblerClassRoomDao;
	
	Map<String, Object> map;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		this.jdbcTemplate = getJdbcTemplate();
	}
	@Override
	public ClassVO findObject(Integer primaryKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE cl.id = ? ").toString();
		return this.jdbcTemplate.query(query, new JClassSingleResultProcessor(), primaryKey);
	}

	@Override
	public ClassVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		throw new RuntimeException("findObjectUniqueKey has not been implemented yet");
	}

	@Override
	public List<ClassVO> loadList() throws SQLException {
		StringBuilder builder = getBaseSelectQuery();				
		return this.jdbcTemplate.query(builder.toString(), new JClassResultProcessor());
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		
		map = new HashMap<>();
		StringBuilder builder = getBaseSelectQuery();
		builder.append(DaoUtil.getOrderBy("cl." + queryParam.getFieldName(), queryParam.getOrderby()))
		.append(" ")
		.append("limit ? offset ?");

		Long countResults = countObject();
		int pageCount = DaoUtil.getPageCount(queryParam.getPageSize(), countResults);
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		Object[] paramList = new Object[] {limit, offset};
		
		List<ClassVO> classes = this.jdbcTemplate.query(builder.toString(), new JClassResultProcessor(), paramList);
		map.put(ClassConstant.CLASSES, classes);
		map.put(ClassConstant.CLASS_COUNT, countResults);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		
		return map;
	}
	
	public class JClassResultProcessor implements RowMapper<ClassVO>{
		@Override
		public ClassVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return mapJClassResult(rs);
		}		
	}
	
	public class JClassSingleResultProcessor implements ResultSetExtractor<ClassVO>{
		@Override
		public ClassVO extractData(ResultSet rs) throws SQLException, DataAccessException {
			ClassVO jClass = null;
			if(rs.next()) {
				jClass = mapJClassResult(rs);
				jClass.setStudents(loadStudentsByJClassID(jClass.getId()));
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
		return new StringBuilder("SELECT cl.id, cl.teacher_id AS teacherId, cl.course_id AS courseId, cl.room_id AS roomId, cl.academic_year_id AS academicYearId, cl.meeting_time_id AS meetingTimeId, cl.capacity, cl.updated_by AS updatedBy, cl.created_on AS createdOn, cl.updated_on AS updatedOn, ")
				.append("(SELECT count(sal.student_id) FROM public.student_academic_levels sal INNER JOIN public.student_classes sc ON sc.student_academic_level_id = sal.id AND sc.class_id = cl.id) AS totalStudents ")
				.append("FROM public.classes cl ")				
				.append("INNER JOIN courses co ON co.id = cl.course_id ");		
	}
	
	@Override
	public List<ClassVO> loadClassesByStudentId(Integer studentId) {		
		String query = "SELECT sc.class_id FROM public.student_academic_levels sal INNER JOIN public.student_classes sc ON sc.student_academic_level_id = sal.id  WHERE sal.student_id = ?";
		
		return this.jdbcTemplate.query(query,new ArgumentPreparedStatementSetter(new Object[] {studentId}), new RowMapper<ClassVO>() {
			@Override
			public ClassVO mapRow(ResultSet rs, int rowNum) throws SQLException {				
				return findObject(rs.getInt("class_id"));
			}
			
		});
		
	}

	@Override
	public Long countObject() throws SQLException {
		StringBuilder queryBuilder = new StringBuilder("SELECT count(*) FROM public.classes ");
		return this.jdbcTemplate.query(queryBuilder.toString(), new LongResultProcessor());
	}
	
	
	public ClassVO mapJClassResult(ResultSet rs) throws SQLException {
		ClassVO jClass = new ClassVO(rs);
		jClass.setAcademicYear(loadAcademicYear(rs.getInt(ClassConstant.CLASS_ACADEMIC_YEAR_ID)));
		jClass.setRoom(loadClassRoom(rs.getInt(ClassConstant.CLASS_ROOM_ID)));
		jClass.setCourse(loadCourse(rs.getInt(ClassConstant.CLASS_COURSE_ID)));
		jClass.setTeacher(loadTeacher(rs.getInt(ClassConstant.CLASS_TEACHER_ID)));		
		jClass.setMeetingTime(loadMeetingTime(rs.getInt(ClassConstant.CLASS_MEETING_TIME_ID)));		
		return jClass;
	}
	
	private MeetingTimeVO loadMeetingTime(int meetingTimeID) throws SQLException {		
		return assemblerMeetingTimeDao.findObject(meetingTimeID);
	}
	private List<StudentVO> loadStudentsByJClassID(Integer classId) throws SQLException {
		return assemblerStudentDao.loadStudentsByJClassID(classId);
	}
	private TeacherVO loadTeacher(Integer teacherId) throws SQLException {
		return assemblerTeacherDao.findObject(teacherId);
	}
	private CourseVO loadCourse(Integer courseId) throws SQLException {
		return assemblerCourseDao.findObject(courseId);
	}
	private RoomVO loadClassRoom(Integer classRoomId) throws SQLException {		
		return assemblerClassRoomDao.findObject(classRoomId);
	}
	private AcademicYearVO loadAcademicYear(Integer academicYearId) throws SQLException {		
		return assemblerAcademicYearDao.findObject(academicYearId);
	}
	
	@Override
	public List<ClassVO> loadClassesByAcademicYear(Integer academicYearId) throws SQLException {
		StringBuilder queryBuilder = getBaseSelectQuery().append( " WHERE cl.academic_year_id = ? ");
		return this.jdbcTemplate.query(queryBuilder.toString(), new JClassResultProcessor(), academicYearId);
	}
	@Override
	public List<ClassVO> loadStudentUnregisteredClassesByAcademicYear(Integer studentId, Integer academicLevelId, Integer academicYearId)
			throws SQLException {
		StringBuilder queryBuilder = getBaseSelectQuery()				
				.append(" WHERE cl.academic_year_id = ? AND co.academic_level_id = ? AND cl.course_id NOT IN( ")
					.append(" SELECT course_id FROM public.classes cc ")
						.append(" INNER JOIN student_classes srcc ON srcc.class_id = cc.id ")
						.append(" INNER JOIN student_academic_levels scc ON scc.id = srcc.student_academic_level_id")
					.append(" WHERE cc.academic_year_id = ? AND scc.student_id = ? ")
				.append(")");
		return this.jdbcTemplate.query(queryBuilder.toString(), new JClassResultProcessor(), academicYearId, academicLevelId, academicYearId, studentId);
	}
	@Override
	public List<ClassVO> loadStudentClassesByAcademicYear(Integer studentId, Integer academicYearId)
			throws SQLException {
		StringBuilder queryBuilder = getBaseSelectQuery()
			.append(" INNER JOIN student_classes src ON src.class_id = cl.id ")
			.append(" INNER JOIN student_academic_levels sc ON sc.id = src.student_academic_level_id")
			.append(" WHERE cl.academic_year_id = ? AND sc.student_id = ? ");				
		return this.jdbcTemplate.query(queryBuilder.toString(), new JClassResultProcessor(), academicYearId, studentId);
	}
	@Override
	public ClassVO findClassByUniqueParams(Integer teacherId, Integer courseId, Integer academicYearId)
			throws SQLException {
		String query = getBaseSelectQuery().append(" WHERE cl.teacher_id = ? AND cl.course_id = ? AND cl.academic_year_id = ?").toString();
		return this.jdbcTemplate.query(query, new JClassSingleResultProcessor(), teacherId, courseId, academicYearId);
	}
	
	@Override
	public List<ClassVO> loadClassesByParams(Integer programId, Integer academicLevelId, Integer academicYearId)
			throws SQLException {
		StringBuilder queryBuilder = getBaseSelectQuery()				
				.append(" WHERE co.program_id = ? AND co.academic_level_id = ? AND  cl.academic_year_id = ?");
		return this.jdbcTemplate.query(queryBuilder.toString(), new JClassResultProcessor(), programId, academicLevelId, academicYearId);
	}
	@Transactional
	@Override
	public List<ClassVO> loadTeacherClassListByTeacherId(Integer teacherId) throws SQLException {
		StringBuilder queryBuilder = getBaseSelectQuery().append( " WHERE cl.teacher_id = ? ");
		return this.jdbcTemplate.query(queryBuilder.toString(), new JClassResultProcessor(), teacherId);
	}
	
	@Override
	public List<ClassVO> loadStudentClasses(Integer studentId, Integer academicLevelId, Integer academicYearId)
			throws SQLException {
		StringBuilder queryBuilder = getBaseSelectQuery()
			.append(" INNER JOIN student_classes sc ON sc.class_id = cl.id ")
			.append(" INNER JOIN student_academic_levels sal ON sal.id = sc.student_academic_level_id")
			.append(" WHERE sal.student_id = ? AND  sal.academic_level_id = ? AND sal.academic_year_id = ? ");				
		return this.jdbcTemplate.query(queryBuilder.toString(), new JClassResultProcessor(), studentId, academicLevelId, academicYearId);
	}
	
	@Transactional
	@Override
	public List<StudentAcademicLevelClass> loadAllStudentAcademicLevelsClassList(Integer studentId)
			throws SQLException {
		
		String query = "SELECT sal.student_id, sal.academic_level_id, sal.academic_year_id FROM public.student_academic_levels sal WHERE sal.student_id = ?";
		
		return this.jdbcTemplate.query(query,new ArgumentPreparedStatementSetter(new Object[] {studentId}), new RowMapper<StudentAcademicLevelClass>() {
			@Override
			public StudentAcademicLevelClass mapRow(ResultSet rs, int rowNum) throws SQLException {				
				
				List<ClassVO> classes = loadStudentClasses(studentId, rs.getInt("academic_level_id"), rs.getInt("academic_year_id"));
				
				StudentVO student = assemblerStudentDao.findObject(studentId);
				AcademicLevelVO academicLevel = assemblerAcademicLevelDao.findObject(rs.getInt("academic_level_id"));
				AcademicYearVO academicYear = loadAcademicYear(rs.getInt("academic_year_id"));
				
				return new StudentAcademicLevelClass(student, academicLevel, academicYear, classes);
			}
			
		});
	}
	@Override
	public List<ClassVO> loadClassesByTeacherClassParams(Integer teacherId, Integer programId, Integer academicLevelId,
			Integer academicYearId) {
		StringBuilder queryBuilder = getBaseSelectQuery()				
				.append(" WHERE cl.teacher_id = ? AND co.program_id = ? AND co.academic_level_id = ? AND  cl.academic_year_id = ?");
		return this.jdbcTemplate.query(queryBuilder.toString(), new JClassResultProcessor(), teacherId, programId, academicLevelId, academicYearId);
	}
}
