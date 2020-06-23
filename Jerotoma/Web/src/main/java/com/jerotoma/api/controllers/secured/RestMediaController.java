package com.jerotoma.api.controllers.secured;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jerotoma.api.controllers.BaseController;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.constants.ExceptionMessageConstant;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.exceptions.UnAuthorizedAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.media.FileUploadWrapper;
import com.jerotoma.common.models.media.Media;
import com.jerotoma.common.models.media.UserMedia;
import com.jerotoma.common.models.users.Parent;
import com.jerotoma.common.models.users.Staff;
import com.jerotoma.common.models.users.Student;
import com.jerotoma.common.models.users.Teacher;
import com.jerotoma.common.utils.FileUpload;
import com.jerotoma.common.viewobjects.UserVO;
import com.jerotoma.services.assemblers.media.AssemblerUserMediaService;
import com.jerotoma.services.media.MediaService;
import com.jerotoma.services.media.UserMediaService;
import com.jerotoma.services.users.ParentService;
import com.jerotoma.services.users.StaffService;
import com.jerotoma.services.users.StudentService;
import com.jerotoma.services.users.TeacherService;

@RestController
@RequestMapping(EndPointConstants.REST_MEDIA_CONTROLLER.BASE)
public class RestMediaController extends BaseController {
	
	@Autowired AssemblerUserMediaService assemblerUserMediaService;
	@Autowired UserMediaService userMediaService;
	@Autowired TeacherService teacherService;
	@Autowired StudentService studentService;
	@Autowired StaffService staffService;
	@Autowired ParentService parentService;
	@Autowired MediaService mediaService;
	
	@PostMapping(value = {"", "/"})
	@ResponseBody
	public HttpResponseEntity<Object> postCreateMedia(
			@RequestParam Map<String, String> params, 
			@RequestParam("media_files") MultipartFile file) throws SQLException{
		
		
		// FileUploadWrapper  fileUploadWrapper = FileUpload.uploadFileHandler(file, context, "users");
		response.setData(map);
		
		
		return response;
	}
	
	
	@PostMapping(value = {"/multimedia", "/multimedia/"})
	@ResponseBody
	public HttpResponseEntity<Object> postCreateMultiMedia(
			@RequestParam Map<String, String> params, 
			@RequestParam("media_files") MultipartFile[] files) throws SQLException {
		
		// List<FileUploadWrapper>  uploadedFiles = FileUpload.uploadFileHandler(files, context, "users");
		
		return response;
	}
	
	
	@PostMapping(value = {"/users/{userId}/profile", "/users/{userId}/profile/"})
	@ResponseBody
	public HttpResponseEntity<Object> createProfileMedia(
			@RequestParam Map<String, String> params, 
			@RequestParam("media_file") MultipartFile file,
			 @PathVariable("userId") Integer userId) throws SQLException {
		
		UserVO userVO = userService.getUserByUserId(userId);
		
		if (userVO == null || userVO.getId() != getAuthenticatedUser().getId()) { 
			throw new UnAuthorizedAccessException(String.format(ExceptionMessageConstant.UN_AUTHORIZED_ACCESS_MESSAGE, "change profile image"));
		}		
		
		FileUploadWrapper  fileUploadWrapper = FileUpload.uploadFileHandler(file, context, "users");
		if (fileUploadWrapper.getSuccess()) {
			Media media = fileUploadWrapper.getMedia();
			media = mediaService.createObject(media);
			UserMedia userMedia = new UserMedia();
			userMedia.setMediaId(media.getId());
			userMedia.setUserId(userId);			
			if (assemblerUserMediaService.doesUserMediaExist(media.getId(), userId)) {
				UserMedia um = userMediaService.findUserMediaByIDs(media.getId(), userId); 
				userMedia.setId(um.getId());
				userMedia = userMediaService.updateObject(userMedia);
			} else {
				userMedia = userMediaService.createObject(userMedia);
			}			
			updateUserProfileImage(userMedia, userVO);
			
			
			fileUploadWrapper.setMedia(media);
			
			response.setSuccess(true);
			response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			response.setHttpStatus(HttpStatus.OK);
			response.setData(fileUploadWrapper);
		} else {
			response.setSuccess(fileUploadWrapper.getSuccess());
			response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			response.setHttpStatus(HttpStatus.OK);
			response.setData(fileUploadWrapper);
		}			
		return response;
	}


	private void updateUserProfileImage(UserMedia userMedia, UserVO user) {
		
		try {		
			switch(user.getUserType()) {
			case PARENT:
				Parent parent = parentService.findObject(user.getId());
				parent.setProfileImageId(userMedia.getId());
				parentService.updateObject(parent);
				
				break;
			case TEACHER :				
					Teacher mTeacher = teacherService.findObject(user.getId());
					mTeacher.setProfileImageId(userMedia.getId());
					teacherService.updateObject(mTeacher);
				break;
			case STUDENT :				
				Student student = studentService.findObject(user.getId());
				student.setProfileImageId(userMedia.getId());
				studentService.updateObject(student);
			break;
			case STAFF :				
				Staff staff = staffService.findObject(user.getId());
				staff.setProfileImageId(userMedia.getId());
				staffService.updateObject(staff);
			break;
			default:
				throw new UsernameNotFoundException("User type not found");				
				
			}
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);	
		}
		
	}

}
