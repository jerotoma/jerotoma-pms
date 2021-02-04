package com.jerotoma.services.academic.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jerotoma.common.constants.CompletionStatus;
import com.jerotoma.common.models.academic.ProgressStatus;
import com.jerotoma.services.academic.ProgressStatusService;

@Service
public class ProgressStatusServiceImpl implements ProgressStatusService {

	@Override
	public List<ProgressStatus> loadProgressStatuses() {
		List<ProgressStatus> progressStatuses = new ArrayList<>();
		for (CompletionStatus completionStatus : CompletionStatus.values()) {
			 progressStatuses.add(new ProgressStatus(completionStatus.getID(), completionStatus.name(), completionStatus.getDescription()));
		}
		return progressStatuses;
	}

	

}
