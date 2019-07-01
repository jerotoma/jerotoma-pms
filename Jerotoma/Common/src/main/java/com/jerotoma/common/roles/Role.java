package com.jerotoma.common.roles;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Role {
	private int id;
	
	private String name;
	
	private String displayName;
	
	private Date createdOn;
		
	private Date updatedOn;
	
	public Role() {}

	public Role(ResultSet rs) throws SQLException {
		this.id = rs.getInt("id");
		this.name = rs.getString("name");
		this.displayName = rs.getString("display_name");
		this.createdOn = rs.getDate("created_on");
		this.updatedOn = rs.getDate("updated_on");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	
	
}
