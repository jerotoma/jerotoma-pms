package com.jerotoma.common.viewobjects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.jerotoma.common.constants.MediaConstant;

public class MediaVO {
	
	private Integer id;
	
	private Integer addedBy;
	
	private String absolutePath;
	
	private String title;
	
	private String description;
	
	private String src;
	
	private String type;
	
	private long size;
	
	private Date createdOn;
	
	private Date updatedOn;

	public MediaVO(ResultSet rs) throws SQLException {
		this.id = rs.getInt(MediaConstant.ID);
		this.absolutePath = rs.getString(MediaConstant.ABSOLUTE_PATH);
		this.title = rs.getString(MediaConstant.TITLE);
		this.description = rs.getString(MediaConstant.DESCRIPTION);
		this.src = rs.getString(MediaConstant.SRC);
		this.type = rs.getString(MediaConstant.TYPE);
		this.size = rs.getInt(MediaConstant.SIZE);
		this.addedBy = rs.getInt(MediaConstant.ADDED_BY);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(Integer addedBy) {
		this.addedBy = addedBy;
	}

	public String getAbsolutePath() {
		return absolutePath;
	}

	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
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
