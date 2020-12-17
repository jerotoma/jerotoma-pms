package com.jerotoma.common.models.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jerotoma.common.constants.DatabaseConstant;

@Entity
@Table(name = DatabaseConstant.TABLES.SYSTEM_CONFIGS)
public class SystemConfig {
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE, 
			generator = DatabaseConstant.TABLES.SYSTEM_CONFIGS + "_generator")
	@SequenceGenerator(
			name = DatabaseConstant.TABLES.SYSTEM_CONFIGS + "_generator", 
			sequenceName = DatabaseConstant.TABLES.SYSTEM_CONFIGS + "_id_seq", 
			allocationSize=1)
	@Column
	private Integer id;
	
	@Column
	private String name;
	
	@Column
	private String value;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}	
}
