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
import com.jerotoma.common.constants.ClassConstant;
import com.jerotoma.common.constants.StudentConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.viewobjects.AcademicLevelVO;
import com.jerotoma.common.viewobjects.AcademicYearVO;
import com.jerotoma.common.viewobjects.ClassVO;
import com.jerotoma.common.viewobjects.CourseVO;
import com.jerotoma.common.viewobjects.MeetingTimeVO;
import com.jerotoma.common.viewobjects.RoomVO;
import com.jerotoma.common.viewobjects.StudentClassVO;
import com.jerotoma.common.viewobjects.StudentVO;
import com.jerotoma.common.viewobjects.TeacherVO;
import com.jerotoma.database.assemblers.dao.AssemblerStudentDao;
import com.jerotoma.database.assemblers.dao.AssemblerTeacherDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerAcademicLevelDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerAcademicYearDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerCourseDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerJClassDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerRoomDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerStudentClassDao;
import com.jerotoma.database.assemblers.dao.schedules.AssemblerMeetingTimeDao;
import com.jerotoma.database.dao.DaoUtil;

@Repository
public class AssemblerStudentClassDaoImpl extends JdbcDaoSupport implements AssemblerStudentClassDao {
	
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired DataSource dataSource;
	@Autowired AssemblerJClassDao assemblerJClasseDao;
	@Autowired AssemblerStudentDao assemblerStudentDao;	
	@Autowired AssemblerAcademicYearDao assemblerAcademicYearDao;
	@Autowired AssemblerAcademicLevelDao assemblerAcademicLevelDao;
	@Autowired AssemblerTeacherDao assemblerTeacherDao;	
	@Autowired AssemblerMeetingTimeDao assemblerMeetingTimeDao;	
	@Autowired AssemblerCourseDao assemblerCourseDao;
	@Autowired AssemblerRoomDao assemblerClassRoomDao;
	
	
	
