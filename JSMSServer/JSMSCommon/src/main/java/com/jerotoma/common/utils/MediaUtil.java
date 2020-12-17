package com.jerotoma.common.utils;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import com.jerotoma.common.models.media.Media;

public class MediaUtil {
	public static Media processMedia(MultipartFile file, String filePath){
		
		Media media = new Media();
		
		try {
			String name = file.getOriginalFilename();
			String fileBaseName = FilenameUtils.getBaseName(name);				
			String fileName = SlugifyUtil.getSlugify().slugify(fileBaseName);
			String path = filePath;
				
			media.setTitle(name);
			media.setType(file.getContentType());
			media.setAbsolutePath(filePath);
			media.setSize(file.getSize());
			media.setSrc(path);
			media.setDescription(fileName);
		
	} catch (Exception e) {
		media = null;
	}
		return media;
		
	}

}
