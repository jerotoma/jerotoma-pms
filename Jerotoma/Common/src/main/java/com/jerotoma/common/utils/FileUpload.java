package com.jerotoma.common.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.jerotoma.common.constants.MediaConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.models.media.FileUploadWrapper;
import com.jerotoma.common.models.media.Media;

public class FileUpload {
	
	private static final Logger logger = LoggerFactory.getLogger(FileUpload.class);
	private static FileUpload fileUploadeController;
	
	public FileUpload(){
		
	}
	
	public static FileUpload getInstance() {
		if(fileUploadeController == null)
			fileUploadeController = new FileUpload(); 
	  return fileUploadeController;		
	}
		
	public static FileUploadWrapper uploadFileHandler(MultipartFile file, ServletContext context, String folderName) {
		FileUploadWrapper fileUploadWrapper = new FileUploadWrapper();
		
		System.out.println("Base : " + System.getProperty("catalina.base"));
		System.out.println("App Deployed Directory path: " + context.getRealPath(File.separator));
		System.out.println("getContextPath(): " + context.getContextPath());
		System.out.println("Apache Tomcat Server: " + context.getServerInfo());
		System.out.println("Servlet API version: " + context.getMajorVersion() + "."+ context.getMinorVersion());
		System.out.println("Tomcat Project Name: " + context.getServletContextName()); 
		
		if (!file.isEmpty()) {
						
			try {
				byte[] bytes = file.getBytes();
				
				//Creating the directory to store file
				
				if(StringUtility.isEmpty(folderName)) {
					fileUploadWrapper.setMessage(MediaConstant.EMPTY_FOLDER_NAME_MESSAGE);
					fileUploadWrapper.setSuccess(false);				
					return fileUploadWrapper;
				}
				
				if(folderName == null || folderName.equals("site-images")) {
					folderName = "images";
				}
				
				
				String name = file.getOriginalFilename();
				String fileExtension = FilenameUtils.getExtension(name);
				String fileBaseName = FilenameUtils.getBaseName(name);				
				String fileName = SlugifyUtil.getSlugify().slugify(fileBaseName);
				
										
				File dir = new File(SystemConstant.UPLOAD_PATH.concat(SystemConstant.FORWARD_SLASH).concat(folderName));
				if (!dir.exists()) {
					dir.mkdirs();
				}
				//Create the file on server
				File serverFile = new File(dir.getAbsolutePath().concat(SystemConstant.FORWARD_SLASH).concat(fileName).concat(SystemConstant.PERIOD).concat(fileExtension));
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				logger.info("Server File Location="	+ serverFile.getAbsolutePath());
				
				Media media = new Media();
				media.setTitle(name);
				media.setType(file.getContentType());
				media.setAbsolutePath(serverFile.getAbsolutePath());
				media.setSize(file.getSize());
				media.setSrc(context.getContextPath()+ SystemConstant.RESOURCE_PATH + SystemConstant.FORWARD_SLASH +folderName+ SystemConstant.FORWARD_SLASH + serverFile.getName());
				media.setDescription("");
				media.setCreatedOn(CalendarUtil.getTodaysDate());
				media.setUpdatedOn(CalendarUtil.getTodaysDate());
				
				fileUploadWrapper.setMedia(media);
				fileUploadWrapper.setSuccess(true);	
				fileUploadWrapper.setMessage(String.format(MediaConstant.MEDIA_UPLOAD_SUCCESS_MESSAGE, serverFile.getAbsolutePath()));
				return fileUploadWrapper;
				
			} catch (Exception e) {	
				e.printStackTrace();
				fileUploadWrapper.setMessage(String.format(MediaConstant.MEDIA_UPLOAD_FAILED_MESSAGE, file.getOriginalFilename(), e.getMessage()));
				fileUploadWrapper.setSuccess(false);				
				return fileUploadWrapper;
			}
		} else {			
			fileUploadWrapper.setMessage(String.format(MediaConstant.INVALID_FILE_MESSAGE, file.getOriginalFilename()));
			fileUploadWrapper.setSuccess(false);				
			return fileUploadWrapper;			
		}
	}
	
	public static List<FileUploadWrapper>  uploadMultipleFileHandler(MultipartFile[] files, ServletContext context, String folderName) {
		List<FileUploadWrapper>  list = new ArrayList<>();
		for(MultipartFile file: files) {
			list.add(uploadFileHandler(file, context,folderName));
		}		
		return list;
	}

	public static boolean deleteFile(Media media) {
		try {
			File dir = new File(media.getAbsolutePath());
			if (!dir.exists()) {
				return false;
			}
			dir.delete();
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;	
	}
	
	
	
	
	
	
}
