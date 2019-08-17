package com.jerotoma.common.models.addresses;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jerotoma.common.constants.DatabaseConstant;

@Entity
@Table(name = DatabaseConstant.TABLES.ADDRESSES)
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="addresses_generator")
	@SequenceGenerator(name="addresses_generator", sequenceName = "addresses_id_seq", allocationSize=1)
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
	
	
	
}
