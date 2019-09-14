package com.jerotoma.common.models.users;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jerotoma.common.constants.DatabaseConstant;
import com.jerotoma.common.utils.CalendarUtil;

@Entity
@Table(name = DatabaseConstant.TABLES.USER_ROLES)
public class UserRole {
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE, 
			generator = DatabaseConstant.TABLES.USER_ROLES + "_generator")
	@SequenceGenerator(
			name = DatabaseConstant.TABLES.USER_ROLES + "_generator", 
			sequenceName = DatabaseConstant.TABLES.USER_ROLES + "_id_seq", 
			allocationSize=1)
	@Column
	private Integer id;
	
	@Column(name="user_id")
	private Integer userId;
	
	@Column(name="role_id")
	private Integer roleId;
	
	@Column(name="created_on")
	private Date createdOn;
	
	@Column(name="updated_on")
	private Date updatedOn;
		
	public UserRole(Integer id, Integer userId, Integer roleId) {
		super();
		this.id = id;
		this.userId = userId;
		this.roleId = roleId;
		this.createdOn = CalendarUtil.getTodaysDate();
		this.updatedOn = CalendarUtil.getTodaysDate();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
	
	
	
	

}
