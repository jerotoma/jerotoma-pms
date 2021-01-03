package com.jerotoma.common.models.users.students;

import java.io.Serializable;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jerotoma.common.constants.DatabaseConstant;
import com.jerotoma.common.models.academic.Program;
import com.jerotoma.common.models.users.CompletedAcademicLevel;
import com.jerotoma.common.models.users.Parent;
import com.jerotoma.common.models.users.Person;


@Entity
@Table(name = DatabaseConstant.TABLES.STUDENTS)
public class Student extends Person implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE, 
			generator = DatabaseConstant.TABLES.STUDENTS + "_generator")
	@SequenceGenerator(
			name= DatabaseConstant.TABLES.STUDENTS + "_generator", 
			sequenceName = DatabaseConstant.TABLES.STUDENTS + "_id_seq", 
			allocationSize=1)
	@Column
	private Integer id;
	
	@Column
	private String position;
	
	@Transient
	List<Integer> parentIds;
	
	@Transient
	Integer programId;
		
	@Column(name="student_number")
	private Integer studentNumber;
	
	@OneToOne
	@JoinColumn(name="program_id", referencedColumnName="id")	
	private Program program;
	
	@Column(name="current_academic_level_id")
	private Integer academicLevelId;
	
	@ManyToMany
    @JoinTable(
    	name = DatabaseConstant.TABLES.STUDENT_PARENTS,
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "parent_id"))
	@JsonBackReference
	private Set<Parent> parents = new HashSet<>();
	
	@OneToMany(mappedBy ="student")
	@JsonManagedReference
	private Set<StudentAcademicLevel> studentAcademicLevels;
	
	@OneToMany(mappedBy ="student")
	@JsonManagedReference
	private Set<CompletedAcademicLevel> completedAcademicLevels;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Integer getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(Integer studentNumber) {
		this.studentNumber = studentNumber;
	}

	public Set<StudentAcademicLevel> getStudentAcademicLevels() {
		return studentAcademicLevels;
	}

	public void setStudentAcademicLevels(Set<StudentAcademicLevel> StudentAcademicLevels) {
		this.studentAcademicLevels = StudentAcademicLevels;
	}

	public List<Integer> getParentIds() {
		return parentIds;
	}

	public void setParentIds(List<Integer> parentIds) {
		this.parentIds = parentIds;
	}

	public Set<Parent> getParents() {
		return parents;
	}

	public void setParents(Set<Parent> parents) {
		this.parents = parents;
	}

	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}
	
	public Set<StudentAcademicLevel> getStudentClasses() {
		return studentAcademicLevels;
	}

	public void setStudentClasses(Set<StudentAcademicLevel> studentClasses) {
		this.studentAcademicLevels = studentClasses;
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

	public Set<CompletedAcademicLevel> getCompletedAcademicLevels() {
		return completedAcademicLevels;
	}

	public void setCompletedAcademicLevels(Set<CompletedAcademicLevel> completedAcademicLevels) {
		this.completedAcademicLevels = completedAcademicLevels;
	}
}
