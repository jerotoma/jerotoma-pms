package com.jerotoma.common.viewobjects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.jerotoma.common.constants.UserConstant;
import com.jerotoma.common.models.users.AuthUser;
import com.jerotoma.common.utils.StringUtility;

public abstract class PersonVO {
	
	private Integer id;
	
	protected String firstName;
	
	protected String userType;
	
	protected String lastName;
	
	protected String middleNames;
	
	protected String fullName;
	
	protected String username;
	
	protected Integer userId;
	
	protected String userCode;
	
	protected AddressVO address;
	
	protected Integer age;
	
	protected Integer updatedBy;
	
	protected String gender;
	
	protected String phoneNumber;
	
	protected String emailAddress;
	
	protected String occupation;
	
	protected Date birthDate;
	
	protected String picture;
	
	protected Date createdOn;
	
	protected Date updatedOn;
	
	public PersonVO() {
		
	}
	
	public PersonVO(ResultSet rs, String userType) throws SQLException {
		this.id = rs.getInt(UserConstant.ID);
		this.firstName = rs.getString(UserConstant.FIRST_NAME);
		this.lastName = rs.getString(UserConstant.LAST_NAME);
		this.middleNames = rs.getString(UserConstant.MIDDLE_NAMES);
		this.phoneNumber = rs.getString(UserConstant.PHONE_NUMBER);
		this.emailAddress = rs.getString(UserConstant.USERNAME);
		this.userId = rs.getInt(UserConstant.USER_ID);
		this.username = rs.getString(UserConstant.USERNAME);
		this.gender = rs.getString(UserConstant.GENDER);
		this.occupation = rs.getString(UserConstant.OCCUPATION);
		this.picture = rs.getString(UserConstant.AVATAR);
		this.updatedOn = rs.getDate(UserConstant.UPDATED_ON);
		this.createdOn = rs.getDate(UserConstant.CREATED_ON);
		this.birthDate = rs.getDate(UserConstant.BIRTH_DATE);
		this.userCode = rs.getString(UserConstant.USER_CODE);
		this.userType = userType;
		this.fullName = getFullName();
	
	}
	
	public PersonVO(AuthUser authUser) {
		this.firstName = authUser.getFirstName();
		this.lastName = authUser.getLastName();
		this.fullName = getFullName();
	}

	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getFullName() {
		if (!StringUtility.isEmpty(this.middleNames)) {
			return 	this.firstName + " " + this.middleNames  + " "  + this.lastName;			
		}		
		return this.firstName + " " + this.lastName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
		
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Integer getAge() {
		return age;
	}
	
	public void setAge(Integer age) {
		this.age = age;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getMiddleNames() {
		return middleNames;
	}

	public void setMiddleNames(String middleNames) {
		this.middleNames = middleNames;
	}

	public String getOccupation() {
		return occupation;
	}
	
	public void setOccupation(String ocupation) {
		this.occupation = ocupation;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	public AddressVO getAddress() {
		return address;
	}

	public void setAddress(AddressVO address) {
		this.address = address;
	}
	
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String teacherCode) {
		this.userCode = teacherCode;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}	
}
