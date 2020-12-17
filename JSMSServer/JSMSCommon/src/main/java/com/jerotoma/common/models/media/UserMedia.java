package com.jerotoma.common.models.media;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jerotoma.common.constants.DatabaseConstant;

@Entity
@Table(name = DatabaseConstant.TABLES.USER_MEDIA)
public class UserMedia {
	
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE, 
			generator = DatabaseConstant.TABLES.USER_MEDIA + "_generator")
	@SequenceGenerator(
			name = DatabaseConstant.TABLES.USER_MEDIA + "_generator", 
			sequenceName = DatabaseConstant.TABLES.USER_MEDIA + "_id_seq", 
			allocationSize=1)
	@Column
	private Integer id;
	
	@Column(name = "user_id")
	private Integer userId;
	
	@Column(name="media_id")
	private Integer mediaId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getMediaId() {
		return mediaId;
	}

	public void setMediaId(Integer mediaId) {
		this.mediaId = mediaId;
	}
}
