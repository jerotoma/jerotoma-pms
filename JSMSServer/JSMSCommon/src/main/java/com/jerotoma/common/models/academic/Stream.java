package com.jerotoma.common.models.academic;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.jerotoma.common.constants.DatabaseConstant;

@Entity
@Table(name = DatabaseConstant.TABLES.STREAMS)
public class Stream {
	
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE, 
			generator = DatabaseConstant.TABLES.STREAMS + "_generator")
	@SequenceGenerator(
			name = DatabaseConstant.TABLES.STREAMS + "_generator", 
			sequenceName = DatabaseConstant.TABLES.STREAMS + "_id_seq", 
			allocationSize=1)
	@Column
	private Integer id;
	
	@Column
	private String code;
	
	@Column
	private String name;
	
	@Column
	private String description;
	
	@Column(name="created_on")
	private Date createdOn;
	
	@Column(name="updated_on")
	private Date updatedOn;
	
	@ManyToMany(mappedBy = "streams")
	@JsonBackReference
	private Set<AcademicLevel> academicLevels;

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

	public Set<AcademicLevel> getAcademicLevels() {
		return academicLevels;
	}

	public void setAcademicLevels(Set<AcademicLevel> academicLevels) {
		this.academicLevels = academicLevels;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 14;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		
		if (obj == this) {
            return true;
		}
		
		if (obj == null) {
			return false;
		}
		
        if (!(obj instanceof Stream)) {
            return false;
        } 
        
        Stream stream = (Stream)obj;        
        if (this.getId().equals(stream.getId()) && this.getCode().equals(stream.getCode())) {
        	return true;
        }
        return false;
	}
}
