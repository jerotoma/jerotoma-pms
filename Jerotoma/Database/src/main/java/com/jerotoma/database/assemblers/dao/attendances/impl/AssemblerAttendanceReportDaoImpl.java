package com.jerotoma.database.assemblers.dao.attendances.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.jerotoma.common.viewobjects.AttendanceReport;
import com.jerotoma.database.assemblers.dao.attendances.AssemblerAttendanceReportDao;

@Repository
public class AssemblerAttendanceReportDaoImpl  extends JdbcDaoSupport implements AssemblerAttendanceReportDao {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired DataSource dataSource;
	Map<String, Object> map;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		this.jdbcTemplate = getJdbcTemplate();
	}

	@Override
	public List<AttendanceReport> loadAttendanceReportsByStudentID(Integer studentId, Integer academicLevelId, Integer attendanceStatusID) throws SQLException {
		StringBuilder builder = AssemblerAttendanceConstant.getAttendanceReportBaseSelectQuery();
		builder.append(" WHERE s.id = ? AND co.academic_level_id  = ?");
		return this.jdbcTemplate.query(builder.toString(), new RowMapper<AttendanceReport>() {
			@Override
			public AttendanceReport mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new AttendanceReport(rs);
			}}, studentId, attendanceStatusID, studentId, academicLevelId);
	}
	
	@Override
	public List<AttendanceReport> loadAttendanceReportDetailsByStudentID(Integer studentId, Integer academicLevelId) throws SQLException {
		StringBuilder builder = AssemblerAttendanceConstant.getAttendanceReportDetailsBaseSelectQuery();
		builder.append(" WHERE s.id = ? AND co.academic_level_id  = ?");
		return this.jdbcTemplate.query(builder.toString(), new RowMapper<AttendanceReport>() {
				@Override
				public AttendanceReport mapRow(ResultSet rs, int rowNum) throws SQLException {
					return new AttendanceReport(rs);
				}
			}, studentId, academicLevelId);
	}

}
