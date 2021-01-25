package com.jerotoma.services.media;

import com.jerotoma.common.models.media.FileUploadWrapper;
import com.jerotoma.common.models.media.Media;
import com.jerotoma.services.BaseService;

public interface MediaService extends BaseService<Media> {
	public FileUploadWrapper saveUpload(FileUploadWrapper  fileUploadWrapper, Integer userId, boolean isForProfile);

}
