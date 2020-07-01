package com.jerotoma.common.models.academic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.jerotoma.common.constants.DatabaseConstant;

@Entity
@Table(name = DatabaseConstant.TABLES.PROGRAM_ACADEMIC_LEVELS)
public class ProgramAcademicLevel {
	
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE, 
			generator = DatabaseConstant.TABLES.PROGRAM_ACADEMIC_LEVELS + "_generator")
	@SequenceGenerator(
			name = DatabaseConstant.TABLES.PROGRAM_ACADEMIC_LEVELS + "_generator", 
			sequenceName = DatabaseConstant.TABLES.PROGRAM_ACADEMIC_LEVELS + "_id_seq", 
			allocationSize=1)
	@Column
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="program_id")
	@JsonBackReference
	private Program program;
	
	@OneToOne
	@JoinColumn(name="academic_level_id")
	private AcademicLevel academicLevel;
	
	public ProgramAcademicLevel() {}

	public ProgramAcademicLevel(Program program, AcademicLevel academicLevel) {
		this.program = program;
		this.academicLevel = academicLevel;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}

	public AcademicLevel getAcademicLevel() {
		return academicLevel;
	}

	public void setAcademicLevel(AcademicLevel academicLevel) {
		this.academicLevel = academicLevel;
	}
}
