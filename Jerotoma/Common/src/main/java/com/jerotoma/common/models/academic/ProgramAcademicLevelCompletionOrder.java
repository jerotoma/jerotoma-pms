package com.jerotoma.common.models.academic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.jerotoma.common.constants.DatabaseConstant;

@Entity
@Table(name = DatabaseConstant.TABLES.PROGRAM_ACADEMIC_LEVEL_COMPLETION_ORDERS)
public class ProgramAcademicLevelCompletionOrder {
	
	@Id
	@Column(name="pal_id")
	private Integer palId;
		
	@ManyToOne
	@JoinColumn(name="program_id")
	@JsonBackReference
	private Program program;
	
	@OneToOne
	@JoinColumn(name="completion_order_id")
	private CompletionOrder completionOrder;
	
	public ProgramAcademicLevelCompletionOrder() {}

	public Integer getPalId() {
		return palId;
	}

	public void setPalId(Integer palId) {
		this.palId = palId;
	}

	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}

	public CompletionOrder getCompletionOrder() {
		return completionOrder;
	}

	public void setCompletionOrder(CompletionOrder completionOrder) {
		this.completionOrder = completionOrder;
	}
}
