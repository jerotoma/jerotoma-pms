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
import com.jerotoma.common.constants.AcademicYearConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.viewobjects.AcademicYearVO;
import com.jerotoma.database.assemblers.dao.academic.AssemblerAcademicYearDao;
import com.jerotoma.database.dao.DaoUtil;

@Repository
public class AssemblerAcademicYearDaoImpl extends JdbcDaoSupport implements AssemblerAcademicYearDao {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired DataSource dataSource;
	Map<String, Object> map;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		this.jdbcTemplate = getJdbcTemplate();
	}

	@Override
	public AcademicYearVO findObject(Integer primaryKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE id = ? ").toString();
		return this.jdbcTemplate.query(query, new AcademicYearSingleResultProcessor(), primaryKey);
	}

	@Override
	public AcademicYearVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE code = ? ").toString();
		return this.jdbcTemplate.query(query, new AcademicYearSingleResultProcessor(), uniqueKey);
	}

	@Override
	public List<AcademicYearVO> loadList() throws SQLException {
		StringBuilder builder = getBaseSelectQuery();		
		return this.jdbcTemplate.query(builder.toString(), new AcademicYearResultProcessor());
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
		
		List<AcademicYearVO> academicYears = this.jdbcTemplate.query(builder.toString(), new AcademicYearResultProcessor(), paramList);
		map.put(AcademicYearConstant.ACADEMIC_YEARS, academicYears);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		
		return map;
	}
	
	public class AcademicYearResultProcessor implements RowMapper<AcademicYearVO>{
		@Override
		public AcademicYearVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			AcademicYearVO academicYear = new AcademicYearVO(rs);
					
			return academicYear;
		}		
	}
	
	public class AcademicYearSingleResultProcessor implements ResultSetExtractor<AcademicYearVO>{
		@Override
		public AcademicYearVO extractData(ResultSet rs) throws SQLException, DataAccessException {
			AcademicYearVO academicYear = null;
			if(rs.next()) {
				academicYear = new AcademicYearVO(rs);			
			}
			return academicYear;
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
		return new StringBuilder("SELECT id, year_of_study AS yearOfStudy, code, name, description, created_on, updated_on FROM public.academic_years ");
		
	}

	@Override
	public Long countObject() throws SQLException {
		StringBuilder queryBuilder = new StringBuilder("SELECT count(*) FROM public.academic_years ");
		return this.jdbcTemplate.query(queryBuilder.toString(), new LongResultProcessor());
	}

	@Override
	public List<AcademicYearVO> loadAllList() throws SQLException {		
		return this.jdbcTemplate.query(getBaseSelectQuery().toString(), new AcademicYearResultProcessor());
	}

	@Override
	public List<AcademicYearVO> findAcademicYears(Integer studentId, Integer academicLevelId) throws SQLException {
		String query = "SELECT sal.academic_year_id FROM public.student_academic_levels sal WHERE sal.student_id = ? AND sal.academic_level_id = ?";		
		return this.jdbcTemplate.query(query,new Object[] {studentId, academicLevelId}, new RowMapper<AcademicYearVO>() {
			@Override
			public AcademicYearVO mapRow(ResultSet rs, int rowNum) throws SQLException {			
				return findObject(rs.getInt("academic_year_id"));
			}			
		});
	}
}
