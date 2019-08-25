package com.jerotoma.common.viewobjects;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.jerotoma.common.constants.AddressConstant;

public class AddressVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1722135564141850906L;
	

	private Integer id;
	
	private String street;
	
	private String unit;
	
	private String postalCode;
	
	private String city;
	
	private String country;
	
	private String state;
	
	private Integer updatedBy;
	
	private Date createdOn;
	
	private Date updatedOn;

	public AddressVO(ResultSet rs) throws SQLException {
		this.id = rs.getInt(AddressConstant.ID);
		this.street = rs.getString(AddressConstant.STREET);
		this.country = rs.getString(AddressConstant.COUNTRY);
		this.state = rs.getString(AddressConstant.STATE);
		this.city = rs.getString(AddressConstant.CITY);
		this.postalCode = rs.getString(AddressConstant.POSTAL_CODE);
		this.unit = rs.getString(AddressConstant.UNIT);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
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
	
	
	
}
