package com.jerotoma.database.assemblers.dao.academic.impls;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.DatabaseConstant;
import com.jerotoma.common.constants.ProgramConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.viewobjects.AcademicLevelVO;
import com.jerotoma.common.viewobjects.ProgramVO;
import com.jerotoma.database.assemblers.dao.academic.AssemblerAcademicLevelDao;
import com.jerotoma.database.assemblers.dao.academic.AssemblerProgramDao;
import com.jerotoma.database.dao.DaoUtil;

@Repository
public class AssemblerProgramDaoImpl extends JdbcDaoSupport implements AssemblerProgramDao {

	private JdbcTemplate jdbcTemplate;
	private Map<String, Object> map;
	
	@Autowired DataSource dataSource;	
	@Autowired AssemblerAcademicLevelDao assemblerAcademicLevelDao;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		this.jdbcTemplate = getJdbcTemplate();		
	}

	@Override
	public ProgramVO findObject(Integer primaryKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE p.id = ? ").toString();
		return this.jdbcTemplate.query(query, new ProgramSingleResultProcessor(), primaryKey);
	}

	@Override
	public ProgramVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE p.code = ? ").toString();
		return this.jdbcTemplate.query(query, new ProgramSingleResultProcessor(), uniqueKey);
	}

	@Override
	public List<ProgramVO> loadList() throws SQLException {
		StringBuilder builder = getBaseSelectQuery();	
		return this.jdbcTemplate.query(builder.toString(), new ProgramResultProcessor());
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		map = new HashMap<>();
		StringBuilder builder = getBaseSelectQuery();
		builder.append(DaoUtil.getOrderBy( "p." + queryParam.getFieldName(), queryParam.getOrderby()))
		.append(" ")
		.append("limit ? offset ?");

		Long countResults = countObject();
		int pageCount = DaoUtil.getPageCount(queryParam.getPageSize(), countResults);
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		Object[] paramList = new Object[] {limit, offset};
		
		List<ProgramVO> programs = this.jdbcTemplate.query(builder.toString(), new ProgramResultProcessor(), paramList);
		map.put(ProgramConstant.PROGRAMS, programs);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		
		return map;
	}

	@Override
	public Long countObject() throws SQLException {
		StringBuilder queryBuilder = new StringBuilder("SELECT count(*) FROM ").append(DatabaseConstant.TABLES.PROGRAMS);
		return this.jdbcTemplate.query(queryBuilder.toString(), new LongResultProcessor());
	}
	
	private StringBuilder getBaseSelectQuery() {		
		return new StringBuilder("SELECT p.id, p.name, p.code, p.description, p.created_on AS createdOn, p.updated_on AS updatedOn FROM ").append(DatabaseConstant.TABLES.PROGRAMS).append(" p ");		
	}
	
	public class ProgramResultProcessor implements RowMapper<ProgramVO>{
		@Override
		public ProgramVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return mapProgram(rs);
		}		
	}
	
	private ProgramVO mapProgram(ResultSet rs) throws SQLException {
		ProgramVO program = new ProgramVO(rs);
		program.setAcademicLevels(findAcademicLevelsByProgramId(program.getId()));		
		return program;		
	}
	
	private List<AcademicLevelVO> findAcademicLevelsByProgramId(Integer programId) throws SQLException {
		StringBuilder builder = new StringBuilder("SELECT al.id, al.code, al.name, al.description, al.created_on AS createdOn, al.updated_on AS updatedOn, pal.program_id FROM ")
				.append(DatabaseConstant.TABLES.ACADEMIC_LEVELS).append(" al ")
				.append("INNER JOIN ").append(DatabaseConstant.TABLES.PROGRAM_ACADEMIC_LEVELS)
				.append(" pal ON pal.academic_level_id = al.id ")
				.append(" WHERE pal.program_id = ? ");		
		return this.jdbcTemplate.query(builder.toString(), new AcademicLevelResultProcessor(), programId);		
	}
	
	private class AcademicLevelResultProcessor implements RowMapper<AcademicLevelVO>{
		@Override
		public AcademicLevelVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			AcademicLevelVO academicLevel = new AcademicLevelVO(rs); 
			academicLevel.setPrerequisites(assemblerAcademicLevelDao.findAcademicLevelPrerequisitesByAcademicLevelId(rs.getInt("program_id"), academicLevel.getId()));
			return academicLevel;
		}	
	}
	
	public class ProgramSingleResultProcessor implements ResultSetExtractor<ProgramVO>{
		@Override
		public ProgramVO extractData(ResultSet rs) throws SQLException, DataAccessException {
			ProgramVO program = null;
			if(rs.next()) {
				program = mapProgram(rs);		
			}
			return program;
		}				
	}
	
	public class LongResultProcessor implements ResultSetExtractor<Long>{
		@Override
		public Long extractData(ResultSet rs) throws SQLException, DataAccessException {
			Long l = null;
			if (rs.next()) {
				l = rs.getLong(1);
			 }
			return l;
		}				
	}
	
	@Transactional
	@Override
	public List<ProgramVO> getAllProgram() throws SQLException {		
		return this.jdbcTemplate.query(getBaseSelectQuery().toString(), new ProgramResultProcessor());
	}

	@Override
	public boolean doesProgramAcademicLevelExist(Integer programId, Integer academicLevelId) throws SQLException {
		StringBuilder builder = new StringBuilder("SELECT COUNT(*) FROM ")
				.append(DatabaseConstant.TABLES.PROGRAM_ACADEMIC_LEVELS).append(" pal")
				.append(" WHERE pal.program_id = ? AND pal.academic_level_id = ? ");
		return this.jdbcTemplate.query(builder.toString(), new ResultSetExtractor<Boolean>() {
			@Override
			public Boolean extractData(ResultSet rs) throws SQLException, DataAccessException {				
				Long l = null;
				if (rs.next()) {
					l = rs.getLong(1);
				 }
				return l != null && l > 0;
			}
		}, programId, academicLevelId);
	}
}
