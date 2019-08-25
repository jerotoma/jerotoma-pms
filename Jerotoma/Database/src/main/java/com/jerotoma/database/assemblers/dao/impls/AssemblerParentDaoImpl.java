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
import com.jerotoma.common.constants.ParentConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.viewobjects.AddressVO;
import com.jerotoma.common.viewobjects.ParentVO;
import com.jerotoma.database.assemblers.dao.AssemblerAddressDao;
import com.jerotoma.database.assemblers.dao.AssemblerParentDao;
import com.jerotoma.database.dao.DaoUtil;
import com.jerotoma.database.dao.roles.RoleDao;

@Repository
public class AssemblerParentDaoImpl extends JdbcDaoSupport implements AssemblerParentDao {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired DataSource dataSource;
	@Autowired RoleDao roleDao;	
	@Autowired AssemblerAddressDao addressDao;
	Map<String, Object> map;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		this.jdbcTemplate = getJdbcTemplate();
	}

	@Override
	public ParentVO findObject(Integer primaryKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE id = ? ").toString();
		return this.jdbcTemplate.query(query, new ParentSingleResultProcessor(), primaryKey);
	}

	@Override
	public ParentVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		throw new RuntimeException("findObjectUniqueKey has not been implemented yet");
	}

	@Override
	public List<ParentVO> loadList(QueryParam queryParam) throws SQLException {
		StringBuilder builder = getBaseSelectQuery();
				builder.append(DaoUtil.getOrderBy(queryParam.getFieldName(), queryParam.getOrderby()))
				.append(" ")
				.append("limit ? offset ?");

		Long countResults = countObject();
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		Object[] paramList = new Object[] {limit, offset};
		
		return this.jdbcTemplate.query(builder.toString(), new ParentResultProcessor(), paramList);
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
		
		List<ParentVO> parents = this.jdbcTemplate.query(builder.toString(), new ParentResultProcessor(), paramList);
		map.put(ParentConstant.PARENTS, parents);
		map.put(ParentConstant.PARENT_COUNT, countResults);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		
		return map;
	}
	
	public class ParentResultProcessor implements RowMapper<ParentVO>{
		@Override
		public ParentVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			ParentVO Parent = new ParentVO(rs);	
			Parent.setAddressVO(loadAddress(Parent.getId()));	
			return Parent;
		}		
	}
	
	public class ParentSingleResultProcessor implements ResultSetExtractor<ParentVO>{
		@Override
		public ParentVO extractData(ResultSet rs) throws SQLException, DataAccessException {
			ParentVO Parent = null;
			if(rs.next()) {
				Parent = new ParentVO(rs);
				Parent.setAddressVO(loadAddress(Parent.getId()));					
			}
			return Parent;
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
		return new StringBuilder("SELECT id, first_name AS firstName, last_name AS lastName, middle_names AS middleNames, email_address AS emailAddress, phone_number AS phoneNumber, occupation, gender, avatar, birth_date AS birthDate, updated_by AS updatedBy, created_on AS createdOn, updated_on AS updatedOn FROM public.parents ");
		
	}

	@Override
	public Long countObject() throws SQLException {
		StringBuilder queryBuilder = new StringBuilder("SELECT count(*) FROM public.parents");
		return this.jdbcTemplate.query(queryBuilder.toString(), new LongResultProcessor());
	}
	
	private AddressVO loadAddress(Integer primaryKey) throws SQLException {
		return this.addressDao.findAddressByParentId(primaryKey);
	}


}
