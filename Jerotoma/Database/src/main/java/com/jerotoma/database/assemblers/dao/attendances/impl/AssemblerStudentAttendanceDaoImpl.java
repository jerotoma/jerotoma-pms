package com.jerotoma.database.assemblers.dao.attendances.impl;

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
import com.jerotoma.common.constants.AttendanceConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.viewobjects.StudentAttendanceVO;
import com.jerotoma.database.assemblers.dao.AssemblerStudentDao;
import com.jerotoma.database.assemblers.dao.attendances.AssemblerStudentAttendanceDao;
import com.jerotoma.database.dao.DaoUtil;

@Repository
public class AssemblerStudentAttendanceDaoImpl extends JdbcDaoSupport implements AssemblerStudentAttendanceDao {
	private JdbcTemplate jdbcTemplate;
	@Autowired DataSource dataSource;	
	@Autowired AssemblerStudentDao assemblerStudentDao;
	Map<String, Object> map;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		this.jdbcTemplate = getJdbcTemplate();
	}

	@Override
	public StudentAttendanceVO findObject(Integer primaryKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE sa.id = ? ").toString();
		return this.jdbcTemplate.query(query, new ClassAttendanceSingleResultProcessor(), primaryKey);
	}

	@Override
	public StudentAttendanceVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		throw new RuntimeException("findObjectUniqueKey has not been implemented yet");
	}

	@Override
	public List<StudentAttendanceVO> loadList(QueryParam queryParam) throws SQLException {
		StringBuilder builder = getBaseSelectQuery();
				builder.append(DaoUtil.getOrderBy(queryParam.getFieldName(), queryParam.getOrderby()))
				.append(" ")
				.append("limit ? offset ?");

		Long countResults = countObject();
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		Object[] paramList = new Object[] {limit, offset};
		
		return this.jdbcTemplate.query(builder.toString(), new ClassAttendanceResultProcessor(), paramList);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		
		map = new HashMap<>();
		StringBuilder builder = getBaseSelectQuery();
		builder.append(DaoUtil.getOrderBy("sa." + queryParam.getFieldName(), queryParam.getOrderby()))
		.append(" ")
		.append("limit ? offset ?");

		Long countResults = countObject();
		int pageCount = DaoUtil.getPageCount(queryParam.getPageSize(), countResults);
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(), countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		Object[] paramList = new Object[] {limit, offset};
		
		List<StudentAttendanceVO> studentAttendancees = this.jdbcTemplate.query(builder.toString(), new ClassAttendanceResultProcessor(), paramList);
		map.put(AttendanceConstant.STUDENT_ATTENDANCES, studentAttendancees);
		map.put(SystemConstant.COUNT, countResults);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		
		return map;
	}
	
	public class ClassAttendanceResultProcessor implements RowMapper<StudentAttendanceVO>{
		@Override
		public StudentAttendanceVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			StudentAttendanceVO studentAttendance = new StudentAttendanceVO(rs);			
			return studentAttendance;			
		}		
	}
	
	public class ClassAttendanceSingleResultProcessor implements ResultSetExtractor<StudentAttendanceVO>{
		@Override
		public StudentAttendanceVO extractData(ResultSet rs) throws SQLException, DataAccessException {
			StudentAttendanceVO studentAttendance = null;
			if(rs.next()) {
				studentAttendance = new StudentAttendanceVO(rs);
				
			}
			return studentAttendance;
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
				.append(" sa.id, sa.student_id AS studentId, sa.class_attendance_id AS classAttendanceId, sa.attendance_status_id AS attendanceStatusId, sa.added_by AS addedBy, sa.created_on AS createdOn, sa.updated_on AS updatedOn, ")
				.append(" s.first_name AS firstName, s.last_name AS lastName, ast.name AS statusName,")
				.append(" co.name AS courseName, co.id AS courseId, c.id AS classId,  ca.attendance_date AS attendanceDate, ")				
				.append(" ay.id AS academicYearId, ay.year_of_study AS yearOfStudy, ay.name AS academicYearName ")
				.append(" FROM public.student_attendances sa ")				
				.append("  INNER JOIN public.students s ON s.id = sa.student_id ")
				.append("  INNER JOIN public.attendance_statuses ast ON ast.id = sa.attendance_status_id ")
				.append("  INNER JOIN public.class_attendances ca ON ca.id = sa.class_attendance_id ")
				.append("  INNER JOIN public.classes c ON c.id = ca.class_id ")
				.append("  INNER JOIN public.courses co ON co.id = c.course_id")
				.append("  INNER JOIN public.academic_years ay ON ay.id = c.academic_year_id");
		
	}	
	
	@Override
	public Long countObject() throws SQLException {
		StringBuilder queryBuilder = new StringBuilder("SELECT count(*) FROM public.student_attendances ");
		return this.jdbcTemplate.query(queryBuilder.toString(), new LongResultProcessor());
	}
	
}
