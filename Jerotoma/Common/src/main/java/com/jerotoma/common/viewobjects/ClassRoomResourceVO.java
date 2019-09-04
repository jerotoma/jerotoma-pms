package com.jerotoma.common.viewobjects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.jerotoma.common.constants.ClassRoomResourceConstant;
import com.jerotoma.common.constants.SystemConstant;

public class ClassRoomResourceVO {
	
	private Integer id;
	
	
	private String code;
	
	
	private String name;
	
	
	private Integer quantity;
	
	
	private String description;
	
	private Date createdOn;
	
	private Date updatedOn;
	
	public ClassRoomResourceVO(ResultSet rs) throws SQLException {
		this.code = rs.getString(ClassRoomResourceConstant.CLASS_ROOM_RESOURCE_CODE);
		this.name = rs.getString(ClassRoomResourceConstant.CLASS_ROOM_RESOURCE_NAME);
		this.description = rs.getString(ClassRoomResourceConstant.CLASS_ROOM_RESOURCE_DESCRIPTION); 
		this.quantity = rs.getInt(ClassRoomResourceConstant.CLASS_ROOM_RESOURCE_QANTITY); 
		this.id = rs.getInt(ClassRoomResourceConstant.CLASS_ROOM_RESOURCE_ID);
		this.updatedOn = rs.getDate(SystemConstant.UPDATED_ON);
		this.createdOn = rs.getDate(SystemConstant.CREATED_ON);
		
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Integer getQuantity() {
		return quantity;
	}


	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
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
