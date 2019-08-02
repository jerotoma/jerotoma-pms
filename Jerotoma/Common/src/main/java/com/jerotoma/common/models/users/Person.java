package com.jerotoma.common.models.users;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Person {
	@Column(name = "first_name")
	protected String firstName;
	
	@Column(name = "last_name")
	protected String lastName;
	
	protected String fullName;
	
	@Column(name = "age")
	protected Integer age;
	
	@Column(name = "gender")
	protected String gender;
	
	@Column(name = "occupation")
	protected String ocupation;
	
	@Column(name = "birth_date")
	protected String birthDate;
	
	@Column(name = "avatar")
	protected String picture;
	
	/*@OneToMany(mappedBy="address")
	protected List<Address> addresses; */
	
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
	public String getOcupation() {
		return ocupation;
	}
	public void setOcupation(String ocupation) {
		this.ocupation = ocupation;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
}
