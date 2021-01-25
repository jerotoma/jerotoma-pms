package com.jerotoma.services.media.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.ErrorMessageConstant;
import com.jerotoma.common.constants.MediaConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.models.media.FileUploadWrapper;
import com.jerotoma.common.models.media.Media;
import com.jerotoma.common.models.media.UserMedia;
import com.jerotoma.common.models.users.Parent;
import com.jerotoma.common.models.users.Staff;
import com.jerotoma.common.models.users.Teacher;
import com.jerotoma.common.models.users.students.Student;
import com.jerotoma.common.utils.FileUpload;
import com.jerotoma.common.viewobjects.UserVO;
import com.jerotoma.database.dao.media.MediaDao;
import com.jerotoma.services.assemblers.media.AssemblerMediaService;
import com.jerotoma.services.assemblers.media.AssemblerUserMediaService;
import com.jerotoma.services.media.MediaService;
import com.jerotoma.services.media.UserMediaService;
import com.jerotoma.services.users.AuthUserService;
import com.jerotoma.services.users.ParentService;
import com.jerotoma.services.users.StaffService;
import com.jerotoma.services.users.StudentService;
import com.jerotoma.services.users.TeacherService;
import com.jerotoma.services.users.UserService;
import com.jerotoma.services.utils.ServiceUtil;

@Transactional
@Service
public class MediaServiceImpl implements MediaService {
	
	@Autowired MediaDao mediaDao;
	@Autowired AssemblerUserMediaService assemblerUserMediaService;
	@Autowired AssemblerMediaService assemblerMediaService;
	@Autowired AuthUserService authUserService;
	@Autowired UserService userService;
	@Autowired UserMediaService userMediaService;
	@Autowired TeacherService teacherService;
	@Autowired StudentService studentService;
	@Autowired StaffService staffService;
	@Autowired ParentService parentService;	

	@Override
	public Media findObject(Integer primaryKey) throws SQLException {
		return ServiceUtil.getEntity(mediaDao.findById(primaryKey));
	}

	@Override
	public Media findObjectUniqueKey(String uniqueKey) throws SQLException {
		throw new RuntimeException(ErrorMessageConstant.METHOD_NOT_IMPLEMENTED);
	}

	@Override
	public Media createObject(Media object) throws SQLException {
		return mediaDao.save(object);
	}

	@Override
	public Media updateObject(Media object) throws SQLException {
		return mediaDao.save(object);
	}

	@Override
	public Boolean deleteObject(Media object) throws SQLException {
		mediaDao.delete(object);
		FileUpload.deleteFile(object);
		return true;
	}

	@Override
	public List<Media> loadList(QueryParam queryParam) throws SQLException {
		if (queryParam == null) {
			return mediaDao.findAll();
		}		
		return mediaDao.findAll(ServiceUtil.getPageable(queryParam)).toList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> map = new HashMap<>();		
		Page<Media> pageMedia = mediaDao.findAll(ServiceUtil.getPageable(queryParam));
		map.put(MediaConstant.MEDIA_LIST, pageMedia.toList());
		map.put(SystemConstant.COUNT, pageMedia.getTotalElements());
		map.put(SystemConstant.PAGE_COUNT, pageMedia.getTotalPages());	
		
		return map;
	}
	
	public FileUploadWrapper saveUpload(FileUploadWrapper  fileUploadWrapper, Integer userId, boolean isForProfile) {
		
		try {		
			UserVO userVO = userService.getUserVOByUserId(userId);			
			if (fileUploadWrapper.getSuccess()) {
				Media media = fileUploadWrapper.getMedia();
				media.setAddedBy(userService.loadCurrentUser().getUserId());
				media = createObject(media);
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
