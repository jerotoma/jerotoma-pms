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
import com.jerotoma.common.viewobjects.AcademicYearVO;
import com.jerotoma.common.viewobjects.JClassVO;
import com.jerotoma.common.viewobjects.StudentClassVO;
import com.jerotoma.common.viewobjects.StudentVO;
import com.jerotoma.database.assemblers.dao.AssemblerStudentDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerAcademicYearDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerJClassDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerStudentClassDao;
import com.jerotoma.database.dao.DaoUtil;

@Repository
public class AssemblerStudentClassDaoImpl extends JdbcDaoSupport implements AssemblerStudentClassDao {
	
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired DataSource dataSource;
	@Autowired AssemblerJClassDao assemblerJClasseDao;
	@Autowired AssemblerStudentDao assemblerStudentDao;	
	@Autowired AssemblerAcademicYearDao assemblerAcademicYearDao;
	
	
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
		throw new RuntimeException("findObjectUniqueKey has not been implemented yet");
	}

	@Override
	public List<StudentClassVO> loadList(QueryParam queryParam) throws SQLException {
		StringBuilder builder = getBaseSelectQuery();
				builder.append(DaoUtil.getOrderBy(queryParam.getFieldName(), queryParam.getOrderby()))
				.append(" ")
				.append("limit ? offset ?");

		Long countResults = countObject();
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		Object[] paramList = new Object[] {limit, offset};
		
		return this.jdbcTemplate.query(builder.toString(), new StudentClassResultProcessor(), paramList);
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
		return new StringBuilder("SELECT id, student_id AS studentId, class_id AS classId, academic_year_id AS academicYearId, updated_by AS updatedBy, created_on AS createdOn, updated_on AS updatedOn FROM public.student_classes ");
		
	}

	@Override
	public Long countObject() throws SQLException {
		StringBuilder queryBuilder = new StringBuilder("SELECT count(*) FROM public.student_classes ");
		return this.jdbcTemplate.query(queryBuilder.toString(), new LongResultProcessor());
	}
	
	
	public StudentClassVO mapStudentClassResult(ResultSet rs) throws SQLException {
		StudentClassVO jClass = new StudentClassVO(rs);
		jClass.setAcademicYear(loadAcademicYear(rs.getInt(StudentConstant.Class.ACADEMIC_YEAR_ID)));
		jClass.setjClass(loadJClass(rs.getInt(StudentConstant.Class.JCLASS_ID)));
		jClass.setStudent(loadStudentsByStudentID(rs.getInt(StudentConstant.Class.STUDENT_ID)));
		return jClass;
	}
	
	private JClassVO loadJClass(Integer jClassId) throws SQLException {		
		return assemblerJClasseDao.findObject(jClassId);
	}
	private StudentVO loadStudentsByStudentID(Integer studentId) throws SQLException {		
		return assemblerStudentDao.findObject(studentId);
	}
	private AcademicYearVO loadAcademicYear(Integer academicYearId) throws SQLException {		
		return assemblerAcademicYearDao.findObject(academicYearId);
	}
}
