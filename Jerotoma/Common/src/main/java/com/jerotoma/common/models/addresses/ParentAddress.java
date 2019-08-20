package com.jerotoma.common.models.addresses;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jerotoma.common.models.users.Parent;

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
	
}
