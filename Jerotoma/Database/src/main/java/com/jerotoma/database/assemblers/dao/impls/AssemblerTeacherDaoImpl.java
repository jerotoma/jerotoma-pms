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
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.constants.TeacherConstant;
import com.jerotoma.common.constants.UserConstant;
import com.jerotoma.common.viewobjects.AcademicDisciplineVO;
import com.jerotoma.common.viewobjects.AddressVO;
import com.jerotoma.common.viewobjects.PositionVO;
import com.jerotoma.common.viewobjects.TeacherVO;
import com.jerotoma.database.assemblers.dao.AssemblerAcademicDisciplineDao;
import com.jerotoma.database.assemblers.dao.AssemblerAddressDao;
import com.jerotoma.database.assemblers.dao.AssemblerPositionDao;
import com.jerotoma.database.assemblers.dao.AssemblerTeacherDao;
import com.jerotoma.database.dao.DaoUtil;
import com.jerotoma.database.dao.roles.RoleDao;

@Repository
public class AssemblerTeacherDaoImpl extends JdbcDaoSupport implements AssemblerTeacherDao {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired DataSource dataSource;
	@Autowired RoleDao roleDao;	
	@Autowired AssemblerPositionDao positionDao;
	@Autowired AssemblerAddressDao addressDao;
	@Autowired AssemblerAcademicDisciplineDao academicDisciplineDao;
	Map<String, Object> map;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		this.jdbcTemplate = getJdbcTemplate();
	}

	@Override
	public TeacherVO findObject(Integer primaryKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE id = ? ").toString();
		return this.jdbcTemplate.query(query, new TeacherSingleResultProcessor(), primaryKey);
	}

	@Override
	public TeacherVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE user_id = ? ").toString();
		return this.jdbcTemplate.query(query, new TeacherSingleResultProcessor(), Integer.valueOf(uniqueKey));
	}

	@Override
	public List<TeacherVO> loadList(QueryParam queryParam) throws SQLException {
		StringBuilder builder = getBaseSelectQuery();
				builder.append(DaoUtil.getOrderBy(queryParam.getFieldName(), queryParam.getOrderby()))
				.append(" ")
				.append("limit ? offset ?");

		Long countResults = countObject();
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		Object[] paramList = new Object[] {limit, offset};
		
		return this.jdbcTemplate.query(builder.toString(), new TeacherResultProcessor(), paramList);
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
		return new StringBuilder("SELECT id, user_id AS userId, user_code AS userCode, first_name AS firstName, last_name AS lastName, middle_names AS middleNames, email_address AS emailAddress, phone_number AS phoneNumber, occupation, gender, avatar, position_id as positionId, academic_discipline_id AS academicDisciplineId, birth_date AS birthDate, updated_by AS updatedBy, created_on AS createdOn, updated_on AS updatedOn FROM public.teachers ");
		
	}

	@Override
	public Long countObject() throws SQLException {
		StringBuilder queryBuilder = new StringBuilder("SELECT count(*) FROM public.teachers");
		return this.jdbcTemplate.query(queryBuilder.toString(), new LongResultProcessor());
	}
	
	private PositionVO loadPosition(Integer primaryKey) throws SQLException {
		return this.positionDao.findObject(primaryKey);
	}
	
	private AcademicDisciplineVO loadAcademicDiscipline(Integer primaryKey) throws SQLException {
		return this.academicDisciplineDao.findObject(primaryKey);
	}
	
	private AddressVO loadAddress(Integer primaryKey) throws SQLException {
		return this.addressDao.findAddressByTeacherId(primaryKey);
	}
	
	public TeacherVO mapStudentResult(ResultSet rs) throws SQLException {
		TeacherVO teacher = new TeacherVO(rs);
		teacher.setAddress(loadAddress(teacher.getId()));	
		teacher.setPosition(loadPosition(rs.getInt(UserConstant.POSITION_ID)));
		teacher.setAcademicDiscipline(loadAcademicDiscipline(rs.getInt(UserConstant.ACADEMIC_DISCIPLINE_ID)));
		return teacher;
	}

}
