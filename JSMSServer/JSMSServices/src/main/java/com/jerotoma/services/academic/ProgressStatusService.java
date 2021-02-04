package com.jerotoma.services.academic;

import java.util.List;

import com.jerotoma.common.models.academic.ProgressStatus;


public interface ProgressStatusService {
	
	List<ProgressStatus> loadProgressStatuses();

}
