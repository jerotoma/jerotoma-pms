package com.jerotoma.common.viewobjects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Set;

import com.jerotoma.common.constants.AcademicLevelConstant;

public class AcademicLevelVO {
	private Integer id;
	
	private String code;
	
	private String name;
	
	private String description;
	
	private Date createdOn;
	
	private Date updatedOn;
	
	private Set<AcademicLevelPrerequisiteVO> prerequisites;	
	
	public AcademicLevelVO(ResultSet rs) throws SQLException {
		this.id = rs.getInt(AcademicLevelConstant.ID);
		this.name = rs.getString(AcademicLevelConstant.NAME);
		this.code = rs.getString(AcademicLevelConstant.CODE);
		this.description = rs.getString(AcademicLevelConstant.DESCRIPTION);
		this.createdOn = rs.getDate("createdOn");
		this.updatedOn = rs.getDate("updatedOn");
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

	public Set<AcademicLevelPrerequisiteVO> getPrerequisites() {
		return prerequisites;
	}

	public void setPrerequisites(Set<AcademicLevelPrerequisiteVO> prerequisites) {
		this.prerequisites = prerequisites;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AcademicLevelVO other = (AcademicLevelVO) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
