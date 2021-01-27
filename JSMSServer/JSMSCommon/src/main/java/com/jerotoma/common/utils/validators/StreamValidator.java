package com.jerotoma.common.utils.validators;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jerotoma.common.constants.StreamConstant;
import com.jerotoma.common.exceptions.FieldRequiredException;
import com.jerotoma.common.models.academic.Stream;
import com.jerotoma.common.utils.CalendarUtil;

public class StreamValidator {

	public static Stream validate(Map<String, Object> params, List<String> requiredFields) {
		
		Stream stream = new Stream();
		String name  = null;		
		String description = null;
		String code = null;	
		Integer id = null;		
		Integer academicLevelId = null;	
		
		
		if(params.containsKey(StreamConstant.STREAM_NAME)) {
			name  = params.get(StreamConstant.STREAM_NAME).toString();
		}
		if(params.containsKey(StreamConstant.STREAM_DESCRIPTION)) {
			description  = params.get(StreamConstant.STREAM_DESCRIPTION).toString();
		}
		
		if(params.containsKey(StreamConstant.STREAM_CODE)) {
			code  = params.get(StreamConstant.STREAM_CODE).toString();
		}
		
		if(params.containsKey(StreamConstant.STREAM_ACADEMIC_LEVEL_ID)) {
			academicLevelId  = (Integer)params.get(StreamConstant.STREAM_ACADEMIC_LEVEL_ID);
		}
		
		if(params.containsKey(StreamConstant.STREAM_ID)) {
			id  = (Integer)params.get(StreamConstant.STREAM_ID);
		}
		
		if (id == null && requiredFields.contains(StreamConstant.STREAM_ID)) {
			throw new FieldRequiredException("ID is required continue");
		}
		stream.setId(id);
		
		if (academicLevelId == null && requiredFields.contains(StreamConstant.STREAM_ACADEMIC_LEVEL_ID)) {
			throw new FieldRequiredException("Academic Level ID is required continue");
		}
		stream.setAcademicLevelId(academicLevelId);
		
		if (name == null && requiredFields.contains(StreamConstant.STREAM_NAME)) {
			throw new FieldRequiredException("Name is required continue");
		}
		stream.setName(name);
		
		if (description == null && requiredFields.contains(StreamConstant.STREAM_DESCRIPTION)) {
			throw new FieldRequiredException("Description is required continue");
		}
		stream.setDescription(description);
		
		if (code == null && requiredFields.contains(StreamConstant.STREAM_CODE)) {
			throw new FieldRequiredException("Code is required continue");
		}
		stream.setCode(code);
		
		
		
		Date today = CalendarUtil.getTodaysDate();		
		stream.setCreatedOn(today);
		stream.setUpdatedOn(today);
		
		return stream;
	}
}
