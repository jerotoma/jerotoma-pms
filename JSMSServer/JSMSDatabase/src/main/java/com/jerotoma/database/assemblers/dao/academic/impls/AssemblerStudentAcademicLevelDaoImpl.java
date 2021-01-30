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
import com.jerotoma.common.viewobjects.StudentAcademicLevelVO;
import com.jerotoma.common.viewobjects.StudentVO;
import com.jerotoma.common.viewobjects.TeacherVO;
import com.jerotoma.database.assemblers.dao.AssemblerStudentDao;
import com.jerotoma.database.assemblers.dao.AssemblerTeacherDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerAcademicLevelDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerAcademicYearDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerCourseDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerClassDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerRoomDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerStudentAcademicLevelDao;
import com.jerotoma.database.assemblers.dao.schedules.AssemblerMeetingTimeDao;
import com.jerotoma.database.dao.DaoUtil;

@Repository
public class AssemblerStudentAcademicLevelDaoImpl extends JdbcDaoSupport implements AssemblerStudentAcademicLevelDao {
	
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired DataSource dataSource;
	@Autowired AssemblerClassDao assemblerJClasseDao;
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
	public StudentAcademicLevelVO findObject(Integer primaryKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE sal.id = ? ").toString();
		return this.jdbcTemplate.query(query, new StudentClassSingleResultProcessor(), primaryKey);
	}

	@Override
	public StudentAcademicLevelVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		throw new RuntimeException("findObjectUniqueKey has not been implemented yet");
	}

	@Override
	public List<StudentAcademicLevelVO> loadList() throws SQLException {
		StringBuilder builder = getBaseSelectQuery();				
		return this.jdbcTemplate.query(builder.toString(), new StudentClassResultProcessor());
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		
		map = new HashMap<>();
		StringBuilder builder = getBaseSelectQuery()
		.append(DaoUtil.getOrderBy(" sal.student_id, " + queryParam.getFieldName(), queryParam.getOrderby()))
		.append(" ")
		.append("limit ? offset ?");

		Long countResults = countObject();
		int pageCount = DaoUtil.getPageCount(queryParam.getPageSize(), countResults);
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		Object[] paramList = new Object[] {limit, offset};
		
		List<StudentAcademicLevelVO> classes = this.jdbcTemplate.query(builder.toString(), new StudentClassResultProcessor(), paramList);
		map.put(StudentConstant.Class.STUDENT_CLASSES, classes);
		map.put(StudentConstant.Class.COUNT, countResults);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		
		return map;
	}
	
	public class StudentClassResultProcessor implements RowMapper<StudentAcademicLevelVO>{
		@Override
		public StudentAcademicLevelVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return mapStudentClassResult(rs);
		}		
	}
	
	public class StudentClassSingleResultProcessor implements ResultSetExtractor<StudentAcademicLevelVO>{
		@Override
		public StudentAcademicLevelVO extractData(ResultSet rs) throws SQLException, DataAccessException {
			StudentAcademicLevelVO jClass = null;
			if(rs.next()) {
				jClass = mapStudentClassResult(rs);	
				jClass.setJClasses(findStudentClassesByStudentIdAndAndAcademicLevelID(jClass.getStudent().getId(), jClass.getAcademicLevel().getId(), jClass.getAcademicYear().getId()));
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
		return new StringBuilder("SELECT sal.id, sal.student_id AS studentId, sal.academic_year_id AS academicYearId, sal.completion_status_id AS completionStatusId, ")
				.append("(SELECT COUNT(*) FROM public.student_classes sc WHERE sc.student_academic_level_id = sal.id) AS classesCount, ")
				.append("sal.academic_level_id AS academicLevelId, sal.updated_by AS updatedBy, sal.created_on AS createdOn, sal.updated_on AS updatedOn ")
				.append("FROM public.student_academic_levels sal ");
		
	}

	@Override
	public Long countObject() throws SQLException {
		StringBuilder queryBuilder = new StringBuilder("SELECT count(DISTINCT student_academic_level_id) FROM public.student_classes ");
		return this.jdbcTemplate.query(queryBuilder.toString(), new LongResultProcessor());
	}
	
	
	public StudentAcademicLevelVO mapStudentClassResult(ResultSet rs) throws SQLException {
		StudentAcademicLevelVO studentAcademicLevel = new StudentAcademicLevelVO(rs);
		Integer studentId = rs.getInt(StudentConstant.Class.STUDENT_ID);		
		int academiLevelId = rs.getInt(StudentConstant.Class.ACADEMIC_LEVEL_ID);
		int academicYearId = rs.getInt(StudentConstant.Class.ACADEMIC_YEAR_ID);
		studentAcademicLevel.setAcademicYear(loadAcademicYear(academicYearId));
		studentAcademicLevel.setAcademicLevel(loadAcademicLevel(academiLevelId));		
		studentAcademicLevel.setStudent(loadStudentsByStudentID(studentId));
		return studentAcademicLevel;
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
	public List<StudentAcademicLevelVO> findStudentClassByParams(Integer studentId, Integer classId) {
		String query = getBaseSelectQuery()
				.append("INNER JOIN public.student_classes src ON sc.student_academic_level_id = sal.id")
				.append("WHERE sc.student_id = ? AND src.class_id = ? ").toString();
		return this.jdbcTemplate.query(query, new StudentClassResultProcessor(), studentId, classId);
	}
	@Override
	public List<StudentAcademicLevelVO> findStudentAcademicLevelsByStudentId(Integer studentId) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE sal.student_id = ? ").toString();
		return this.jdbcTemplate.query(query, new StudentClassResultProcessor(), studentId);
	}
	
	@Override
	public List<ClassVO> findStudentClassesByStudentIdAndAndAcademicLevelID(Integer studentId, Integer academicLevelId, Integer academicYearId)
			throws SQLException {
		
		StringBuilder builder = getBaseClassesSQL()			
				.append("WHERE sal.student_id = ? AND sal.academic_level_id = ? AND sal.academic_year_id = ? ");
		
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
		}, studentId, academicLevelId, academicYearId);
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
	@Override
	public List<ClassVO> findStudentClassesByStudentIdAndAndAcademicLevelID(Integer studentId,
			Integer academicLevelId) {
		StringBuilder builder = getBaseClassesSQL().append("WHERE sal.student_id = ? AND sal.academic_level_id = ? ");;
		
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
	protected StringBuilder getBaseClassesSQL() {
		return new StringBuilder("SELECT ")
				.append(" cl.id, cl.teacher_id AS teacherId, cl.course_id AS courseId, cl.room_id AS roomId, cl.academic_year_id AS academicYearId, ")
				.append("(SELECT count(sal.student_id) FROM public.student_academic_levels sal INNER JOIN public.student_classes sc ON sc.student_academic_level_id = sal.id AND sc.class_id = cl.id) AS totalStudents, ")
				.append(" cl.meeting_time_id AS meetingTimeId, cl.capacity, cl.updated_by AS updatedBy, cl.created_on AS createdOn, cl.updated_on AS updatedOn ")
				.append("FROM public.classes cl ")
				.append("INNER JOIN public.student_classes sc ON sc.class_id = cl.id ")
				.append("INNER JOIN public.student_academic_levels sal ON sal.id = sc.student_academic_level_id ");			
	}
}
