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
import com.jerotoma.common.constants.ParentConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.utils.StringUtility;
import com.jerotoma.common.viewobjects.AddressVO;
import com.jerotoma.common.viewobjects.ParentVO;
import com.jerotoma.common.viewobjects.ResultBuilder;
import com.jerotoma.common.viewobjects.StudentVO;
import com.jerotoma.database.assemblers.dao.AssemblerAddressDao;
import com.jerotoma.database.assemblers.dao.AssemblerParentDao;
import com.jerotoma.database.assemblers.dao.AssemblerStudentDao;
import com.jerotoma.database.dao.DaoUtil;
import com.jerotoma.database.dao.roles.RoleDao;

@Repository
public class AssemblerParentDaoImpl extends JdbcDaoSupport implements AssemblerParentDao {
	
	private JdbcTemplate jdbcTemplate;
	private AssemblerStudentDao assemblerStudentDao;
	private Map<String, Object> map;
	
	@Autowired DataSource dataSource;
	@Autowired RoleDao roleDao;	
	@Autowired AssemblerAddressDao addressDao;	
			
	public void setAssemblerStudentDao(AssemblerStudentDao assemblerStudentDao) {
		this.assemblerStudentDao = assemblerStudentDao;
	}

	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		this.jdbcTemplate = getJdbcTemplate();
	}

	@Override
	public ParentVO findObject(Integer primaryKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE pa.id = ? ").toString();
		return this.jdbcTemplate.query(query, new ParentSingleResultProcessor(), primaryKey);
	}

	@Override
	public ParentVO findObjectUniqueKey(String uniqueKey) throws SQLException {		
		String query = getBaseSelectQuery().append("WHERE u.username = ? ").toString();
		return this.jdbcTemplate.query(query, new ParentSingleResultProcessor(), uniqueKey);
	}

	@Override
	public List<ParentVO> loadList() throws SQLException {
		StringBuilder builder = getBaseSelectQuery();
		return this.jdbcTemplate.query(builder.toString(), new ParentResultProcessor());
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		
		map = new HashMap<>();
		StringBuilder builder = getBaseSelectQuery();
		builder.append(DaoUtil.getOrderBy(queryParam.getFieldName(), queryParam.getOrderby(), "pa"))
		.append(" ")
		.append("limit ? offset ?");

		Long countResults = countObject();
		int pageCount = DaoUtil.getPageCount(queryParam.getPageSize(), countResults);
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		Object[] paramList = new Object[] {limit, offset};
		
		List<ParentVO> parents = this.jdbcTemplate.query(builder.toString(), new ParentResultProcessor(), paramList);
		map.put(ParentConstant.PARENTS, parents);
		map.put(ParentConstant.PARENT_COUNT, countResults);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		
		return map;
	}
	
	public class ParentResultProcessor implements RowMapper<ParentVO>{
		@Override
		public ParentVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return mapParentResult(rs);
		}		
	}
	
	public class ParentSingleResultProcessor implements ResultSetExtractor<ParentVO>{
		@Override
		public ParentVO extractData(ResultSet rs) throws SQLException, DataAccessException {
			ParentVO parent = null;
			if(rs.next()) {
				parent = mapParentResult(rs);								
			}
			return parent;
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
		return new StringBuilder("SELECT pa.id, pa.user_id AS userId, pa.first_name AS firstName, pa.last_name AS lastName, pa.middle_names AS middleNames,")
				.append(" pa.email_address AS emailAddress, pa.phone_number AS phoneNumber, pa.user_code AS userCode, pa.occupation, pa.gender, ")
				.append(" pa.birth_date AS birthDate, pa.updated_by AS updatedBy, pa.created_on AS createdOn, pa.updated_on AS updatedOn, ")
				.append(" u.username, u.user_type AS userType, ")
				.append(" m.src AS avatar, pa.profile_image_id AS profileImageId ")				
				.append(" FROM public.parents pa ")
				.append(" INNER JOIN users u ON u.id = pa.user_id ")
				.append(" LEFT JOIN user_media um ON um.id = pa.profile_image_id ")
				.append(" LEFT JOIN media m ON m.id = um.media_id ");		
	}
		
	@Override
	public Long countObject() throws SQLException {
		return countObject(null, null);
	}
	
	public Long countObject(String extraQuery, Object[] paramList) throws SQLException {
		StringBuilder queryBuilder = new StringBuilder("SELECT count(*) FROM public.parents pa ");
		if (!StringUtility.isEmpty(extraQuery)) {
			queryBuilder.append(extraQuery);
			return this.jdbcTemplate.query(queryBuilder.toString(), new LongResultProcessor(), paramList);
		}		
		return this.jdbcTemplate.query(queryBuilder.toString(), new LongResultProcessor());
	}
	
	
	private AddressVO loadAddress(Integer primaryKey) throws SQLException {
		return this.addressDao.findAddressByParentId(primaryKey);
	}
	
	public ParentVO mapParentResult(ResultSet rs) throws SQLException {
		ParentVO parent = new ParentVO(rs);
		parent.setAddress(loadAddress(parent.getId()));
		return parent;
	}

	@Override
	public List<StudentVO> loadStudentsByParentId(Integer parentId) throws SQLException {		
		return assemblerStudentDao.findStudentsByParentId(parentId);
	}

	@Override
	public List<ParentVO> search(QueryParam queryParam) throws SQLException {
		StringBuilder queryBuilder = getBaseSelectQuery();
		queryBuilder.append(" WHERE LOWER(pa.first_name) like ? OR LOWER(pa.last_name) like ? OR LOWER(pa.middle_names) like ? ")
				.append(DaoUtil.getOrderBy(queryParam.getFieldName(), queryParam.getOrderby(), "pa"))
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
		return this.jdbcTemplate.query(queryBuilder.toString(), new ParentResultProcessor(), paramList);
	}

	
	@Override
	public List<ParentVO> findParentsByStudentId(Integer studentId) throws SQLException {
		StringBuilder builder = getBaseSelectQuery().append(" INNER JOIN student_parents sp ON sp.parent_id = pa.id WHERE sp.student_id = ? ");
		return this.jdbcTemplate.query(builder.toString(), new ParentResultProcessor(), studentId);
	}

	@Override
	public ResultBuilder<ParentVO> loadParentMapListByStudentID(QueryParam queryParam, Integer studentId)
			throws SQLException {
		
		ResultBuilder<ParentVO> resultBuilder = new ResultBuilder<>();
		
		StringBuilder joins = new StringBuilder().append(" INNER JOIN student_parents sp ON sp.parent_id = pa.id WHERE sp.student_id = ? ");
		StringBuilder builder = getBaseSelectQuery().append(joins).append("limit ? offset ?");
		
		Long countResults = countObject(joins.toString(), new Object[] {studentId});
		int pageCount = DaoUtil.getPageCount(queryParam.getPageSize(), countResults);
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		Object[] paramList = new Object[] {studentId, limit, offset};		
		List<ParentVO> parents = jdbcTemplate.query(builder.toString(), new ParentResultProcessor(), paramList);
		
		Set<ParentVO> parentSet = new HashSet<>();
		parents.stream().forEach(parent -> {
			parentSet.add(parent);
		});
		resultBuilder.setDataList(parentSet);
		resultBuilder.setPageCount(pageCount);
		resultBuilder.setCount(countResults.intValue());
		resultBuilder.setPageSize(limit);		
		return resultBuilder;
	}

}
