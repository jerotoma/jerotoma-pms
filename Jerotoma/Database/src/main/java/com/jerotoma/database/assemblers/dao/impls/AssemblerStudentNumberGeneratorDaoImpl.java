package com.jerotoma.database.assemblers.dao.impls;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.jerotoma.database.assemblers.AssemblerStudentNumberGeneratorDao;

@Repository
public class AssemblerStudentNumberGeneratorDaoImpl extends JdbcDaoSupport implements AssemblerStudentNumberGeneratorDao {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired DataSource dataSource;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		this.jdbcTemplate = getJdbcTemplate();
	}
	
	@Override
	public Long getNextNumber() throws SQLException {
		String sql = "SELECT nextval('public.seq_student_numbers')";
		return this.jdbcTemplate.query(sql, new LongResultProcessor());
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
}
