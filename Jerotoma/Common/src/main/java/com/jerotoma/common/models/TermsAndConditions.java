package com.jerotoma.common.models;

public class TermsAndConditions {
	private Long id;
	private Integer userID;
	private String termsTitle;
	private String termsDescription;
		
	public TermsAndConditions() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
