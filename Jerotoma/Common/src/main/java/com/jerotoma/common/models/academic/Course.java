package com.jerotoma.common.models.academic;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.jerotoma.common.constants.DatabaseConstant;
import com.jerotoma.common.models.academicDisciplines.AcademicDiscipline;

@Entity
@Table(name = DatabaseConstant.TABLES.COURSES)
public class Course implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE, 
			generator = DatabaseConstant.TABLES.COURSES + "_generator")
	@SequenceGenerator(
			name = DatabaseConstant.TABLES.COURSES + "_generator", 
			sequenceName = DatabaseConstant.TABLES.COURSES + "_id_seq", 
			allocationSize=1)
	@Column
	private Integer id;
	
	@Transient
	private Integer academicYearId;
	
	@Column
	private String code;
	
	@Column
	private String name;
	
	@Column
	private String description;
	
	@Transient
	private List<Integer> academicDisciplineIds;
	
	@ManyToMany
    @JoinTable(
    	name = DatabaseConstant.TABLES.COURSE_ACADEMIC_DISCIPLINES,
        joinColumns = @JoinColumn(name = "course_id"),
        inverseJoinColumns = @JoinColumn(name = "academic_discipline_id"))
	@JsonBackReference
	private Set<AcademicDiscipline> academicDisciplines = new HashSet<>();
	
	@ManyToOne
	@JoinColumn(name="academic_year_id")
	private AcademicYear academicYear;
	
	@Column(name="updated_by")
	private Integer updatedBy;
	
	@OneToMany(mappedBy = "course")
	private List<Class> jclass;
	
	@Column(name="created_on")
	private Date createdOn;
	
	@Column(name="updated_on")
	private Date updatedOn;
	
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

	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Integer getAcademicYearId() {
		return academicYearId;
	}

	public void setAcademicYearId(Integer academicYearId) {
		this.academicYearId = academicYearId;
	}

	public AcademicYear getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(AcademicYear academicYear) {
		this.academicYear = academicYear;
	}

	public List<Integer> getAcademicDisciplineIds() {
		return academicDisciplineIds;
	}

	public void setAcademicDisciplineIds(List<Integer> academicDisciplineIds) {
		this.academicDisciplineIds = academicDisciplineIds;
	}

	public Set<AcademicDiscipline> getAcademicDisciplines() {
		return academicDisciplines;
	}

	public void setAcademicDisciplines(Set<AcademicDiscipline> academicDisciplines) {
		this.academicDisciplines = academicDisciplines;
	}
}
