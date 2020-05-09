package com.jerotoma.common.viewobjects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.jerotoma.common.constants.RoomConstant;
import com.jerotoma.common.constants.SystemConstant;

public class RoomVO {
	
	private Integer id;
	
	private String code;
	
	private String name;
	
	private Integer capacity;
	
	private String description;
	
	private Integer updatedBy;
		
	private Date createdOn;
	
	private Date updatedOn;
	
	private List<ClassRoomResourceVO> classRoomResources;
	
	public RoomVO(ResultSet rs) throws SQLException {
		this.code = rs.getString(RoomConstant.ROOM_CODE);
		this.name = rs.getString(RoomConstant.ROOM_NAME);
		this.description = rs.getString(RoomConstant.ROOM_DESCRIPTION); 
		this.capacity = rs.getInt(RoomConstant.ROOM_CAPACITY); 
		this.id = rs.getInt(RoomConstant.ROOM_ID);
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

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
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

	public List<ClassRoomResourceVO> getClassRoomResources() {
		return classRoomResources;
	}

	public void setClassRoomResources(List<ClassRoomResourceVO> classRoomResources) {
		this.classRoomResources = classRoomResources;
	}
	
	
}
