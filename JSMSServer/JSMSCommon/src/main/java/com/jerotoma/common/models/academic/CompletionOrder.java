package com.jerotoma.common.models.academic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jerotoma.common.constants.CompletionOrderConstant;
import com.jerotoma.common.constants.DatabaseConstant;
import com.jerotoma.common.constants.SystemConstant;

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

	public CompletionOrder(ResultSet rs) throws SQLException {
		this.id = rs.getInt(CompletionOrderConstant.ID);
		this.name = rs.getString(CompletionOrderConstant.NAME);
		this.completionOrder = rs.getInt(CompletionOrderConstant.COMPLETION_ORDER);
		this.updatedBy = rs.getInt(SystemConstant.UPDATED_BY);
		this.createdOn = rs.getDate("createdOn");
		this.updatedOn = rs.getDate("updatedOn");		
	}

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
