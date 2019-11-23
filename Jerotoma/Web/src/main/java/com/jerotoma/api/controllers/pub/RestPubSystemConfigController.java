package com.jerotoma.api.controllers.pub;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.constants.SystemConfigConstant;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.config.SystemConfig;
import com.jerotoma.services.configs.SystemConfigService;

@RestController
@RequestMapping(value=EndPointConstants.REST_PUB_SYSTEM_CONFIG_CONTROLLER.BASE)
public class RestPubSystemConfigController {
	
	@Autowired SystemConfigService systemConfigService;;
	
	@GetMapping(value = {"/keys", "/keys/"})
	@ResponseBody
	public HttpResponseEntity<Object> getSystemConfigByKey(Authentication auth, @RequestParam(required = true, value="key") String systemConfigKey) throws JDataAccessException {
		Map<String, Object> map = new HashMap<>();
		HttpResponseEntity<Object> instance = new HttpResponseEntity<>();
		SystemConfig systemConfig = null;
		
		try {
			systemConfig = systemConfigService.findObjectUniqueKey(systemConfigKey);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		
		
		map.put(SystemConfigConstant.CURRENT_THEME_ID, systemConfig.getId());
		map.put(SystemConfigConstant.CURRENT_THEME, systemConfig.getValue());
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setData(map);
		return instance;
	}
}
