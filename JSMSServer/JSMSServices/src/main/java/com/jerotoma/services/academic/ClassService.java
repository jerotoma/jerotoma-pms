package com.jerotoma.services.academic;

import com.jerotoma.common.models.academic.Class;
import com.jerotoma.services.BaseService;

public interface ClassService extends BaseService<Class> {
	public Integer countTeacherClasses(Integer teacherId);

}
