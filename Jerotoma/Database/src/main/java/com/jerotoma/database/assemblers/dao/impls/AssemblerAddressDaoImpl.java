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
import com.jerotoma.common.constants.AddressConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.viewobjects.AddressVO;
import com.jerotoma.database.assemblers.dao.AssemblerAddressDao;
import com.jerotoma.database.dao.DaoUtil;

@Repository
public class AssemblerAddressDaoImpl extends JdbcDaoSupport implements AssemblerAddressDao {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired DataSource dataSource;
	Map<String, Object> map;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		this.jdbcTemplate = getJdbcTemplate();
	}

	@Override
	public AddressVO findObject(Integer primaryKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE id = ? ").toString();
		return this.jdbcTemplate.query(query, new AddressSingleResultProcessor(), primaryKey);
	}

	@Override
	public AddressVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		throw new RuntimeException("findObjectUniqueKey has not been implemented yet");
	}

	@Override
	public List<AddressVO> loadList(QueryParam queryParam) throws SQLException {
		StringBuilder builder = getBaseSelectQuery();
				builder.append(DaoUtil.getOrderBy(queryParam.getFieldName(), queryParam.getOrderby()))
				.append(" ")
				.append("limit ? offset ?");

		Long countResults = countObject();
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		Object[] paramList = new Object[] {limit, offset};
		
		return this.jdbcTemplate.query(builder.toString(), new AddressResultProcessor(), paramList);
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
		
		List<AddressVO> addresses = this.jdbcTemplate.query(builder.toString(), new AddressResultProcessor(), paramList);
		map.put(AddressConstant.ADDRESSES, addresses);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		
		return map;
	}
	
	public class AddressResultProcessor implements RowMapper<AddressVO>{
		@Override
		public AddressVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new AddressVO(rs);
		}		
	}
	
	public class AddressSingleResultProcessor implements ResultSetExtractor<AddressVO>{
		@Override
		public AddressVO extractData(ResultSet rs) throws SQLException, DataAccessException {
			AddressVO address = null;
			if(rs.next()) {
				address = new AddressVO(rs);			
			}
			return address;
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
		return new StringBuilder("SELECT a.id, a.street, a.unit, a.city, a.country, a.state, a.postal_code AS postalCode , a.updated_by AS updatedBy, a.created_on AS createdOn, a.updated_on AS updatedOn FROM public.addresses a ");
		
	}

	@Override
	public Long countObject() throws SQLException {
		StringBuilder queryBuilder = new StringBuilder("SELECT count(*) FROM public.addresses ");
		return this.jdbcTemplate.query(queryBuilder.toString(), new LongResultProcessor());
	}
	
	@Override
	public AddressVO findAddressByStudentId(Integer studentId) throws SQLException {
		StringBuilder builder =  getBaseSelectQuery();
		builder
		.append("INNER JOIN public.student_addresses sa ON sa.student_id = a.id ")
		.append("WHERE sa.student_id = ?");
		return this.jdbcTemplate.query(builder.toString(), new AddressSingleResultProcessor(), studentId);
	}

	@Override
	public AddressVO findAddressByTeacherId(Integer teacherId) throws SQLException {
		StringBuilder builder =  getBaseSelectQuery();
		builder
		.append("INNER JOIN public.teacher_addresses ta ON ta.teacher_id = a.id ")
		.append("WHERE ta.teacher_id = ?");
		return this.jdbcTemplate.query(builder.toString(), new AddressSingleResultProcessor(), teacherId);
	}

	@Override
	public AddressVO findAddressByParentId(Integer parentId) throws SQLException {
		StringBuilder builder =  getBaseSelectQuery();
		builder
		.append("INNER JOIN public.parent_addresses pa ON pa.parent_id = a.id ")
		.append("WHERE pa.parent_id = ?");
		return this.jdbcTemplate.query(builder.toString(), new AddressSingleResultProcessor(), parentId);
	}

	@Override
	public AddressVO findAddressByStaffId(Integer staffId) throws SQLException {
		StringBuilder builder =  getBaseSelectQuery();
		builder
		.append("INNER JOIN public.staff_addresses sa ON sa.staff_id = a.id ")
		.append("WHERE sa.staff_id = ?");
		return this.jdbcTemplate.query(builder.toString(), new AddressSingleResultProcessor(), staffId);
	}

}
