package com.jerotoma.common.utils.validators;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jerotoma.common.constants.ScoreStandingConstant;
import com.jerotoma.common.exceptions.FieldRequiredException;
import com.jerotoma.common.models.academic.ScoreStanding;
import com.jerotoma.common.utils.CalendarUtil;
import com.jerotoma.common.utils.NumberUtil;

public class ScoreStandingValidator {

	public static ScoreStanding validate(Map<String, Object> params, List<String> requiredFields) {
		
		ScoreStanding scoreStanding = new ScoreStanding();
		String standing  = null;		
		String standingColor = null;
		Integer id = null;	
		Double maxScore = null;
		Double minScore = null;
						
		if(params.containsKey(ScoreStandingConstant.STANDING)) {
			standing  = params.get(ScoreStandingConstant.STANDING).toString();
		}
		if(params.containsKey(ScoreStandingConstant.STANDING_COLOR)) {
			standingColor  = params.get(ScoreStandingConstant.STANDING_COLOR).toString();
		}
				
		if(params.containsKey(ScoreStandingConstant.MAX_SCORE)) {
			maxScore  = NumberUtil.getDoube(params, ScoreStandingConstant.MAX_SCORE);
		}
		
		if(params.containsKey(ScoreStandingConstant.MIN_SCORE)) {
			minScore = NumberUtil.getDoube(params, ScoreStandingConstant.MIN_SCORE);
		}
		
		if(params.containsKey(ScoreStandingConstant.ID)) {
			id  = (Integer)params.get(ScoreStandingConstant.ID);
		}
				
		if (id == null && requiredFields.contains(ScoreStandingConstant.ID)) {
			throw new FieldRequiredException("ID is required to continue");
		}
		scoreStanding.setId(id);
		
		if (maxScore == null && requiredFields.contains(ScoreStandingConstant.MAX_SCORE)) {
			throw new FieldRequiredException("Maximum Score is required to continue");
		}
		scoreStanding.setMaxScore(maxScore);
		
		if (minScore == null && requiredFields.contains(ScoreStandingConstant.MIN_SCORE)) {
			throw new FieldRequiredException("Minimum Score is required to continue");
		}
		scoreStanding.setMinScore(minScore);
		
		
		if (standing == null && requiredFields.contains(ScoreStandingConstant.STANDING)) {
			throw new FieldRequiredException("Standing is required to continue");
		}
		scoreStanding.setStanding(standing);
		
		if (standingColor == null && requiredFields.contains(ScoreStandingConstant.STANDING_COLOR)) {
			throw new FieldRequiredException("Standing Color is required to continue");
		}
		scoreStanding.setStandingColor(standingColor);
		
		Date cal = CalendarUtil.getTodaysDate();
		scoreStanding.setCreatedOn(cal);
		scoreStanding.setUpdatedOn(cal);
				
		return scoreStanding;
	}
}
