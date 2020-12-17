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
import com.jerotoma.common.constants.AcademicDisciplineConstant;
import com.jerotoma.common.constants.DatabaseConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.viewobjects.AcademicDisciplineVO;
import com.jerotoma.database.assemblers.dao.AssemblerAcademicDisciplineDao;
import com.jerotoma.database.dao.DaoUtil;

@Repository
public class AssemblerAcademicDisciplineDaoImpl extends JdbcDaoSupport implements AssemblerAcademicDisciplineDao {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired DataSource dataSource;
	Map<String, Object> map;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		this.jdbcTemplate = getJdbcTemplate();
	}

	@Override
	public AcademicDisciplineVO findObject(Integer primaryKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE id = ? ").toString();
		return this.jdbcTemplate.query(query, new AcademicDisciplineSingleResultProcessor(), primaryKey);
	}

	@Override
	public AcademicDisciplineVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE code = ? ").toString();
		return this.jdbcTemplate.query(query, new AcademicDisciplineSingleResultProcessor(), uniqueKey);
	}

	@Override
	public List<AcademicDisciplineVO> loadList() throws SQLException {
		StringBuilder builder = getBaseSelectQuery();
		return this.jdbcTemplate.query(builder.toString(), new AcademicDisciplineResultProcessor());
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
		
		List<AcademicDisciplineVO> academicDisciplines = this.jdbcTemplate.query(builder.toString(), new AcademicDisciplineResultProcessor(), paramList);
		map.put(AcademicDisciplineConstant.ACADEMIC_DISCIPLINES, academicDisciplines);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		
		return map;
	}
	
	public class AcademicDisciplineResultProcessor implements RowMapper<AcademicDisciplineVO>{
		@Override
		public AcademicDisciplineVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new AcademicDisciplineVO(rs);
		}		
	}
	
	public class AcademicDisciplineSingleResultProcessor implements ResultSetExtractor<AcademicDisciplineVO>{
		@Override
		public AcademicDisciplineVO extractData(ResultSet rs) throws SQLException, DataAccessException {
			AcademicDisciplineVO academicDiscipline = null;
			if(rs.next()) {
				academicDiscipline = new AcademicDisciplineVO(rs);			
			}
			return academicDiscipline;
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
		return new StringBuilder("SELECT ad.id, ad.code, ad.name, ad.description, ad.created_on, ad.updated_on FROM public.academic_disciplines ad ");		
	}

	@Override
	public Long countObject() throws SQLException {
		StringBuilder queryBuilder = new StringBuilder("SELECT count(*) FROM public.academic_disciplines ");
		return this.jdbcTemplate.query(queryBuilder.toString(), new LongResultProcessor());
	}

	@Override
	public List<AcademicDisciplineVO> findAcademicDisciplinesByCourseId(Integer courseId) throws SQLException {
		StringBuilder builder = getBaseSelectQuery().append(" INNER JOIN ").append(DatabaseConstant.TABLES.COURSE_ACADEMIC_DISCIPLINES).append(" cad ON cad.academic_discipline_id = ad.id  where cad.course_id = ? ");
		return this.jdbcTemplate.query(builder.toString(), new AcademicDisciplineResultProcessor(), courseId);	
	}
	
}