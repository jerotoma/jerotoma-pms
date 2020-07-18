package com.jerotoma.api.controllers.secured;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jerotoma.api.controllers.BaseController;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.exceptions.FieldRequiredException;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.media.FileUploadWrapper;
import com.jerotoma.common.models.media.Media;
import com.jerotoma.common.models.media.UserMedia;
import com.jerotoma.common.models.users.Parent;
import com.jerotoma.common.models.users.Staff;
import com.jerotoma.common.models.users.Student;
import com.jerotoma.common.models.users.Teacher;
import com.jerotoma.common.utils.FileUpload;
import com.jerotoma.common.utils.StringUtility;
import com.jerotoma.common.viewobjects.UserVO;
import com.jerotoma.services.assemblers.media.AssemblerMediaService;
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
	@Autowired AssemblerMediaService assemblerMediaService;
	@Autowired UserMediaService userMediaService;
	@Autowired TeacherService teacherService;
	@Autowired StudentService studentService;
	@Autowired StaffService staffService;
	@Autowired ParentService parentService;
	@Autowired MediaService mediaService;
	
	
	@GetMapping(value = {"/paginated", "/paginated/"})
	@ResponseBody
	public HttpResponseEntity<Object> loadUploadsPaginated(
			Authentication auth,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="pageSize", required=false) Integer pageSize,
			@RequestParam(value="fieldName", required=false) String fieldName,
			@RequestParam(value="orderby", required=false) String orderby,
			@RequestParam(value="userType", required=false) String userType) {
		
		this.logRequestDetail("GET : " + EndPointConstants.REST_DASHBOARD_CONTROLLER.BASE);
		this.proccessLoggedInUser(auth);
		this.securityCheckAccessByRoles(auth);
		queryParam = this.setParams(page, pageSize, fieldName, orderby);
		queryParam.setType(userType);
		try {
			response.setData(assemblerMediaService.loadMapList(queryParam));
			response.setSuccess(true);
			response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		} catch (SQLException e) {
			response.setSuccess(false);
			throw new JDataAccessException(e.getMessage(), e);	
		}
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}
	
	@PostMapping(value = {"/search", "/search/"})
	@ResponseBody
	public HttpResponseEntity<Object> searchMedia(
			Authentication auth,
			@RequestBody Map<String, Object> params, 
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="pageSize", required=false) Integer pageSize,
			@RequestParam(value="fieldName", required=false) String fieldName,
			@RequestParam(value="orderby", required=false) String orderby,
			@RequestParam(value="userType", required=false) String userType) {
		
		this.logRequestDetail("GET : " + EndPointConstants.REST_DASHBOARD_CONTROLLER.BASE);
		this.proccessLoggedInUser(auth);
		this.securityCheckAccessByRoles(auth);
		queryParam = this.setParams(page, pageSize, fieldName, orderby);
		queryParam.setType(userType);
		
		String searchTerm = (String)params.get("searchTerm");
		if (StringUtility.isEmpty(searchTerm)) {
			throw new FieldRequiredException("Search Term is required to continue");
		}
		queryParam.setSearch(searchTerm);
		
		try {
			response.setData(assemblerMediaService.searchMedia(queryParam));
			response.setSuccess(true);
			response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		} catch (SQLException e) {
			response.setSuccess(false);
			throw new JDataAccessException(e.getMessage(), e);	
		}
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}
	
	
	@GetMapping(value = {"", "/"})
	@ResponseBody
	public HttpResponseEntity<Object> loadUploads(Authentication auth) throws SQLException{
		this.logRequestDetail("GET : " + EndPointConstants.REST_DASHBOARD_CONTROLLER.BASE);
		this.proccessLoggedInUser(auth);
		this.securityCheckAccessByRoles(auth);
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		try {
			response.setData(assemblerMediaService.getMediaList());
		} catch (SQLException e) {
			response.setSuccess(false);
			throw new JDataAccessException(e.getMessage(), e);	
		}
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}
	
	@PostMapping(value = {"", "/"})
	@ResponseBody
	public HttpResponseEntity<Object> postCreateMedia(
			@RequestParam Map<String, String> params, 
			@RequestParam("media_files[]") MultipartFile[] files) throws SQLException{
		
		List<FileUploadWrapper>  fileUploadWrappers = new ArrayList<>();
		List<FileUploadWrapper>  uploadedFiles = FileUpload.uploadMultipleFileHandler(files, context, "users");
		for (FileUploadWrapper uploadedFile: uploadedFiles) {
			fileUploadWrappers.add(saveUpload(uploadedFile, getAuthenticatedUserVO().getUserId(), false));		
		}	
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setHttpStatus(HttpStatus.OK);
		response.setData(fileUploadWrappers);		
		return response;
	}
		
	@PostMapping(value = {"/users/{userId}/profile", "/users/{userId}/profile/"})
	@ResponseBody
	public HttpResponseEntity<Object> createProfileMedia(
			@RequestParam Map<String, String> params, 
			@RequestParam("media_file") MultipartFile file,
			 @PathVariable("userId") Integer userId) throws SQLException {
							
		userSecurityClearance.checkChangeUserProfileImageClearance(userId);
		
		FileUploadWrapper  fileUploadWrapper = FileUpload.uploadFileHandler(file, context, "users");
		fileUploadWrapper = saveUpload(fileUploadWrapper, userId, true);
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setHttpStatus(HttpStatus.OK);
		response.setData(fileUploadWrapper);		
		return response;
	}
	
	@DeleteMapping(value = {"/{mediaId}", "/{mediaId}/"})
	@ResponseBody
	protected HttpResponseEntity<Object> deleteMedia(Authentication auth, @PathVariable("mediaId") Integer mediaId) {
		HttpResponseEntity<Object> instance = new HttpResponseEntity<>();
			
		this.logRequestDetail("DELETE : " + EndPointConstants.REST_DASHBOARD_CONTROLLER.BASE + "/" + mediaId);
		this.securityCheckAccessByRoles(auth);
		this.securityCheckAdminAccess(auth, "delete");
		this.proccessLoggedInUser(auth);		
		Media media;		
		try {
			media = mediaService.findObject(mediaId);	
			if (media == null) {
				instance.setSuccess(false);
				instance.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
				return instance;
			} 
			boolean isDeleted = mediaService.deleteObject(media);
			if (isDeleted) {
				FileUpload.deleteFile(media);
			}						
			instance.setData(isDeleted);
			instance.setSuccess(isDeleted);
			instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		return instance;
	}
	
	
	private FileUploadWrapper saveUpload(FileUploadWrapper  fileUploadWrapper, Integer userId, boolean isForProfile) {
		
		try {		
			UserVO userVO = userService.getUserByUserId(userId);			
			if (fileUploadWrapper.getSuccess()) {
				Media media = fileUploadWrapper.getMedia();
				media.setAddedBy(getAuthenticatedUserVO().getUserId());
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
				
				if (isForProfile) {
					updateUserProfileImage(userMedia, userVO);	
				}				
				fileUploadWrapper.setMedia(media);				
			}
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);
		}
		return fileUploadWrapper;
		
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
