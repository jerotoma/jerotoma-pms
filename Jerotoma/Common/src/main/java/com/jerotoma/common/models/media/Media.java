package com.jerotoma.common.models.media;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="media")
public class Media implements Serializable{
	
	/**  
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="media_generator")
	@SequenceGenerator(name="media_generator", sequenceName = "media_id_seq", allocationSize=1)
	@Column(name="id")
	private Integer id;
	
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
	
	public Media() {
		
	}	
	public Media(int id, String absolutePath, String title, String description, String src, String type, long size) {
		super();
		this.id = id;
		this.absolutePath = absolutePath;
		this.title = title;
		this.description = description;
		this.src = src;
		this.type = type;
		this.size = size;
	}

	public Media(Map<String, String> params) {
		
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	
}
