package com.jerotoma.common.models.media;

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

import com.jerotoma.common.constants.DatabaseConstant;

@Entity
@Table(name = DatabaseConstant.TABLES.MEDIA)
public class Media implements Serializable{
	
	/**  
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE, 
			generator = DatabaseConstant.TABLES.MEDIA + "_generator")
	@SequenceGenerator(
			name = DatabaseConstant.TABLES.MEDIA + "_generator", 
			sequenceName = DatabaseConstant.TABLES.MEDIA + "_id_seq", 
			allocationSize=1)
	@Column
	private Integer id;
	
	@Column(name="added_by")
	private Integer addedBy;
	
	@Column(name="absolute_path")
	private String absolutePath;
	
	@Column(name="title")
	private String title;
	
	@Column(name="description")
	private String description;
	
	@Column(name="src")
	private String src;
	
	@Column(name="type")
	private String type;
	
	@Column(name="size")
	private long size;
	
	@Column(name="created_on")
	private Date createdOn;
	
	@Column(name="updated_on")
	private Date updatedOn;
	
	public Media() {
		
	}	
	public Media(int id, String absolutePath, String title, String description, String src, String type, long size, int addedBy) {
		super();
		this.id = id;
		this.absolutePath = absolutePath;
		this.title = title;
		this.description = description;
		this.src = src;
		this.type = type;
		this.size = size;
		this.addedBy = addedBy;
	}

	public Media(Map<String, String> params) {
		
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getAddedBy() {
		return addedBy;
	}
	
	public void setAddedBy(Integer addedBy) {
		this.addedBy = addedBy;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	

	public void setSize(long size) {
		
		this.size = size;
	}
   public long getSize() {
		return size;
	}

	public String getAbsolutePath() {
		return absolutePath;
	}
	
	public void setAbsolutePath(String absolutPath) {
		this.absolutePath = absolutPath;
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
	public boolean equals(Object obj) {
		if(obj == null) { return false;}
		if(obj == this) { return true;}
		if(obj instanceof Media) {
			Media m = (Media)obj;
			return getId().equals(m.getId());
		}
		return false;		
	}
	
	@Override
	public int hashCode() {		
		return super.hashCode();
	}
}
