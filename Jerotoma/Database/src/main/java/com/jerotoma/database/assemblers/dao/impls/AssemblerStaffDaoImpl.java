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
import com.jerotoma.common.constants.StaffConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.constants.UserConstant;
import com.jerotoma.common.viewobjects.AddressVO;
import com.jerotoma.common.viewobjects.PositionVO;
import com.jerotoma.common.viewobjects.StaffVO;
import com.jerotoma.database.assemblers.dao.AssemblerAddressDao;
import com.jerotoma.database.assemblers.dao.AssemblerPositionDao;
import com.jerotoma.database.assemblers.dao.AssemblerStaffDao;
import com.jerotoma.database.dao.DaoUtil;

@Repository
public class AssemblerStaffDaoImpl extends JdbcDaoSupport implements AssemblerStaffDao {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired DataSource dataSource;
	@Autowired AssemblerAddressDao addressDao;
	@Autowired AssemblerPositionDao positionDao;
	Map<String, Object> map;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		this.jdbcTemplate = getJdbcTemplate();
	}

	@Override
	public StaffVO findObject(Integer primaryKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE sta.id = ? ").toString();
		return this.jdbcTemplate.query(query, new StaffSingleResultProcessor(), primaryKey);
	}

	@Override
	public StaffVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE u.username = ? ").toString();
		return this.jdbcTemplate.query(query, new StaffSingleResultProcessor(), uniqueKey);
	}

	@Override
	public List<StaffVO> loadList(QueryParam queryParam) throws SQLException {
		StringBuilder builder = getBaseSelectQuery();
				builder.append(DaoUtil.getOrderBy(queryParam.getFieldName(), queryParam.getOrderby()))
				.append(" ")
				.append("limit ? offset ?");

		Long countResults = countObject();
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		Object[] paramList = new Object[] {limit, offset};
		
		return this.jdbcTemplate.query(builder.toString(), new StaffResultProcessor(), paramList);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		
		map = new HashMap<>();
		StringBuilder builder = getBaseSelectQuery();
		builder.append(DaoUtil.getOrderBy(queryParam.getFieldName(), queryParam.getOrderby(), "sta"))
		.append(" ")
		.append("limit ? offset ?");

		Long countResults = countObject();
		int pageCount = DaoUtil.getPageCount(queryParam.getPageSize(), countResults);
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		Object[] paramList = new Object[] {limit, offset};
		
		List<StaffVO> teachers = this.jdbcTemplate.query(builder.toString(), new StaffResultProcessor(), paramList);
		map.put(StaffConstant.STAFFS, teachers);
		map.put(StaffConstant.STAFF_COUNT, countResults);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		
		return map;
	}
	
	public class StaffResultProcessor implements RowMapper<StaffVO>{
		@Override
		public StaffVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return mapStudentResult(rs);
		}		
	}
	
	public class StaffSingleResultProcessor implements ResultSetExtractor<StaffVO>{
		@Override
		public StaffVO extractData(ResultSet rs) throws SQLException, DataAccessException {
			StaffVO staff = null;
			if(rs.next()) {
				staff = mapStudentResult(rs);
			}
			return staff;
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
		return new StringBuilder("SELECT sta.id, u.username, sta.user_id AS userId, sta.first_name AS firstName, sta.last_name AS lastName, sta.middle_names AS middleNames, sta.email_address AS emailAddress, sta.phone_number AS phoneNumber, sta.user_code AS userCode, sta.occupation, sta.gender, sta.avatar, sta.position_id AS positionId, sta.birth_date AS birthDate, sta.updated_by AS updatedBy, sta.created_on AS createdOn, sta.updated_on AS updatedOn FROM public.staffs sta INNER JOIN users u ON u.id = sta.user_id ");
		
	}

	@Override
	public Long countObject() throws SQLException {
		StringBuilder queryBuilder = new StringBuilder("SELECT count(*) FROM public.staffs");
		return this.jdbcTemplate.query(queryBuilder.toString(), new LongResultProcessor());
	}
	
	private AddressVO loadAddress(Integer primaryKey) throws SQLException {
		return this.addressDao.findAddressByStaffId(primaryKey);
	}
	
	private PositionVO loadPosition(Integer primaryKey) throws SQLException {
		return this.positionDao.findObject(primaryKey);
	}
	
	public StaffVO mapStudentResult(ResultSet rs) throws SQLException {
		StaffVO staff = new StaffVO(rs);
		staff.setAddress(loadAddress(staff.getId()));
		staff.setPosition(loadPosition(rs.getInt(UserConstant.POSITION_ID)));
		return staff;
	}

	@Override
	public List<StaffVO> search(QueryParam queryParam) throws SQLException {
		StringBuilder queryBuilder = getBaseSelectQuery();
		queryBuilder.append(" WHERE lower(sta.first_name) like ? OR lower(sta.last_name) like ? OR lower(sta.middle_names) like ? ")
				.append(DaoUtil.getOrderBy(queryParam.getFieldName(), queryParam.getOrderby(), "sta"))
				.append(" ")
				.append("limit ? offset ?");
		
		Long countResults = countObject();
		//int pageCount = DaoUtil.getPageCount(queryParam.getPageSize(), countResults);
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		Object[] paramList = new Object[] {				
				DaoUtil.addPercentBothSide(queryParam.getSearch()),
				DaoUtil.addPercentBothSide(queryParam.getSearch()),
				DaoUtil.addPercentBothSide(queryParam.getSearch()),
				limit, 
				offset
		};
		return this.jdbcTemplate.query(queryBuilder.toString(), new StaffResultProcessor(), paramList);
	}

}
