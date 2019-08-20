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
import com.jerotoma.common.models.users.Staff;

public class StaffAddress {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="staff_addresses_generator")
	@SequenceGenerator(name="staff_addresses_generator", sequenceName = "staff_addresses_id_seq", allocationSize=1)
	@Column
	private Integer id;
	
	@ManyToOne
   	@JoinColumn(name="staff_id")
	private Staff staff;
	
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

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	
}
