package com.jerotoma.common.models.addresses;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.jerotoma.common.constants.DatabaseConstant;

@Entity
@Table(name = DatabaseConstant.TABLES.ADDRESSES)
public class Address implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE, 
			generator = DatabaseConstant.TABLES.ADDRESSES + "_generator")
	@SequenceGenerator(
			name = DatabaseConstant.TABLES.ADDRESSES + "_generator", 
			sequenceName = DatabaseConstant.TABLES.ADDRESSES + "_id_seq", 
			allocationSize=1)
	@Column
	private Integer id;
	
	@Column
	private String street;
	
	@Column
	private String unit;
	
	@Column(name="postal_code")
	private String postalCode;
	
	@Column
	private String city;
	
	@Column
	private String country;
	
	@Column
	private String state;
	
	@OneToOne(mappedBy = "address")
	@JsonBackReference
	private ParentAddress parentAddress;
	
	@OneToOne(mappedBy = "address")
	@JsonBackReference
	private StudentAddress studentAddress;
	
	@OneToOne(mappedBy = "address")
	@JsonBackReference
	private StaffAddress staffAddress;
	
	@OneToOne(mappedBy = "address")
	@JsonBackReference
	private TeacherAddress teacherAddress;
	
	@Column(name="updated_by")
	private Integer updatedBy;
	
	@Column(name="created_on")
	private Date createdOn;
	
	@Column(name="updated_on")
	private Date updatedOn;
		
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Integer getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}
	public ParentAddress getParentAddress() {
		return parentAddress;
	}
	public void setParentAddress(ParentAddress parentAddress) {
		this.parentAddress = parentAddress;
	}
	public StudentAddress getStudentAddress() {
		return studentAddress;
	}
	public void setStudentAddress(StudentAddress studentAddress) {
		this.studentAddress = studentAddress;
	}
	public StaffAddress getStaffAddress() {
		return staffAddress;
	}
	public void setStaffAddress(StaffAddress staffAddress) {
		this.staffAddress = staffAddress;
	}
	public TeacherAddress getTeacherAddress() {
		return teacherAddress;
	}
	public void setTeacherAddress(TeacherAddress teacherAddress) {
		this.teacherAddress = teacherAddress;
	}	
	
	
}
