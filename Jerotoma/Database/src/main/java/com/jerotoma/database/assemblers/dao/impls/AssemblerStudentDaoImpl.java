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
	public List<StudentVO> loadList(QueryParam queryParam) throws SQLException {
		StringBuilder builder = getBaseSelectQuery();
				builder.append(DaoUtil.getOrderBy(queryParam.getFieldName(), queryParam.getOrderby(), "st"))
				.append(" ")
				.append("limit ? offset ?");

		Long countResults = countObject();
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		Object[] paramList = new Object[] {limit, offset};
		
		return this.jdbcTemplate.query(builder.toString(), new StudentResultProcessor(), paramList);
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
		return new StringBuilder("SELECT st.id, u.username, st.user_id AS userId, st.student_number AS studentNumber, st.first_name AS firstName, st.last_name AS lastName, st.middle_names AS middleNames, st.email_address AS emailAddress, st.phone_number as phoneNumber, st.user_code AS userCode, st.occupation, st.gender, st.avatar, st.position, st.birth_date AS birthDate, st.updated_by AS updatedBy, st.created_on AS createdOn, st.updated_on AS updatedOn FROM public.students st INNER JOIN users u ON u.id = st.user_id ");
		
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
		student.setAddress(loadAddress(student.getId()));
		student.setParents(loadParents(student.getId()));
		return student;
	}

	private List<ParentVO> loadParents(Integer studentId) throws SQLException {
		return parentDao.findParentsByStudentId(studentId);
	}

	@Override
	public List<StudentVO> loadStudentsByJClassID(Integer classId) {
		String query = "SELECT student_id FROM public.student_classes WHERE class_id = ?";
		
		return this.jdbcTemplate.query(query,new Object[] {classId}, new RowMapper<StudentVO>() {
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
	
}
