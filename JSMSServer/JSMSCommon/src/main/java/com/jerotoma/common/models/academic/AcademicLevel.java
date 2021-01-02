package com.jerotoma.common.models.academic;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jerotoma.common.constants.DatabaseConstant;
import com.jerotoma.common.viewobjects.AcademicLevelVO;

@Entity
@Table(name = DatabaseConstant.TABLES.ACADEMIC_LEVELS)
public class AcademicLevel {
	
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE, 
			generator = DatabaseConstant.TABLES.ACADEMIC_LEVELS + "_generator")
	@SequenceGenerator(
			name = DatabaseConstant.TABLES.ACADEMIC_LEVELS + "_generator", 
			sequenceName = DatabaseConstant.TABLES.ACADEMIC_LEVELS + "_id_seq", 
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
	
	@ManyToMany
	@JoinTable(
			  name = DatabaseConstant.TABLES.ACADEMIC_LEVEL_STREAMS, 
			  joinColumns = @JoinColumn(name = "academic_level_id"), 
			  inverseJoinColumns = @JoinColumn(name = "stream_id"))
	@JsonManagedReference
	private Set<Stream> streams;
	
	@ManyToMany(mappedBy = "academicLevels")
	@JsonBackReference
	private Set<Program> programs;
	
	@OneToMany(mappedBy = "prerequisiteAcademicLevel")
	@JsonBackReference
	private Set<ProgramAcademicLevelPrerequisite> prerequisites;
	
	public AcademicLevel(AcademicLevelVO academicLevel) {
		this.id = academicLevel.getId();
		this.code = academicLevel.getCode();
		this.name = academicLevel.getName();
		this.createdOn = academicLevel.getCreatedOn();
		this.updatedOn = academicLevel.getUpdatedOn();
	}

	public AcademicLevel() {}

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

	public Set<Program> getPrograms() {
		return programs;
	}

	public void setPrograms(Set<Program> programs) {
		this.programs = programs;
	}

	public Set<Stream> getStreams() {
		return streams;
	}

	public void setStreams(Set<Stream> streams) {
		this.streams = streams;
	}

	public Set<ProgramAcademicLevelPrerequisite> getPrerequisites() {
		return prerequisites;
	}

	public void setPrerequisites(Set<ProgramAcademicLevelPrerequisite> prerequisites) {
		this.prerequisites = prerequisites;
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
		
        if (!(obj instanceof AcademicLevel)) {
            return false;
        } 
        
        AcademicLevel academicLevel = (AcademicLevel)obj;        
        if (this.getId().equals(academicLevel.getId())) {
        	return true;
        }
        return false;
	}	
}
