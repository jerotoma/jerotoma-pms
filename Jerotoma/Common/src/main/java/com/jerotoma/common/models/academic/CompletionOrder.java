package com.jerotoma.common.models.academic;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jerotoma.common.constants.DatabaseConstant;

@Entity
@Table(name = DatabaseConstant.TABLES.COMPLETION_ORDERS)
public class CompletionOrder {
	
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE, 
			generator = DatabaseConstant.TABLES.COMPLETION_ORDERS + "_generator")
	@SequenceGenerator(
			name = DatabaseConstant.TABLES.COMPLETION_ORDERS + "_generator", 
			sequenceName = DatabaseConstant.TABLES.COMPLETION_ORDERS + "_id_seq", 
			allocationSize=1)
	@Column
	private Integer id;
	
	@Column(name="completion_order")
	private Integer completionOrder;
	
	@Column
	private String name;
	
	@Column(name="created_on")
	private Date createdOn;
	
	@Column(name="updated_by")
	private Integer updatedBy;
	
	@Column(name="updated_on")
	private Date updatedOn;
	
	public CompletionOrder() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getCompletionOrder() {
		return completionOrder;
	}

	public void setCompletionOrder(Integer completionOrder) {
		this.completionOrder = completionOrder;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
}
