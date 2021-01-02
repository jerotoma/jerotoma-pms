package com.jerotoma.common.viewobjects;

public class AcademicLevelPrerequisiteVO {
	
	private Integer id;	
	private AcademicLevelVO academicLevel;	
	private Integer programId;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public AcademicLevelVO getAcademicLevel() {
		return academicLevel;
	}
	
	public void setAcademicLevel(AcademicLevelVO academicLevel) {
		this.academicLevel = academicLevel;
	}
		
	public Integer getProgramId() {
		return programId;
	}

	public void setProgramId(Integer programId) {
		this.programId = programId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((academicLevel == null) ? 0 : academicLevel.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((programId == null) ? 0 : programId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AcademicLevelPrerequisiteVO other = (AcademicLevelPrerequisiteVO) obj;
		if (academicLevel == null) {
			if (other.academicLevel != null)
				return false;
		} else if (!academicLevel.equals(other.academicLevel))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (programId == null) {
			if (other.programId != null)
				return false;
		} else if (!programId.equals(other.programId))
			return false;
		return true;
	}	
}
