package com.jerotoma.database.assemblers.dao.impls;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.StudentConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.viewobjects.AddressVO;
import com.jerotoma.common.viewobjects.ParentVO;
import com.jerotoma.common.viewobjects.StudentVO;
import com.jerotoma.database.assemblers.dao.AssemblerAddressDao;
import com.jerotoma.database.assemblers.dao.AssemblerParentDao;
import com.jerotoma.database.assemblers.dao.AssemblerStudentDao;
import com.jerotoma.database.dao.DaoUtil;
import com.jerotoma.database.dao.roles.RoleDao;

@Repository
public class AssemblerStudentDaoImpl extends JdbcDaoSupport implements AssemblerStudentDao {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired DataSource dataSource;
	@Autowired RoleDao roleDao;		
	@Autowired AssemblerAddressDao addressDao;
	
	@Autowired AssemblerParentDao parentDao;
	
	Map<String, Object> map;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		this.jdbcTemplate = getJdbcTemplate();
		this.parentDao.setAssemblerStudentDao(this);
		
	}

	@Override
	public StudentVO findObject(Integer primaryKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE st.id = ? ").toString();
		return this.jdbcTemplate.query(query, new StudentSingleResultProcessor(), primaryKey);
	}

	@Override
	public StudentVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE u.username = ? ").toString();
		return this.jdbcTemplate.query(query, new StudentSingleResultProcessor(), uniqueKey);
	}

	@Override
	public List<StudentVO> loadList() throws SQLException {
		StringBuilder builder = getBaseSelectQuery();
		return this.jdbcTemplate.query(builder.toString(), new StudentResultProcessor());
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		
		map = new HashMap<>();
		StringBuilder builder = getBaseSelectQuery();
		builder.append(DaoUtil.getOrderBy(queryParam.getFieldName(), queryParam.getOrderby(), "st"))
		.append(" ")
		.append("limit ? offset ?");

		Long countResults = countObject();
		int pageCount = DaoUtil.getPageCount(queryParam.getPageSize(), countResults);
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		Object[] paramList = new Object[] {limit, offset};
		
		List<StudentVO> teachers = this.jdbcTemplate.query(builder.toString(), new StudentResultProcessor(), paramList);
		map.put(StudentConstant.STUDENTS, teachers);
		map.put(StudentConstant.STUDENT_COUNT, countResults);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		
		return map;
	}
	
	public class StudentResultProcessor implements RowMapper<StudentVO>{
		@Override
		public StudentVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return mapStudentResult(rs);
		}		
	}
	
	public class StudentSingleResultProcessor implements ResultSetExtractor<StudentVO>{
		@Override
		public StudentVO extractData(ResultSet rs) throws SQLException, DataAccessException {
			StudentVO student = null;
			if(rs.next()) {
				student = mapStudentResult(rs);					
			}
			return student;
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
		return new StringBuilder("SELECT st.id, st.user_id AS userId, st.student_number AS studentNumber,")
				.append(" st.first_name AS firstName, st.last_name AS lastName, st.middle_names AS middleNames, st.email_address AS emailAddress,")
				.append(" st.phone_number as phoneNumber, st.user_code AS userCode, st.occupation, st.gender, st.position, st.birth_date AS birthDate, ")
				.append(" st.primary_parent_id as primaryParentId, st.updated_by AS updatedBy, st.created_on AS createdOn, st.updated_on AS updatedOn, ")
				.append(" u.username, u.user_type AS userType, ")
				.append(" pr.name AS programName, pr.id AS programId, ")
				.append(" al.name AS academicLevelName, al.id AS academicLevelId, ")
				.append(" m.src AS avatar, st.profile_image_id AS profileImageId ")				
				.append(" FROM public.students st  ")
				.append(" INNER JOIN users u ON u.id = st.user_id ")
				.append(" INNER JOIN programs pr ON pr.id = st.program_id ")
				.append(" LEFT JOIN student_academic_levels sal ON sal.student_id = st.id AND sal.is_current_academic_level IS TRUE")
				.append(" LEFT JOIN academic_levels al ON al.id = sal.academic_level_id ")
				.append(" LEFT JOIN user_media um ON um.id = st.profile_image_id ")
				.append(" LEFT JOIN media m ON m.id = um.media_id ");
		
	}

	@Override
	public Long countObject() throws SQLException {
		StringBuilder queryBuilder = new StringBuilder("SELECT count(*) FROM public.students");
		return this.jdbcTemplate.query(queryBuilder.toString(), new LongResultProcessor());
	}
	
	private AddressVO loadAddress(Integer primaryKey) throws SQLException {
		return this.addressDao.findAddressByStudentId(primaryKey);
	}
	
	public StudentVO mapStudentResult(ResultSet rs) throws SQLException {
		StudentVO student = new StudentVO(rs);
		student.setPrimaryParent(loadParent(rs.getInt("primaryParentId")));
		student.setAddress(loadAddress(student.getId()));
		student.setParents(loadParents(student.getId()));
		return student;
	}

	private ParentVO loadParent(Integer primaryParentId) throws SQLException {
		return parentDao.findObject(primaryParentId);
	}

	private List<ParentVO> loadParents(Integer studentId) throws SQLException {
		return parentDao.findParentsByStudentId(studentId);
	}

	@Override
	public List<StudentVO> loadStudentsByJClassID(Integer classId) {
		String query = "SELECT sal.student_id FROM public.student_academic_levels sal INNER JOIN public.student_classes sc ON sc.student_academic_level_id = sal.id WHERE sc.class_id = ?";
		
		return this.jdbcTemplate.query(query, new ArgumentPreparedStatementSetter(new Object[] {classId}), new RowMapper<StudentVO>() {
			@Override
			public StudentVO mapRow(ResultSet rs, int rowNum) throws SQLException {				
				return findObject(rs.getInt("student_id"));
			}			
		});		
	}

	@Override
	public List<StudentVO> search(QueryParam queryParam) throws SQLException {
		StringBuilder queryBuilder = getBaseSelectQuery();
		queryBuilder.append(" WHERE lower(st.first_name) like ? OR lower(st.last_name) like ? OR lower(st.middle_names) like ? ")
				.append(DaoUtil.getOrderBy(queryParam.getFieldName(), queryParam.getOrderby(), "st"))
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
		return this.jdbcTemplate.query(queryBuilder.toString(), new StudentResultProcessor(), paramList);
	}

	@Override
	public List<StudentVO> findStudentsByParentId(Integer parentId) throws SQLException {
		StringBuilder builder = getBaseSelectQuery().append(" INNER JOIN student_parents sp ON sp.student_id = st.id WHERE sp.parent_id = ?");
		return getJdbcTemplate().query(builder.toString(), new StudentResultProcessor(), parentId);
	}

	@Override
	public List<ParentVO> loadParentsByStudentId(Integer studentId) throws SQLException {
		return parentDao.findParentsByStudentId(studentId);
	}

	@Override
	public List<StudentVO> loadStudentsByProgramAndAcademicLevelIDs(Integer programId, Integer academicLevelId)
			throws SQLException {
		StringBuilder queryBuilder = getBaseSelectQuery();
		queryBuilder.append("WHERE pr.id = ? AND  al.id = ? ");
		return this.jdbcTemplate.query(queryBuilder.toString(), new StudentResultProcessor(), programId, academicLevelId);
	}

	@Override
	public List<StudentVO> findStudentsWhoAreUnenrolledAndQualifiedForThisProgramAndAcademicLevel(Integer programId,
			Integer academicLevelId) throws SQLException {
		return null;
	}
	
}
