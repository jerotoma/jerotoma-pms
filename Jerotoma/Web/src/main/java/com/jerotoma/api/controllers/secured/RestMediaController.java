package com.jerotoma.api.controllers.secured;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jerotoma.api.controllers.BaseController;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.utils.FileUpload;

@RestController
@RequestMapping(EndPointConstants.REST_MEDIA_CONTROLLER.BASE)
public class RestMediaController extends BaseController {
	
	@PostMapping(value = {"", "/"})
	@ResponseBody
	public HttpResponseEntity<Object> postCreateMedia(@RequestParam Map<String, String> params, @RequestParam("media_files") MultipartFile file) throws SQLException{
		
		
		Map<String, Object>  map = FileUpload.uploadFileHandler(file, context, "users");
		instance.setData(map);
		
		
		return instance;
	}
	
	
	@PostMapping(value = {"/multimedea", "/multimedea/"})
	@ResponseBody
	public HttpResponseEntity<Object> postCreateMultiMedia(@RequestParam Map<String, String> params, @RequestParam("media_files") MultipartFile[] files) throws SQLException{
		
		return instance;
	}

}
