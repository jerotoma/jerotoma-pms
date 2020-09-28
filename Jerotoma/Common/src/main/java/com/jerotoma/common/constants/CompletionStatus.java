package com.jerotoma.common.constants;

public enum CompletionStatus {
	NO_STATUS(1, "No Status"),
	IN_PROGRESS(2, "In Progress"),
	PENDING(3, "Pending"),
	COMPLETED(4, "Completed");
	
	private Integer ID;  
	private String description;

	CompletionStatus(Integer ID, String description) {
		this.ID = ID;
		this.description = description;
	}

	public Integer getID() {
		return ID;
	}

	public String getDescription() {
		return description;
	}
	
	public static CompletionStatus getCompletionStatusfromID(Integer ID) {
		for (CompletionStatus completionStatus : CompletionStatus.values()) {
			if (completionStatus.getID().equals(ID)) {
				return completionStatus;
			}
		}
		return null;
	}

}
