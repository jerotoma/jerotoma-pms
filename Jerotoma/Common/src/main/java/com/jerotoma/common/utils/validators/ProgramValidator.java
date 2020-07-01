package com.jerotoma.common.utils.validators;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jerotoma.common.constants.ProgramConstant;
import com.jerotoma.common.exceptions.FieldRequiredException;
import com.jerotoma.common.models.academic.Program;
import com.jerotoma.common.utils.CalendarUtil;

public class ProgramValidator {

	@SuppressWarnings("unchecked")
	public static Program validate(Map<String, Object> params, List<String> requiredFields) {
		Program program = new Program();
		Integer id = null;
		String code = null;
		String name = null;
		String description = null;		
		List<Integer> academicLevelIDs = null;
		
		if (params.containsKey(ProgramConstant.ID)) {
			id = (Integer) params.get(ProgramConstant.ID);
		}
		
		if (params.containsKey(ProgramConstant.CODE)) {
			code = (String) params.get(ProgramConstant.CODE);
		}
		
		if (params.containsKey(ProgramConstant.NAME)) {
			name = (String) params.get(ProgramConstant.NAME);
		}
		
		if (params.containsKey(ProgramConstant.DESCRIPTION)) {
			description = (String) params.get(ProgramConstant.DESCRIPTION);
		}
		
		if (params.containsKey(ProgramConstant.ACADEMIC_LEVEL_IDS)) {
			academicLevelIDs = (ArrayList<Integer>) params.get(ProgramConstant.ACADEMIC_LEVEL_IDS);
		}
		
		
		if (id == null && requiredFields.contains(ProgramConstant.ID)) {
			throw new FieldRequiredException("ID is required to continue");
		}
		program.setId(id);
		
		if (name == null && requiredFields.contains(ProgramConstant.NAME)) {
			throw new FieldRequiredException("Name is required to continue");
		}
		program.setName(name);
		
		if (code == null && requiredFields.contains(ProgramConstant.CODE)) {
			throw new FieldRequiredException("ID is required to continue");
		}
		program.setCode(code);
		
		if (description == null && requiredFields.contains(ProgramConstant.DESCRIPTION)) {
			throw new FieldRequiredException("Description is required to continue");
		}
		program.setDescription(description);		
		
		if (academicLevelIDs == null && requiredFields.contains(ProgramConstant.ACADEMIC_LEVEL_IDS)) {
			throw new FieldRequiredException("Academic Level IDs are required to continue");
		}
		program.setAcademicLevelIDs(academicLevelIDs);
		
		Date today = CalendarUtil.getTodaysDate();
		
		program.setCreatedOn(today);
		program.setUpdatedOn(today);
		
		return program;
	}

}
