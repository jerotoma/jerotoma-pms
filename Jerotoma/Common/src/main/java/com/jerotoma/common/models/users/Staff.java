package com.jerotoma.common.models.users;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jerotoma.common.constants.DatabaseConstant;
import com.jerotoma.common.models.positions.Position;

@Entity
@Table(name = DatabaseConstant.TABLES.STAFFS)
public class Staff extends Person implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="staffs_generator")
	@SequenceGenerator(name="staffs_generator", sequenceName = "staffs_id_seq", allocationSize=1)
	@Column
	private Integer id;
	
	@ManyToOne
   	@JsonManagedReference
   	@JoinColumn(name="position_id")
	private Position position;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
}
