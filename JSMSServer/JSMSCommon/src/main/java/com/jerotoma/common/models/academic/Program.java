package com.jerotoma.common.models.academic;

import java.util.Date;
import java.util.List;
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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jerotoma.common.constants.DatabaseConstant;

@Entity
@Table(name = DatabaseConstant.TABLES.PROGRAMS)
public class Program {
	
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE, 
			generator = DatabaseConstant.TABLES.PROGRAMS + "_generator")
	@SequenceGenerator(
			name = DatabaseConstant.TABLES.PROGRAMS + "_generator", 
			sequenceName = DatabaseConstant.TABLES.PROGRAMS + "_id_seq", 
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
			  name = DatabaseConstant.TABLES.PROGRAM_ACADEMIC_LEVELS, 
			  joinColumns = @JoinColumn(name = "program_id"), 
			  inverseJoinColumns = @JoinColumn(name = "academic_level_id"))
	@JsonManagedReference
	private Set<AcademicLevel> academicLevels;
	
	@OneToMany(mappedBy = "program")
	@JsonBackReference
	private Set<ProgramAcademicLevelPrerequisite> prerequisites;
	
	@Transient
	private List<Integer> academicLevelIDs;

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

	public void setAcademicLevelIDs(List<Integer> academicLevelIDs) {
		this.academicLevelIDs = academicLevelIDs;
		
	}

	public List<Integer> getAcademicLevelIDs() {
		return academicLevelIDs;
	}
	
	public Set<AcademicLevel> getAcademicLevels() {
		return academicLevels;
	}

	public void setAcademicLevels(Set<AcademicLevel> academicLevels) {
		this.academicLevels = academicLevels;
	}

	public Set<ProgramAcademicLevelPrerequisite> getPrerequisites() {
		return prerequisites;
	}

	public void setPrerequisites(Set<ProgramAcademicLevelPrerequisite> prerequisites) {
		this.prerequisites = prerequisites;
	}

	public static class ProgramAcademicLevel {
		private Integer programId;
		private Integer academicLevelId;
		private List<Integer> academicLevelPrerequisiteIds;
		
		public ProgramAcademicLevel(Integer programId, Integer academicLevelId, List<Integer> academicLevelPrerequisiteIds) {			
			this.programId = programId;
			this.academicLevelId = academicLevelId;
			this.academicLevelPrerequisiteIds = academicLevelPrerequisiteIds;
		}

		public Integer getProgramId() {
			return programId;
		}

		public void setProgramId(Integer programId) {
			this.programId = programId;
		}

		public Integer getAcademicLevelId() {
			return academicLevelId;
		}

		public void setAcademicLevelId(Integer academicLevelId) {
			this.academicLevelId = academicLevelId;
		}

		public List<Integer> getAcademicLevelPrerequisiteIds() {
			return academicLevelPrerequisiteIds;
		}

		public void setAcademicLevelPrerequisiteIds(List<Integer> academicLevelPrerequisiteIds) {
			this.academicLevelPrerequisiteIds = academicLevelPrerequisiteIds;
		}		
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
		
        if (!(obj instanceof Program)) {
            return false;
        } 
        
        Program program = (Program)obj;        
        if (this.getId().equals(program.getId())) {
        	return true;
        }
        return false;
	}
	
}
