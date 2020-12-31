package com.jerotoma.database.dao.academic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jerotoma.common.models.academic.Stream;

public interface StreamDao extends JpaRepository<Stream, Integer>{

	@Query("select str from Stream str where str.code = ?1")
	public Stream findByUniqueKey(String uniqueKey);

}
