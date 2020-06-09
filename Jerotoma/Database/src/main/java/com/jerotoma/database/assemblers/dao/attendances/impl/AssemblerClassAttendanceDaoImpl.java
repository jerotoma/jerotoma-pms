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
import com.jerotoma.common.viewobjects.AcademicYearVO;
import com.jerotoma.common.viewobjects.ClassAttendanceVO;
import com.jerotoma.common.viewobjects.ClassVO;
import com.jerotoma.database.assemblers.dao.academic.AssemblerAcademicYearDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerJClassDao;
import com.jerotoma.database.assemblers.dao.attendances.AssemblerClassAttendanceDao;
import com.jerotoma.database.dao.DaoUtil;

@Repository
public class AssemblerClassAttendanceDaoImpl extends JdbcDaoSupport implements AssemblerClassAttendanceDao {

private JdbcTemplate jdbcTemplate;
	
	@Autowired DataSource dataSource;
	@Autowired AssemblerJClassDao assemblerJClassDao;
	@Autowired AssemblerAcademicYearDao assemblerAcademicYearDao;
	Map<String, Object> map;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		this.jdbcTemplate = getJdbcTemplate();
	}

	@Override
	public ClassAttendanceVO findObject(Integer primaryKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE id = ? ").toString();
		return this.jdbcTemplate.query(query, new ClassAttendanceSingleResultProcessor(), primaryKey);
	}

	@Override
	public ClassAttendanceVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		throw new RuntimeException("findObjectUniqueKey has not been implemented yet");
	}

	@Override
	public List<ClassAttendanceVO> loadList(QueryParam queryParam) throws SQLException {
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
		builder.append(DaoUtil.getOrderBy(queryParam.getFieldName(), queryParam.getOrderby()))
		.append(" ")
		.append("limit ? offset ?");

		Long countResults = countObject();
		int pageCount = DaoUtil.getPageCount(queryParam.getPageSize(), countResults);
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		Object[] paramList = new Object[] {limit, offset};
		
		List<ClassAttendanceVO> classAttendancees = this.jdbcTemplate.query(builder.toString(), new ClassAttendanceResultProcessor(), paramList);
		map.put(AttendanceConstant.CLASS_ATTENDANCES, classAttendancees);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		
		return map;
	}
	
	public class ClassAttendanceResultProcessor implements RowMapper<ClassAttendanceVO>{
		@Override
		public ClassAttendanceVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			ClassAttendanceVO classAttendance = new ClassAttendanceVO(rs);			
			classAttendance.setAcademicYear(loadAcademicYearById(rs.getInt("academicYearId")));
			classAttendance.setMClass(loadClassById(rs.getInt("classId")));		
			return classAttendance;			
		}		
	}
	
	public class ClassAttendanceSingleResultProcessor implements ResultSetExtractor<ClassAttendanceVO>{
		@Override
		public ClassAttendanceVO extractData(ResultSet rs) throws SQLException, DataAccessException {
			ClassAttendanceVO classAttendance = null;
			if(rs.next()) {
				classAttendance = new ClassAttendanceVO(rs);
				classAttendance.setAcademicYear(loadAcademicYearById(rs.getInt("academicYearId")));
				classAttendance.setMClass(loadClassById(rs.getInt("classId")));	
			}
			return classAttendance;
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
		return new StringBuilder("SELECT ca.id, ca.class_id AS classId, ca.attendance_date AS attendanceDate, ca.academic_year_id AS academicYearId, ca.added_by AS addedBy, ca.created_on AS createdOn, ca.updated_on AS updatedOn FROM public.class_attendances ca ");
		
	}

	public ClassVO loadClassById(Integer classId) throws SQLException {		
		return assemblerJClassDao.findObject(classId);
	}
	
	public AcademicYearVO loadAcademicYearById(Integer academicYearId) throws SQLException {		
		return assemblerAcademicYearDao.findObject(academicYearId);
	}

	@Override
	public Long countObject() throws SQLException {
		StringBuilder queryBuilder = new StringBuilder("SELECT count(*) FROM public.class_attendances ");
		return this.jdbcTemplate.query(queryBuilder.toString(), new LongResultProcessor());
	}
	
}
