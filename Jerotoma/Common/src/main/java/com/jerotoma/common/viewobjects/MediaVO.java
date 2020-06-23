package com.jerotoma.common.viewobjects;

import java.sql.ResultSet;

public class MediaVO {
	
	Integer id;

	public MediaVO(ResultSet rs) {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	

}