	Map<String, Object> map;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		this.jdbcTemplate = getJdbcTemplate();
	}
	@Override
	public StudentClassVO findObject(Integer primaryKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE sc.id = ? ").toString();
		return this.jdbcTemplate.query(query, new StudentClassSingleResultProcessor(), primaryKey);
	}

	@Override
	public StudentClassVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		throw new RuntimeException("findObjectUniqueKey has not been implemented yet");
	}

	@Override
	public List<StudentClassVO> loadList() throws SQLException {
		StringBuilder builder = getBaseSelectQuery();				
		return this.jdbcTemplate.query(builder.toString(), new StudentClassResultProcessor());
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		
		map = new HashMap<>();
		StringBuilder builder = getBaseSelectQuery()
		.append(DaoUtil.getOrderBy(" sc.student_id, " + queryParam.getFieldName(), queryParam.getOrderby()))
		.append(" ")
		.append("limit ? offset ?");

		Long countResults = countObject();
		int pageCount = DaoUtil.getPageCount(queryParam.getPageSize(), countResults);
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		Object[] paramList = new Object[] {limit, offset};
		
		List<StudentClassVO> classes = this.jdbcTemplate.query(builder.toString(), new StudentClassResultProcessor(), paramList);
		map.put(StudentConstant.Class.STUDENT_CLASSES, classes);
		map.put(StudentConstant.Class.COUNT, countResults);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		
		return map;
	}
	
	public class StudentClassResultProcessor implements RowMapper<StudentClassVO>{
		@Override
		public StudentClassVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return mapStudentClassResult(rs);
		}		
	}
	
	public class StudentClassSingleResultProcessor implements ResultSetExtractor<StudentClassVO>{
		@Override
		public StudentClassVO extractData(ResultSet rs) throws SQLException, DataAccessException {
			StudentClassVO jClass = null;
			if(rs.next()) {
				jClass = mapStudentClassResult(rs);	
				jClass.setJClasses(findStudentClassesByStudentIdAndAndAcademicLevelID(jClass.getStudent().getId(), jClass.getAcademicLevel().getId()));
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
		return new StringBuilder("SELECT sc.id, sc.student_id AS studentId, sc.academic_year_id AS academicYearId, ")
				.append(" (SELECT COUNT(*) FROM public.student_registered_classes src WHERE src.student_class_id = sc.id) AS classesCount, ")
				.append(" sc.academic_level_id AS academicLevelId, sc.updated_by AS updatedBy, sc.created_on AS createdOn, sc.updated_on AS updatedOn FROM public.student_classes sc ");
		
	}

	@Override
	public Long countObject() throws SQLException {
		StringBuilder queryBuilder = new StringBuilder("SELECT count(DISTINCT student_id) FROM public.student_classes ");
		return this.jdbcTemplate.query(queryBuilder.toString(), new LongResultProcessor());
	}
	
	
	public StudentClassVO mapStudentClassResult(ResultSet rs) throws SQLException {
		StudentClassVO jClass = new StudentClassVO(rs);
		Integer studentId = rs.getInt(StudentConstant.Class.STUDENT_ID);
		int academicYearId = rs.getInt(StudentConstant.Class.ACADEMIC_YEAR_ID);
		int academiLevelId = rs.getInt(StudentConstant.Class.ACADEMIC_LEVEL_ID);
		jClass.setAcademicYear(loadAcademicYear(academicYearId));
		jClass.setAcademicLevel(loadAcademicLevel(academiLevelId));		
		jClass.setStudent(loadStudentsByStudentID(studentId));
		return jClass;
	}	
	
	private AcademicLevelVO loadAcademicLevel(Integer academiLevelId) throws SQLException {
		return assemblerAcademicLevelDao.findObject(academiLevelId);
	}
		
	private StudentVO loadStudentsByStudentID(Integer studentId) throws SQLException {		
		return assemblerStudentDao.findObject(studentId);
	}
	private AcademicYearVO loadAcademicYear(Integer academicYearId) throws SQLException {		
		return assemblerAcademicYearDao.findObject(academicYearId);
	}
	@Override
	public StudentClassVO findStudentClassByParams(Integer studentId, Integer classId) {
		String query = getBaseSelectQuery()
				.append("INNER JOIN public.student_registered_classes src ON src.student_class_id = sc.id")
				.append("WHERE sc.student_id = ? AND src.class_id = ? ").toString();
		return this.jdbcTemplate.query(query, new StudentClassSingleResultProcessor(), studentId, classId);
	}
	@Override
	public StudentClassVO findStudentClassesByStudentId(Integer studentId) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE sc.student_id = ? ").toString();
		return this.jdbcTemplate.query(query, new StudentClassSingleResultProcessor(), studentId);
	}
	
	@Override
	public List<ClassVO> findStudentClassesByStudentIdAndAndAcademicLevelID(Integer studentId, Integer academicLevelId)
			throws SQLException {
		
		StringBuilder builder = new StringBuilder("SELECT ")
				.append(" cl.id, cl.teacher_id AS teacherId, cl.course_id AS courseId, cl.room_id AS roomId, cl.academic_year_id AS academicYearId, ")
				.append(" cl.meeting_time_id AS meetingTimeId, cl.capacity, cl.updated_by AS updatedBy, cl.created_on AS createdOn, cl.updated_on AS updatedOn ")
				.append("FROM public.classes cl ")
				.append("INNER JOIN public.student_registered_classes src ON src.class_id = cl.id ")
				.append("INNER JOIN public.student_classes sc ON sc.id = src.student_class_id ")
				.append("WHERE sc.student_id = ? AND sc.academic_level_id = ? ");
		
		return this.jdbcTemplate.query(builder.toString(), new RowMapper<ClassVO>() {
			@Override
			public ClassVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ClassVO classVO = new ClassVO(rs);
				classVO.setAcademicYear(loadAcademicYear(rs.getInt(ClassConstant.CLASS_ACADEMIC_YEAR_ID)));
				classVO.setRoom(loadClassRoom(rs.getInt(ClassConstant.CLASS_ROOM_ID)));
				classVO.setCourse(loadCourse(rs.getInt(ClassConstant.CLASS_COURSE_ID)));
				classVO.setTeacher(loadTeacher(rs.getInt(ClassConstant.CLASS_TEACHER_ID)));
				classVO.setMeetingTime(loadMeetingTime(rs.getInt(ClassConstant.CLASS_MEETING_TIME_ID)));
				return classVO;
			}			
		}, studentId, academicLevelId);
	}
	
	private MeetingTimeVO loadMeetingTime(int meetingTimeID) throws SQLException {		
		return assemblerMeetingTimeDao.findObject(meetingTimeID);
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
	
	@Override
	public List<ClassVO> findTeacherClassesByTeacherId(Integer teacherID) throws SQLException {
		StringBuilder builder = new StringBuilder("SELECT ")
				.append(" cl.id, cl.teacher_id AS teacherId, cl.course_id AS courseId, cl.room_id AS roomId, cl.academic_year_id AS academicYearId, ")
				.append(" cl.meeting_time_id AS meetingTimeId, cl.capacity, cl.updated_by AS updatedBy, cl.created_on AS createdOn, cl.updated_on AS updatedOn ")
				.append("FROM public.classes cl ")				
				.append("WHERE cl.teacher_id = ? ");
		
		return this.jdbcTemplate.query(builder.toString(), new RowMapper<ClassVO>() {
			@Override
			public ClassVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ClassVO classVO = new ClassVO(rs);
				classVO.setAcademicYear(loadAcademicYear(rs.getInt(ClassConstant.CLASS_ACADEMIC_YEAR_ID)));
				classVO.setRoom(loadClassRoom(rs.getInt(ClassConstant.CLASS_ROOM_ID)));
				classVO.setCourse(loadCourse(rs.getInt(ClassConstant.CLASS_COURSE_ID)));
				classVO.setTeacher(loadTeacher(rs.getInt(ClassConstant.CLASS_TEACHER_ID)));
				classVO.setMeetingTime(loadMeetingTime(rs.getInt(ClassConstant.CLASS_MEETING_TIME_ID)));
				return classVO;
			}			
		}, teacherID);
	}
}
