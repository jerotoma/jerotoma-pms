package com.jerotoma.common.models.users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jerotoma.common.constants.UserConstant;
import com.jerotoma.common.models.addresses.Address;
import com.jerotoma.common.utils.StringUtility;
import com.jerotoma.common.viewobjects.PersonVO;

@MappedSuperclass
public abstract class Person {
	
	@Column(name = "user_id")
	private Integer userId;
	
	@Column(name = "first_name")
	protected String firstName;
	
	@Column(name = "last_name")
	protected String lastName;
	
	@Column(name="middle_names")
	protected String middleNames;
	
	@Column(name="user_code")
	protected String userCode;
	
	@Transient
	protected String fullName;
	
	@Transient
	@JsonIgnore
	protected Address address;
	
	@Transient
	protected Integer age;
	
	@Column(name = "updated_by")
	protected Integer updatedBy;
	
	@Column(name = "gender")
	protected String gender;
	
	@Column(name = "phone_number")
	protected String phoneNumber;
	
	@Column(name = "email_address")
	protected String emailAddress;
	
	@Column(name = "occupation")
	protected String occupation;
	
	@Column(name = "birth_date")
	protected Date birthDate;
	
	@Column(name = "avatar")
	protected String picture;
	
	@Column(name="created_on")
	protected Date createdOn;
	
	@Column(name="updated_on")
	protected Date updatedOn;
	
	public Person() {
		
	}
	
	public Person(ResultSet rs) throws SQLException {
		
		this.firstName = rs.getString(UserConstant.FIRST_NAME);
		this.lastName = rs.getString(UserConstant.LAST_NAME);
		this.middleNames = rs.getString(UserConstant.MIDDLE_NAMES);
		this.phoneNumber = rs.getString(UserConstant.PHONE_NUMBER);
		this.emailAddress = rs.getString(UserConstant.EMAIL_ADDRESS);
		this.gender = rs.getString(UserConstant.GENDER);
		this.occupation = rs.getString(UserConstant.OCCUPATION);
		this.picture = rs.getString(UserConstant.AVATAR);
		this.updatedOn = rs.getDate(UserConstant.UPDATED_ON);
		this.createdOn = rs.getDate(UserConstant.CREATED_ON);
		this.birthDate = rs.getDate(UserConstant.BIRTH_DATE);
		this.userCode =  rs.getString(UserConstant.USER_CODE);
		this.fullName = getFullName();
	
	}
	
	public Person(PersonVO person) {
		
		this.firstName = person.getFirstName();
		this.lastName = person.getLastName();
		this.middleNames = person.getMiddleNames();
		this.phoneNumber = person.getPhoneNumber();
		this.emailAddress = person.getEmailAddress();
		this.gender = person.getGender();
		this.occupation = person.getOccupation();
		this.picture = person.getPicture();
		this.updatedOn = person.getUpdatedOn();
		this.createdOn = person.getCreatedOn();
		this.birthDate = person.getBirthDate();
		this.userCode =  person.getUserCode();
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
