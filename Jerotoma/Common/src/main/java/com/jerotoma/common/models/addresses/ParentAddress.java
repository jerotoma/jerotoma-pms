package com.jerotoma.common.models.addresses;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jerotoma.common.constants.DatabaseConstant;
import com.jerotoma.common.models.users.Parent;

@Entity
@Table(name = DatabaseConstant.TABLES.PARENT_ADDRESSES)
public class ParentAddress {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="parent_addresses_generator")
	@SequenceGenerator(name="parent_addresses_generator", sequenceName = "parent_addresses_id_seq", allocationSize=1)
	@Column
	private Integer id;
	
	@ManyToOne
   	@JoinColumn(name="parent_id")
	private Parent parent;
	
	@OneToOne
   	@JsonManagedReference
   	@JoinColumn(name="address_id")
	private Address address;
	
	@Column(name="created_on")
	private Date createdOn;
	
	@Column(name="updated_on")
	private Date updatedOn;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Parent getParent() {
		return parent;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
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
