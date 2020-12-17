package com.jerotoma.common.models.academic;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

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
	
	@Transient
	private List<AcademicLevelCompletionOrder> academicLevelCompletionOrders;
	
	@Column
	private String description;
	
	@Column(name="created_on")
	private Date createdOn;
	
	@Column(name="updated_on")
	private Date updatedOn;
	
	@OneToMany(mappedBy = "program")
	@JsonManagedReference
	private List<ProgramAcademicLevel> programAcademicLevels;
	
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

	public List<ProgramAcademicLevel> getProgramAcademicLevels() {
		return programAcademicLevels;
	}

	public void setProgramAcademicLevels(List<ProgramAcademicLevel> programAcademicLevels) {
		this.programAcademicLevels = programAcademicLevels;
	}
		
	public List<AcademicLevelCompletionOrder> getAcademicLevelCompletionOrders() {
		return academicLevelCompletionOrders;
	}

	public void setAcademicLevelCompletionOrders(List<AcademicLevelCompletionOrder> academicLevelCompletionOrders) {
		this.academicLevelCompletionOrders = academicLevelCompletionOrders;
	}




	public static class AcademicLevelCompletionOrder {
		private Integer completionOrder;
		private Integer academicLevelId;
		
		public AcademicLevelCompletionOrder(Integer completionOrder, Integer academicLevelId) {			
			this.completionOrder = completionOrder;
			this.academicLevelId = academicLevelId;
		}
		
		public Integer getCompletionOrder() {
			return completionOrder;
		}
		
		public void setCompletionOrder(Integer completionOrder) {
			this.completionOrder = completionOrder;
		}
		
		public Integer getAcademicLevelId() {
			return academicLevelId;
		}
		
		public void setAcademicLevelId(Integer academicLevelId) {
			this.academicLevelId = academicLevelId;
		}
	}
	
}
