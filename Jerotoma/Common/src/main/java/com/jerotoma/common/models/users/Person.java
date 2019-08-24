package com.jerotoma.common.models.users;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.jerotoma.common.models.addresses.Address;

@MappedSuperclass
public abstract class Person {
	@Column(name = "first_name")
	protected String firstName;
	
	@Column(name = "last_name")
	protected String lastName;
	
	@Column(name="middle_names")
	protected String middleNames;
	
	@Transient
	protected String fullName;
	
	@Transient
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
	private Date createdOn;
	
	@Column(name="updated_on")
	private Date updatedOn;
	
	public Person() {
		
	}
	
	public Person(AuthUser authUser) {
		this.firstName = authUser.getFirstName();
		this.lastName = authUser.getLastName();
		this.fullName = getFirstName() + " " + getLastName();
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
		return fullName;
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
}
