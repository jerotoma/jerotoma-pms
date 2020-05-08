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
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.viewobjects.AcademicYearVO;
import com.jerotoma.common.viewobjects.RoomVO;
import com.jerotoma.common.viewobjects.CourseVO;
import com.jerotoma.common.viewobjects.ClassVO;
import com.jerotoma.common.viewobjects.MeetingTimeVO;
import com.jerotoma.common.viewobjects.StudentVO;
import com.jerotoma.common.viewobjects.TeacherVO;
import com.jerotoma.database.assemblers.dao.AssemblerStudentDao;
import com.jerotoma.database.assemblers.dao.AssemblerTeacherDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerAcademicYearDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerClassRoomDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerCourseDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerJClassDao;
import com.jerotoma.database.assemblers.dao.schedules.AssemblerMeetingTimeDao;
import com.jerotoma.database.dao.DaoUtil;

@Repository
public class AssemblerJClassDaoImpl extends JdbcDaoSupport implements AssemblerJClassDao {
	
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired DataSource dataSource;
	@Autowired AssemblerTeacherDao assemblerTeacherDao;	
	@Autowired AssemblerMeetingTimeDao assemblerMeetingTimeDao;	
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
	public ClassVO findObject(Integer primaryKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE c.id = ? ").toString();
		return this.jdbcTemplate.query(query, new JClassSingleResultProcessor(), primaryKey);
	}

	@Override
	public ClassVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		throw new RuntimeException("findObjectUniqueKey has not been implemented yet");
	}

	@Override
	public List<ClassVO> loadList(QueryParam queryParam) throws SQLException {
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
		
		List<ClassVO> classes = this.jdbcTemplate.query(builder.toString(), new JClassResultProcessor(), paramList);
		map.put(ClassConstant.JCLASSES, classes);
		map.put(ClassConstant.JCLASS_COUNT, countResults);
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
		return new StringBuilder("SELECT c.id, c.teacher_id AS teacherId, c.course_id AS courseId, c.class_room_id AS classRoomId, c.academic_year_id AS academicYearId, c.meeting_time_id AS meetingTimeId, c.capacity, c.updated_by AS updatedBy, c.created_on AS createdOn, c.updated_on AS updatedOn FROM public.classes c ");		
	}
	
	@Override
	public List<ClassVO> loadJClassesByStudentId(Integer studentId) {		
		String query = "SELECT src.class_id FROM public.student_classes sc INNER JOIN public.student_registered_classes src ON src.student_class_id = sc.id  WHERE sc.student_id = ?";
		
		return this.jdbcTemplate.query(query,new Object[] {studentId}, new RowMapper<ClassVO>() {
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
		jClass.setAcademicYear(loadAcademicYear(rs.getInt(ClassConstant.JCLASS_ACADEMIC_YEAR_ID)));
		jClass.setRoom(loadClassRoom(rs.getInt(ClassConstant.JCLASS_CLASS_ROOM_ID)));
		jClass.setCourse(loadCourse(rs.getInt(ClassConstant.JCLASS_COURSE_ID)));
		jClass.setTeacher(loadTeacher(rs.getInt(ClassConstant.JCLASS_TEACHER_ID)));
		jClass.setMeetingTime(loadMeetingTime(rs.getInt(ClassConstant.JCLASS_MEETING_TIME_ID)));
		jClass.setStudents(loadStudentsByJClassID(jClass.getId()));
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
	public List<ClassVO> loadJClassesByAcademicYear(Integer academicYearId) throws SQLException {
		StringBuilder queryBuilder = getBaseSelectQuery().append( " WHERE c.academic_year_id = ? ");
		return this.jdbcTemplate.query(queryBuilder.toString(), new JClassResultProcessor(), academicYearId);
	}
	@Override
	public List<ClassVO> loadStudentUnregisteredJClassesByAcademicYear(Integer academicYearId, Integer studentId)
			throws SQLException {
		StringBuilder queryBuilder = getBaseSelectQuery()				
				.append(" WHERE c.academic_year_id = ? AND c.course_id NOT IN( ")
					.append(" SELECT course_id FROM public.classes cc ")
						.append(" INNER JOIN student_registered_classes srcc ON srcc.class_id = cc.id ")
						.append(" INNER JOIN student_classes scc ON scc.id = srcc.student_class_id")
					.append(" WHERE cc.academic_year_id = ? AND scc.student_id = ? ")
				.append(")");
		return this.jdbcTemplate.query(queryBuilder.toString(), new JClassResultProcessor(), academicYearId, academicYearId, studentId);
	}
	@Override
	public List<ClassVO> loadStudentJClassesByAcademicYear(Integer studentId, Integer academicYearId)
			throws SQLException {
		StringBuilder queryBuilder = getBaseSelectQuery()
			.append(" INNER JOIN student_registered_classes src ON src.class_id = c.id ")
			.append(" INNER JOIN student_classes sc ON sc.id = src.student_class_id")
			.append(" WHERE c.academic_year_id = ? AND sc.student_id = ? ");				
		return this.jdbcTemplate.query(queryBuilder.toString(), new JClassResultProcessor(), academicYearId, studentId);
	}
}
