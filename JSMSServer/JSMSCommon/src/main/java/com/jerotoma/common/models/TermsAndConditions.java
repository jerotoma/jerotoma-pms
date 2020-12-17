package com.jerotoma.common.models;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.jerotoma.common.constants.DatabaseConstant;

public class TermsAndConditions {
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
	
	private Integer userID;
	private String termsTitle;
	private String termsDescription;
		
	public TermsAndConditions() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public String getTermsTitle() {
		return termsTitle;
	}

	public void setTermsTitle(String termsTitle) {
		this.termsTitle = termsTitle;
	}

	public String getTermsDescription() {
		return termsDescription;
	}

	public void setTermsDescription(String termsDescription) {
		this.termsDescription = termsDescription;
	}
	
}
