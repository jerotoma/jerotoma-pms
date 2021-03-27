package com.jerotoma.database.assemblers.dao.impls;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.constants.TeacherConstant;
import com.jerotoma.common.constants.UserConstant;
import com.jerotoma.common.utils.StringUtility;
import com.jerotoma.common.viewobjects.AddressVO;
import com.jerotoma.common.viewobjects.DepartmentVO;
import com.jerotoma.common.viewobjects.PositionVO;
import com.jerotoma.common.viewobjects.ResultBuilder;
import com.jerotoma.common.viewobjects.TeacherVO;
import com.jerotoma.database.assemblers.dao.AssemblerAddressDao;
import com.jerotoma.database.assemblers.dao.AssemblerPositionDao;
import com.jerotoma.database.assemblers.dao.AssemblerTeacherDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerDepartmentDao;
import com.jerotoma.database.dao.DaoUtil;
import com.jerotoma.database.dao.roles.RoleDao;

@Repository
public class AssemblerTeacherDaoImpl extends JdbcDaoSupport implements AssemblerTeacherDao {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired DataSource dataSource;
	@Autowired RoleDao roleDao;	
	@Autowired AssemblerPositionDao positionDao;
	@Autowired AssemblerAddressDao addressDao;
	@Autowired AssemblerDepartmentDao assemblerDepartmentDao;
	Map<String, Object> map;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		this.jdbcTemplate = getJdbcTemplate();
	}

	@Override
	public TeacherVO findObject(Integer primaryKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE t.id = ? ").toString();
		return this.jdbcTemplate.query(query, new TeacherSingleResultProcessor(), primaryKey);
	}

	@Override
	public TeacherVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE u.username = ? ").toString();
		return this.jdbcTemplate.query(query, new TeacherSingleResultProcessor(), uniqueKey);
	}

	@Override
	public List<TeacherVO> loadList() throws SQLException {
		StringBuilder builder = getBaseSelectQuery();
		return this.jdbcTemplate.query(builder.toString(), new TeacherResultProcessor());
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		
		map = new HashMap<>();
		StringBuilder builder = getBaseSelectQuery();
		builder.append(DaoUtil.getOrderBy(queryParam.getFieldName(), queryParam.getOrderby(), "t"))
		.append(" ")
		.append("limit ? offset ?");

		Long countResults = countObject();
		int pageCount = DaoUtil.getPageCount(queryParam.getPageSize(), countResults);
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		Object[] paramList = new Object[] {limit, offset};
		
		List<TeacherVO> teachers = this.jdbcTemplate.query(builder.toString(), new TeacherResultProcessor(), paramList);
		map.put(TeacherConstant.TEACHERS, teachers);
		map.put(TeacherConstant.TEACHER_COUNT, countResults);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		
		return map;
	}
	
	public class TeacherResultProcessor implements RowMapper<TeacherVO>{
		@Override
		public TeacherVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return mapStudentResult(rs);	
		}		
	}
	
	public class TeacherSingleResultProcessor implements ResultSetExtractor<TeacherVO>{
		@Override
		public TeacherVO extractData(ResultSet rs) throws SQLException, DataAccessException {
			TeacherVO teacher = null;
			if(rs.next()) {
				teacher = mapStudentResult(rs);				
			}
			return teacher;
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
		return new StringBuilder("SELECT t.id, t.user_id AS userId, t.user_code AS userCode, t.first_name AS firstName, ")
				.append(" t.last_name AS lastName, t.middle_names AS middleNames, t.email_address AS emailAddress, t.phone_number AS phoneNumber, ")
				.append(" t.occupation, t.gender, t.position_id as positionId, t.department_id AS departmentId, t.birth_date AS birthDate, t.updated_by AS updatedBy, ")
				.append(" t.created_on AS createdOn, t.updated_on AS updatedOn, ")
				.append(" u.username, u.user_type AS userType, ")
				.append(" m.src AS avatar, t.profile_image_id AS profileImageId ")				
				.append(" FROM public.teachers t ")
				.append(" INNER JOIN users u ON u.id = t.user_id  ")
				.append(" LEFT JOIN user_media um ON um.id = t.profile_image_id ")
				.append(" LEFT JOIN media m ON m.id = um.media_id ");
		
	}

	@Override
	public Long countObject() throws SQLException {
		return countObject(null, null);
	}
	
	public Long countObject(String extraQuery, Object[] paramList) throws SQLException {
		StringBuilder queryBuilder = new StringBuilder("SELECT count(*) FROM public.teachers t ");
		if (!StringUtility.isEmpty(extraQuery)) {
			queryBuilder.append(extraQuery);
			return this.jdbcTemplate.query(queryBuilder.toString(), new LongResultProcessor(), paramList);
		}		
		return this.jdbcTemplate.query(queryBuilder.toString(), new LongResultProcessor());
	}
	
	private PositionVO loadPosition(Integer primaryKey) throws SQLException {
		return this.positionDao.findObject(primaryKey);
	}
	
	private DepartmentVO loadDepartment(Integer primaryKey) throws SQLException {
		return this.assemblerDepartmentDao.findObject(primaryKey);
	}
	
	private AddressVO loadAddress(Integer primaryKey) throws SQLException {
		return this.addressDao.findAddressByTeacherId(primaryKey);
	}
	
	public TeacherVO mapStudentResult(ResultSet rs) throws SQLException {
		TeacherVO teacher = new TeacherVO(rs);
		teacher.setAddress(loadAddress(teacher.getId()));	
		teacher.setPosition(loadPosition(rs.getInt(UserConstant.POSITION_ID)));
		teacher.setDepartment(loadDepartment(rs.getInt(UserConstant.DEPARTMENT_ID)));
		return teacher;
	}

	@Override
	public List<TeacherVO> loadTeachersByAcademicDisciplineID(Integer academicDisciplineID) throws SQLException {
		StringBuilder query =  getBaseSelectQuery()
				.append(" INNER JOIN academic_disciplines ad ON ad.id = t.academic_discipline_id WHERE ad.id = ? ");
		return this.jdbcTemplate.query(query.toString(), new TeacherResultProcessor(), academicDisciplineID);
	}

	@Override
	public List<TeacherVO> loadTeachersByCourseID(Integer courseID) throws SQLException {
		StringBuilder query =  getBaseSelectQuery()				
				.append(" INNER JOIN courses c ON c.department_id = t.department_id WHERE c.id = ? ");
		return this.jdbcTemplate.query(query.toString(), new TeacherResultProcessor(), courseID);
	}

	@Override
	public List<TeacherVO> findAllTeachers() throws SQLException {
		StringBuilder builder = getBaseSelectQuery();
		return this.jdbcTemplate.query(builder.toString(), new TeacherResultProcessor());
	}

	@Override
	public List<TeacherVO> search(QueryParam queryParam) throws SQLException {
		StringBuilder queryBuilder = getBaseSelectQuery();
		queryBuilder.append(" WHERE lower(t.first_name) like ? OR lower(t.last_name) like ? OR lower(t.middle_names) like ? ")
				.append(DaoUtil.getOrderBy(queryParam.getFieldName(), queryParam.getOrderby(), "t"))
				.append(" ")
				.append("limit ? offset ?");
		
		Long countResults = countObject();
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		Object[] paramList = new Object[] {				
				DaoUtil.addPercentBothSide(queryParam.getSearch()),
				DaoUtil.addPercentBothSide(queryParam.getSearch()),
				DaoUtil.addPercentBothSide(queryParam.getSearch()),
				limit, 
				offset
		};
		return this.jdbcTemplate.query(queryBuilder.toString(), new TeacherResultProcessor(), paramList);
	}

	@Override
	public ResultBuilder<TeacherVO> loadTeacherMapListByStudentID(QueryParam queryParam, Integer studentId)
			throws SQLException {
		ResultBuilder<TeacherVO> resultBuilder = new ResultBuilder<>();
		StringBuilder builder = getBaseSelectQuery();
				
		StringBuilder joins = new StringBuilder().append(" INNER JOIN public.classes cl ON cl.teacher_id = t.id ")
				.append(" INNER JOIN student_registered_classes srcc ON srcc.class_id = cl.id ")
				.append(" INNER JOIN student_classes scc ON scc.id = srcc.student_class_id")
				.append(" WHERE scc.student_id = ? ");		
		
		builder.append(joins);
		builder.append(DaoUtil.getOrderBy(queryParam.getFieldName(), queryParam.getOrderby(), "t"))
		.append(" ")
		.append("limit ? offset ?");

		Long countResults = countObject(joins.toString(), new Object[] {studentId});
		int pageCount = DaoUtil.getPageCount(queryParam.getPageSize(), countResults);
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		Object[] paramList = new Object[] {studentId, limit, offset};
		
		List<TeacherVO> teachers = this.jdbcTemplate.query(builder.toString(), new TeacherResultProcessor(), paramList);
		Set<TeacherVO> teacherSet = new HashSet<>();
		teachers.stream().forEach(teacher -> {
			teacherSet.add(teacher);
		});
		resultBuilder.setDataList(teacherSet);
		resultBuilder.setPageCount(pageCount);
		resultBuilder.setCount(countResults.intValue());
		resultBuilder.setPageSize(limit);		
		return resultBuilder;
	}

	@Override
	public Integer countTeacherClasses(Integer teacherId) throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM public.classes cl WHERE cl.teacher_id = ? ");
		return this.jdbcTemplate.query(sql.toString(), new LongResultProcessor(), teacherId).intValue();
	}


}
