package com.jerotoma.controllers;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.users.OtherStaff;
import com.jerotoma.common.users.Parent;
import com.jerotoma.common.users.Student;
import com.jerotoma.common.users.Teacher;
import com.jerotoma.common.utils.UserType;
import com.jerotoma.common.utils.UserTypeConverter;
import com.jerotoma.http.HttpResponseEntity;

@RestController
@RequestMapping(EndPointConstants.USER_CONTROLLER.BASE)
public class UserRestController {
	Student student;
	Parent parent;
	Teacher teacher;
	OtherStaff otherStaff;
	
	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public HttpResponseEntity<Object> findUserById(
			@PathVariable(value = "userId")Integer userId, 
			@RequestParam UserType userType){
		
		HttpResponseEntity<Object>  httpResponseEntity = new HttpResponseEntity<Object>();
		
		switch(userType) {
		case STUDENT:
			student = new Student();
			httpResponseEntity.setData(student);
			break;
		case PARENT:
			parent = new Parent();
			httpResponseEntity.setData(parent);
			break;
		case TEACHER:
			teacher = new Teacher();
			httpResponseEntity.setData(teacher);
			break;
		case OTHER_STAFF:
			otherStaff = new OtherStaff();
			httpResponseEntity.setData(otherStaff);
			break;
		default:
			
		}
		
		System.out.println(httpResponseEntity.getData());
		return httpResponseEntity;
		
	}
	
	@InitBinder
	public void initBinder(final WebDataBinder webdataBinder) {
		webdataBinder.registerCustomEditor(UserType.class, new UserTypeConverter());
	}

}
