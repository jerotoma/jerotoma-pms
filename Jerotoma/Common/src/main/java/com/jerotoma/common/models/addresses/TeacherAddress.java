package com.jerotoma.common.models.addresses;

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
import com.jerotoma.common.models.users.Teacher;

@Entity
@Table(name = DatabaseConstant.TABLES.TEACHER_ADDRESSES)
public class TeacherAddress {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="teacher_addresses_generator")
	@SequenceGenerator(name="teacher_addresses_generator", sequenceName = "teacher_addresses_id_seq", allocationSize=1)
	@Column
	private Integer id;
	
	@ManyToOne
   	@JsonManagedReference
   	@JoinColumn(name="teacher_id")
	private Teacher teacher;
	
	@OneToOne
   	@JsonManagedReference
   	@JoinColumn(name="address_id")
	private Address address;	

}
