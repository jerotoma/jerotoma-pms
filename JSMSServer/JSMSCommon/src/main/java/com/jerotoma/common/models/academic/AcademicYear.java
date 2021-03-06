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

import com.jerotoma.common.constants.DatabaseConstant;
import com.jerotoma.common.viewobjects.AcademicYearVO;

@Entity
@Table(name = DatabaseConstant.TABLES.ACADEMIC_YEARS)
public class AcademicYear {
	
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE, 
			generator = DatabaseConstant.TABLES.ACADEMIC_YEARS + "_generator")
	@SequenceGenerator(
			name = DatabaseConstant.TABLES.ACADEMIC_YEARS + "_generator", 
			sequenceName = DatabaseConstant.TABLES.ACADEMIC_YEARS + "_id_seq", 
			allocationSize=1)
	@Column
	private Integer id;
	
	@Column
	private String code;
	
	@Column
	private String name;
	
	@Column(name="year_of_study")
	private String yearOfStudy;
	
	@OneToMany(mappedBy = "academicYear")
	private List<Class> classes;
	
	@Column
	private String description;
	
	@Column(name="created_on")
	private Date createdOn;
	
	@Column(name="updated_on")
	private Date updatedOn;

	public AcademicYear(AcademicYearVO academicYear) {
		this.id = academicYear.getId();
		this.code = academicYear.getCode();
		this.name = academicYear.getName();
		this.yearOfStudy = academicYear.getYearOfStudy();
		this.description = academicYear.getDescription();
		this.createdOn = academicYear.getCreatedOn();
		this.updatedOn = academicYear.getUpdatedOn();
	}


	public AcademicYear() {		
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getYearOfStudy() {
		return yearOfStudy;
	}

	public void setYearOfStudy(String yearOfStudy) {
		this.yearOfStudy = yearOfStudy;
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
	
	
}
