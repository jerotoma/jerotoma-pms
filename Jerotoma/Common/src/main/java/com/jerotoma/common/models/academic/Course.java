package com.jerotoma.common.models.academic;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.jerotoma.common.constants.DatabaseConstant;
import com.jerotoma.common.viewobjects.CourseVO;

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
	private Integer academicLevelId;
	
	@Transient
	private Integer departmentId;
	
	@Column
	private String code;
	
	@Column
	private String name;
	
	@Column
	private String description;
		
	@OneToOne
	@JoinColumn(name="program_id", referencedColumnName = "id")
	private Program program;
	
	@OneToOne
	@JoinColumn(name="academic_level_id", referencedColumnName = "id")
	private AcademicLevel academicLevel;
	
	@ManyToOne
	@JoinColumn(name="department_id")
	private Department department;
	
	@Column(name="updated_by")
	private Integer updatedBy;
	
	@OneToMany(mappedBy = "course")
	private List<Class> classes;
	
	@Column(name="created_on")
	private Date createdOn;
	
	@Column(name="updated_on")
	private Date updatedOn;
	
	public Course(CourseVO course) {
		this.id = course.getId();
		this.academicLevelId = course.getAcademicLevelId();
		this.departmentId = course.getDepartmentId();
		this.code = course.getCode();
		this.name = course.getName();
		this.description = course.getDescription();
		this.academicLevel = new AcademicLevel(course.getAcademicLevel());
		this.department = new Department(course.getDepartment());
		this.createdOn = course.getCreatedOn();
		this.updatedOn = course.getUpdatedOn();
	}

	public Course() {
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

	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Integer getAcademicLevelId() {
		return academicLevelId;
	}

	public void setAcademicLevelId(Integer academicLevelId) {
		this.academicLevelId = academicLevelId;
	}

	public AcademicLevel getAcademicLevel() {
		return academicLevel;
	}

	public void setAcademicLevel(AcademicLevel academicLevel) {
		this.academicLevel = academicLevel;
	}	
	
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Integer getDepartmentId() {
		return this.departmentId;
	}
	public void setDepartmentID(Integer departmentId) {
		this.departmentId = departmentId;		
	}

	public List<Class> getClasses() {
		return classes;
	}

	public void setClasses(List<Class> classes) {
		this.classes = classes;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}
}
