package com.jerotoma.common.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.jerotoma.common.constants.SystemConstant;
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
		
	public static Map<String, Object> uploadFileHandler(MultipartFile file, ServletContext context, String folderName) {
		Map<String, Object>   map = new HashMap<String, Object>();
		String message;		
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
					map.put("message", "Directory can not be empty");
					map.put("success", false);
					
					return map;
				}
				
				if(folderName == null || folderName.equals("site-images")) {
					folderName = "images";
				}
				
				
				String name = file.getOriginalFilename();
				String fileExtension = FilenameUtils.getExtension(name);
				String fileBaseName = FilenameUtils.getBaseName(name);				
				String fileName = SlugifyUtil.getSlugify().slugify(fileBaseName);
				
										
				File dir = new File( SystemConstant.UPLOAD_PATH + "/" + folderName);
				if (!dir.exists())
					dir.mkdirs();
              
				//Create the file on server
				File serverFile = new File(dir.getAbsolutePath() + "/" + fileName +"."+fileExtension);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				logger.info("Server File Location="	+ serverFile.getAbsolutePath());
				
				Media media = new Media();
				media.setTitle(name);
				media.setType(file.getContentType());
				media.setAbsolutePath(serverFile.getAbsolutePath());
				media.setSize(file.getSize());
				media.setSrc(context.getContextPath()+ SystemConstant.RESOURCE_PATH + "/" +folderName+"/"+serverFile.getName());
				media.setDescription("");
				message = "You successfully uploaded file=" + serverFile.getAbsolutePath();
				
				
				map.put("media", media);
				map.put("success", true);
				return map; 
			} catch (Exception e) {
				message = "You failed to upload " + file.getOriginalFilename() + " => " + e.getMessage();
				map.put("message", message);
				map.put("success", false);
				
				
				return map;
			}
		} else {
			
			message = "You failed to upload " + file.getOriginalFilename() + " because the file was empty.";
			map.put("success", false);
			map.put("message", message);
			
			return map; 
		}
	}
	
	public static List<Map<String, Object>>  uploadMultipleFileHandler(MultipartFile[] files, ServletContext context, String folderName) {
		List<Map<String, Object>>  list = new ArrayList<>();
		for(MultipartFile file: files) {
			list.add(uploadFileHandler(file, context,folderName));
		}
		
		return list;
	}
	
	
	
	
	
	
}
