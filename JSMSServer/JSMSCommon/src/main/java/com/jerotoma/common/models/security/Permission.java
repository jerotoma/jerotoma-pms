package com.jerotoma.common.models.security;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="permissions")
public class Permission implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="permission_generator")
	@SequenceGenerator(name="permission_generator", sequenceName = "permissions_id_seq", allocationSize=1)
	@Column(name="id")
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="display_name")
	private String displayName;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM d, yyyy hh:mm:ss a")
	@Column(name="created_on")
	private Date createdOn;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM d, yyyy hh:mm:ss a")
	@Column(name="updated_on")
	private Date updatedOn;
	
	
	public Permission() {}
	
	public Permission(final String name, String displayName) {
		super();
		this.name=name;
		this.displayName=displayName;
		this.createdOn = new Date();
		this.updatedOn = new Date();
	}	  	
	public Permission(Map<String, String> params) {
		this.name = params.get("name");
		this.displayName = params.get("displayName");
		this.createdOn = new Date();
		this.updatedOn = new Date();
	}
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
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
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Permission other = (Permission) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Privilege [name=").append(name).append("]").append("[id=").append(id).append("]");
        return builder.toString();
    }


	
	
}
