package com.jerotoma.common.viewobjects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.jerotoma.common.constants.CourseConstant;
import com.jerotoma.common.constants.SystemConstant;

public class CourseVO {
	
	private Integer id;
	
	private Integer academicLevelId;
	
	private Integer programId;
	
	private Integer departmentId;
	
	private String code;
	
	private String name;
	
	private String description;
	
	private ProgramVO program;
	
	private AcademicLevelVO academicLevel;
	
	private AcademicYearVO academicYear;
	
	private DepartmentVO department;
	
	private List<AcademicDisciplineVO> academicDisciplines;
	
	private Date createdOn;
	
	private Date updatedOn;
	
	public CourseVO(ResultSet rs) throws SQLException {
		this.code = rs.getString(CourseConstant.COURSE_CODE);
		this.name = rs.getString(CourseConstant.COURSE_NAME);
		this.description = rs.getString(CourseConstant.COURSE_DESCRIPTION); 
		this.id = rs.getInt(CourseConstant.COURSE_ID);
		this.academicLevelId = rs.getInt(CourseConstant.ACADEMIC_LEVEL_ID);
		this.departmentId = rs.getInt(CourseConstant.DEPARTMENT_ID);
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

	public AcademicLevelVO getAcademicLevel() {
		return academicLevel;
	}

	public void setAcademicLevel(AcademicLevelVO academicLevel) {
		this.academicLevel = academicLevel;
	}

	public Integer getAcademicLevelId() {		
		return academicLevelId;
	}
	
	public void setAcademicLevelId(Integer academicLevelId) {
		this.academicLevelId = academicLevelId;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public void setAcademicYearId(Integer academicYearId) {
		this.academicLevelId = academicYearId;
	}

	public List<AcademicDisciplineVO> getAcademicDisciplines() {
		return academicDisciplines;
	}

	public void setAcademicDisciplines(List<AcademicDisciplineVO> academicDisciplines) {
		this.academicDisciplines = academicDisciplines;
	}

	public DepartmentVO getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentVO department) {
		this.department = department;
	}

	public AcademicYearVO getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(AcademicYearVO academicYear) {
		this.academicYear = academicYear;
	}

	public ProgramVO getProgram() {
		return program;
	}

	public void setProgram(ProgramVO program) {
		this.program = program;
	}

	public Integer getProgramId() {
		return programId;
	}

	public void setProgramId(Integer programId) {
		this.programId = programId;
	}
}
