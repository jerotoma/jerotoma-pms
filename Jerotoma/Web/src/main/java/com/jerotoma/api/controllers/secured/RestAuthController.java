package com.jerotoma.api.controllers.secured;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jerotoma.api.controllers.BaseController;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.constants.RoleConstant;
import com.jerotoma.common.constants.SecurityConstant;
import com.jerotoma.common.constants.UserConstant;
import com.jerotoma.common.exceptions.InvalidJwtTokenException;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.jwt.AccessJwtToken;
import com.jerotoma.common.models.users.User;
import com.jerotoma.common.models.users.Staff;
import com.jerotoma.common.models.users.UserContext;
import com.jerotoma.common.utils.CalendarUtil;
import com.jerotoma.common.utils.StringUtility;
import com.jerotoma.config.auth.jwt.extractor.TokenExtractor;
import com.jerotoma.config.auth.jwt.verifier.TokenVerifier;
import com.jerotoma.config.auth.tokens.JwtTokenFactory;
import com.jerotoma.config.auth.tokens.RawAccessJwtToken;
import com.jerotoma.config.auth.tokens.RefreshToken;
import com.jerotoma.services.cookies.CookieService;
import com.jerotoma.services.http.HttpService;
import com.jerotoma.services.users.AuthUserService;

@RestController
@RequestMapping(EndPointConstants.REST_AUTH_CONTROLLER.BASE)
public class RestAuthController extends BaseController {
	
	@Autowired private AuthUserService authUserService;
	@Autowired private CookieService cookieService;
    @Autowired private HttpService httpService;
    @Autowired private JwtTokenFactory tokenFactory;
    @Autowired private TokenVerifier tokenVerifier;
    @Autowired private ObjectMapper mapper;
    @Autowired @Qualifier("jwtHeaderTokenExtractor") TokenExtractor tokenExtractor;
	
	@PostMapping(EndPointConstants.REST_AUTH_CONTROLLER.REGISTER)
	@ResponseBody
	public HttpResponseEntity<Object> postCreate(@RequestBody Map<String, Object> params) {		
		response.setSuccess(true);
		response.setStatusCode("200");		
		try {
			authUser = authUserService.createUserLoginAccount(
					User.validateAndMapAuthUser(params, 
							RoleConstant.USER_ROLES.ROLE_USER));		
			
			Staff staff = new Staff();
			staff.setUserId(authUser.getId());		
			staff.setFirstName(params.get(UserConstant.FIRST_NAME).toString());
			staff.setLastName(params.get(UserConstant.LAST_NAME).toString());
			staff.setPhoneNumber(UserConstant.DEFAULT_PHONE_NUMBER);			
			staff.setBirthDate(CalendarUtil.convertStringToDate(UserConstant.DEFAULT_BIRTH_DATE));
			staff.setCreatedOn(CalendarUtil.getTodaysDate());
			staff.setUpdatedOn(CalendarUtil.getTodaysDate());
			staffService.createObject(staff);
			response.setData(userService.getUserByUsername(authUser.getUsername()));
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);	
		}		
		return response;		
	}
	
    
    @RequestMapping(value="/refresh-token", method=RequestMethod.POST, produces={ MediaType.APPLICATION_JSON_VALUE })
    public void refreshToken(@RequestBody Map<String, String> params, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	Map<String, Object> tokenMap = new HashMap<>();
    	if(!params.containsKey(SecurityConstant.REFRESH_TOKEN_KEY)) {
    		throw new InvalidJwtTokenException("Invalid Token");
    	}
    	
    	String refreshTokenKey = params.get(SecurityConstant.REFRESH_TOKEN_KEY);
    	
    	if (!refreshTokenKey.equals(SecurityConstant.REFRESH_TOKEN_VALUE)) {
    		throw new InvalidJwtTokenException("Invalid Token");
    	}	
    			
    	String tokenPayload = cookieService.findCookieValue(request, SecurityConstant.JWT_COOKIE_AUTH_REFRESH_TOKEN);
    	if (StringUtility.isEmpty(tokenPayload)) {
    		cookieService.deleteCookie(request, response, SecurityConstant.JWT_COOKIE_AUTH_REFRESH_TOKEN);
    		tokenMap.put("message", "Invalid Token was provided");   
    		tokenMap.put("success", false);   
            mapper.writeValue(response.getWriter(), tokenMap);
        	return;
        }
    	   	
    	String token = tokenExtractor.extract(SecurityConstant.HEADER_PREFIX + tokenPayload);
       
    	RawAccessJwtToken rawToken = new RawAccessJwtToken(token);
        RefreshToken refreshToken = RefreshToken.create(rawToken, tokenFactory.getJwtSettings().getTokenSigningKey()).orElseThrow(() -> new InvalidJwtTokenException("Invalid Token"));

        String jti = refreshToken.getJti();
        if (!tokenVerifier.verify(jti)) {
            throw new InvalidJwtTokenException("Invalid Token");
        }

        String subject = refreshToken.getSubject();
        User user = authUserService.loadUserByUsername(subject);

        if (user == null) {
        	throw new UsernameNotFoundException("User not found of the provided token");
        }
        
        
        UserContext userContext = UserContext.create(user.getUsername(), user.getAuthorities());
        AccessJwtToken accessJwtToken = tokenFactory.createAccessJwtToken(userContext);
        httpService.processResponse(request, response, refreshToken.getToken(), accessJwtToken.getToken());
        
      }
	

}
