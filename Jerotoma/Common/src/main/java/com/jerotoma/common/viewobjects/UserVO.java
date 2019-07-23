package com.jerotoma.common.viewobjects;

import java.io.Serializable;

import com.jerotoma.common.users.AuthUser;
import com.jerotoma.common.users.Person;

public class UserVO extends Person implements Serializable{
	
	private Integer id;	
	private String picture;
	private String username;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserVO(AuthUser authUser){
		super(authUser);
		this.id = authUser.getId();
		this.username = authUser.getUsername();
		this.picture = "/assets/images/kate.png";
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
