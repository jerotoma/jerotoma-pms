package com.jerotoma.database.assemblers.dao.academic.impls;

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
import com.jerotoma.common.constants.AcademicLevelConstant;
import com.jerotoma.common.constants.CompletionStatus;
import com.jerotoma.common.constants.DatabaseConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.viewobjects.AcademicLevelPrerequisiteVO;
import com.jerotoma.common.viewobjects.AcademicLevelVO;
import com.jerotoma.database.assemblers.dao.academic.AssemblerAcademicLevelDao;
import com.jerotoma.database.dao.DaoUtil;


@Repository
public class AssemblerAcademicLevelDaoImpl extends JdbcDaoSupport implements AssemblerAcademicLevelDao {

	private JdbcTemplate jdbcTemplate;
	private Map<String, Object> map;
	
	@Autowired DataSource dataSource;	
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		this.jdbcTemplate = getJdbcTemplate();		
	}

	@Override
	public AcademicLevelVO findObject(Integer primaryKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE al.id = ? ").toString();
		return this.jdbcTemplate.query(query, new AcademicLevelSingleResultProcessor(), primaryKey);
	}

	@Override
	public AcademicLevelVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		String query = getBaseSelectQuery().append("WHERE al.code = ? ").toString();
		return this.jdbcTemplate.query(query, new AcademicLevelSingleResultProcessor(), uniqueKey);
	}

	@Override
	public List<AcademicLevelVO> loadList() throws SQLException {
		StringBuilder builder = getBaseSelectQuery();		
		return this.jdbcTemplate.query(builder.toString(), new AcademicLevelResultProcessor());
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		map = new HashMap<>();
		StringBuilder builder = getBaseSelectQuery();
		builder.append(DaoUtil.getOrderBy( "al." + queryParam.getFieldName(), queryParam.getOrderby()))
		.append(" ")
		.append("limit ? offset ?");

		Long countResults = countObject();
		int pageCount = DaoUtil.getPageCount(queryParam.getPageSize(), countResults);
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		Object[] paramList = new Object[] {limit, offset};
		
		List<AcademicLevelVO> academicLevels = this.jdbcTemplate.query(builder.toString(), new AcademicLevelResultProcessor(), paramList);
		map.put(AcademicLevelConstant.ACADEMIC_LEVELS, academicLevels);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		
		return map;
	}

	@Override
	public Long countObject() throws SQLException {
		StringBuilder queryBuilder = new StringBuilder("SELECT count(*) FROM ").append(DatabaseConstant.TABLES.ACADEMIC_LEVELS);
		return this.jdbcTemplate.query(queryBuilder.toString(), new LongResultProcessor());
	}
	
	private StringBuilder getBaseSelectQuery() {		
		return new StringBuilder("SELECT al.id, al.name, al.code, al.description, al.created_on AS createdOn, al.updated_on AS updatedOn FROM ").append(DatabaseConstant.TABLES.ACADEMIC_LEVELS).append(" al ");		
	}
	
	public class AcademicLevelResultProcessor implements RowMapper<AcademicLevelVO>{
		
		private Integer programId;
		
		public AcademicLevelResultProcessor(Integer programId) {
			this.programId = programId;
		}
		
		public AcademicLevelResultProcessor() {	}
		
		@Override
		public AcademicLevelVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			AcademicLevelVO academicLevel = mapAcademicLevel(rs);;
			if (getProgramId() != null && academicLevel != null) {				
				academicLevel.setPrerequisites(findAcademicLevelPrerequisitesByAcademicLevelId(getProgramId(), academicLevel.getId()));
			}
			return academicLevel;
		}	
		
		private Integer getProgramId() {
			return programId;
		}
	}
	
	private AcademicLevelVO mapAcademicLevel(ResultSet rs) throws SQLException {
		return new AcademicLevelVO(rs);	
	}
	
	public class AcademicLevelSingleResultProcessor implements ResultSetExtractor<AcademicLevelVO>{
		@Override
		public AcademicLevelVO extractData(ResultSet rs) throws SQLException, DataAccessException {
			AcademicLevelVO academicLevel = null;
			if(rs.next()) {
				academicLevel = mapAcademicLevel(rs);		
			}
			return academicLevel;
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

	@Override
	public List<AcademicLevelVO> getAllAcademicLevel() throws SQLException {		
		return this.jdbcTemplate.query(getBaseSelectQuery().toString(), new AcademicLevelResultProcessor());
	}

	@Override
	public List<AcademicLevelVO> loadUnAddedAcademicLevelByProgram(Integer programId) throws SQLException {
		StringBuilder queryBuilder  = getBaseSelectQuery().append("WHERE al.id NOT IN (SELECT pal.academic_level_id FROM program_academic_levels pal WHERE pal.program_id = ?)");		
		return this.jdbcTemplate.query(queryBuilder.toString(), new AcademicLevelResultProcessor(), programId);
	}

	@Override
	public List<AcademicLevelVO> loadAcademicLevelByProgram(Integer programId) throws SQLException {
		StringBuilder queryBuilder  = getBaseSelectQuery().append("WHERE al.id IN (SELECT pal.academic_level_id FROM program_academic_levels pal WHERE pal.program_id = ?)");		
		return this.jdbcTemplate.query(queryBuilder.toString(), new AcademicLevelResultProcessor(), programId);
	}

	@Override
	public List<AcademicLevelVO> loadAvailableAcademicLevelsByStudentId(Integer programId, Integer studentId) {
		StringBuilder queryBuilder  = getBaseSelectQuery()
				.append("WHERE al.id NOT IN (")
				.append("SELECT pal.academic_level_id FROM program_academic_levels pal ")
				.append(" INNER JOIN student_academic_levels sal ON sal.academic_level_id = pal.academic_level_id ")				
				.append(" WHERE sal.completion_status_id <> ? AND sal.completion_status_id <> ? AND pal.program_id = ? AND sal.student_id = ? )");		
		return this.jdbcTemplate.query(queryBuilder.toString(), new AcademicLevelResultProcessor(programId), 
				CompletionStatus.COMPLETED.getID(), CompletionStatus.IN_PROGRESS.getID(), programId, studentId);
	}
	
	public Set<AcademicLevelPrerequisiteVO> findAcademicLevelPrerequisitesByAcademicLevelId(Integer programId, Integer academicLevelId) throws SQLException {
		StringBuilder builder = new StringBuilder("SELECT pap.id, pap.program_id, pap.academic_level_id, pap.prerequisite_academic_level_id FROM ")
				.append(DatabaseConstant.TABLES.PROGRAM_ACADEMIC_LEVEL_PREREQUISITES).append(" pap ")				
				.append(" WHERE pap.academic_level_id = ? AND pap.program_id = ? ");		
		return new HashSet<>(this.jdbcTemplate.query(builder.toString(), new AcademicLevelPrerequisiteResultProcessor(), academicLevelId, programId));		
	}
	
	private class AcademicLevelPrerequisiteResultProcessor implements RowMapper<AcademicLevelPrerequisiteVO>{
		@Override
		public AcademicLevelPrerequisiteVO mapRow(ResultSet rs, int rowNum) throws SQLException {		
			AcademicLevelPrerequisiteVO prerequisiteVO = new AcademicLevelPrerequisiteVO();
			prerequisiteVO.setId(rs.getInt("id"));
			prerequisiteVO.setProgramId(rs.getInt("program_id"));
			prerequisiteVO.setAcademicLevel(findObject(rs.getInt("prerequisite_academic_level_id")));			
			return prerequisiteVO;
		}	
	}
}
