package com.jerotoma.common.models.users;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class Person {
	@Column(name = "first_name")
	protected String firstName;
	
	@Column(name = "last_name")
	protected String lastName;
	
	@Transient
	protected String fullName;
	
	@Transient
	protected Integer age;
	
	@Column(name = "gender")
	protected String gender;
	
	@Column(name = "occupation")
	protected String occupation;
	
	@Column(name = "birth_date")
	protected Date birthDate;
	
	@Column(name = "avatar")
	protected String picture;
	
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
}
